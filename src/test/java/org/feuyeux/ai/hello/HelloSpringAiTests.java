package org.feuyeux.ai.hello;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.feuyeux.ai.hello.service.MovieRecommendationService;
import org.junit.jupiter.api.Test;

@Slf4j
class HelloSpringAiTests {
  private MovieRecommendationService movieRecommendationService = new MovieRecommendationService();

  @Test
  public void testRecommendation() {
    long begin = System.currentTimeMillis();
    String recommend = movieRecommendationService.recommend("romance");
    long end = System.currentTimeMillis();
    log.info("recommend[{}ms]:{}", end - begin, recommend);
    assertTrue(!recommend.isEmpty(), "recommendation should not empty");
  }

  @Test
  public void testRecommendationWithList() {
    long begin = System.currentTimeMillis();
    String recommend =
        movieRecommendationService.recommend(
            "thriller", List.of("Heat", "Training Day", "Eyes Wide Shut"));
    long end = System.currentTimeMillis();
    log.info("recommendation with list[{}ms]:{}", end - begin, recommend);
    assertTrue(!recommend.isEmpty(), "recommendation should not empty");
  }
}
