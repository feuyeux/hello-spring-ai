package org.feuyeux.ai.hello.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Media;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;
import reactor.core.publisher.Flux;

@Slf4j
@Service
public class VisionService {

    private final OllamaChatModel ollamaChatClient;

    public VisionService(OllamaChatModel ollamaChatClient) {
        this.ollamaChatClient = ollamaChatClient;
    }

    public void recognize(Resource imageResource) {
        log.info("Recognizing image: {}", imageResource);
        Media imageMedia = new Media(MimeTypeUtils.IMAGE_PNG, imageResource);

        String query = "Explain what do you see on this picture? express the expression of the person in the picture.";
        var userMessage = new UserMessage(query, imageMedia);
        Prompt prompt = new Prompt(userMessage);

        try {
            AssistantMessage message = ollamaChatClient.call(prompt).getResult().getOutput();
            String content = message.getContent();
            log.info("Recognized image: {}", content);
        } catch (Exception e) {
            log.error("Error while calling OllamaChatClient", e);
        }
    }
}
