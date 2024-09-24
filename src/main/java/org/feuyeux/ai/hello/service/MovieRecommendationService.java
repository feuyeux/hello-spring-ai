package org.feuyeux.ai.hello.service;

import static java.util.stream.Collectors.joining;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MovieRecommendationService {
  private static final String INSTRUCTIONS_PROMPT_MESSAGE =
      """
      You're a movie recommendation system. Recommend exactly 5 movies on `movie_genre`=%s.

      Write the final recommendation using the following template:
          Movie Name:
          Synopsis:
          Cast:
      """;

  private static final String EXAMPLES_PROMPT_MESSAGE =
      """
      Use the `movies_list`
      below to read each `movie_name`.
      Recommend similar movies to the ones presented in `movies_list`
      that falls exactly or close to the `movie_genre`provided.
      `movies_list`:
      %s
      """;

  private final OllamaChatModel ollamaChatClient;

  public MovieRecommendationService(OllamaChatModel ollamaChatClient) {
    this.ollamaChatClient = ollamaChatClient;
  }

  public String recommend(String genre) {
    var generalInstructions = String.format(INSTRUCTIONS_PROMPT_MESSAGE, genre);

    var currentPromptMessage = new UserMessage(generalInstructions);

    var prompt = new Prompt(currentPromptMessage);

    AssistantMessage message = ollamaChatClient.call(prompt).getResult().getOutput();
    return message.getContent();
  }

  public String recommend(String genre, List<String> movies) {
    var moviesCollected = movies.stream().collect(joining("\n`movie_name`=", "\n", ""));
    var generalInstructions = new UserMessage(String.format(INSTRUCTIONS_PROMPT_MESSAGE, genre));
    var examplesSystemMessage =
        new SystemMessage(String.format(EXAMPLES_PROMPT_MESSAGE, moviesCollected));
    var prompt = new Prompt(List.of(generalInstructions, examplesSystemMessage));
    String content = "";

    try {
      AssistantMessage message = ollamaChatClient.call(prompt).getResult().getOutput();
      content = message.getContent();
    } catch (Exception e) {
      log.error("Error while calling OllamaChatClient", e);
    }
    return content;
  }
}
