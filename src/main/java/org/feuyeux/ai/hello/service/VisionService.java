package org.feuyeux.ai.hello.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Media;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

@Slf4j
@Service
public class VisionService {

  private final OllamaChatModel ollamaChatClient;

  public VisionService(OllamaChatModel ollamaChatClient) {
    this.ollamaChatClient = ollamaChatClient;
  }

  public void recognize(Resource imageResource) {
    log.info("Recognizing image: {}", imageResource);
    String extension =
        imageResource.getFilename().substring(imageResource.getFilename().lastIndexOf('.') + 1);
    Media imageMedia;
    switch (extension) {
      case "jpg":
        imageMedia = new Media(MimeTypeUtils.IMAGE_JPEG, imageResource);
        break;
      case "png":
        imageMedia = new Media(MimeTypeUtils.IMAGE_PNG, imageResource);
        break;
      default:
        throw new IllegalArgumentException("Unsupported image format: " + extension);
    }
    String query = "你看到了什么？解释下你看到的内容";
    var userMessage = new UserMessage(query, imageMedia);
    Prompt prompt = new Prompt(userMessage);
    try {
      long begin = System.currentTimeMillis();
      AssistantMessage message = ollamaChatClient.call(prompt).getResult().getOutput();
      String content = message.getContent();
      long end = System.currentTimeMillis();
      log.info("Recognized image[{}ms]: {}", end - begin, content);
    } catch (Exception e) {
      log.error("Error while calling OllamaChatClient", e);
    }
  }
}
