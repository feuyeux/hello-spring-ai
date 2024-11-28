package org.feuyeux.ai.hello;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.feuyeux.ai.hello.service.MovieRecommendationService;
import org.feuyeux.ai.hello.service.VisionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import static org.springframework.test.util.AssertionErrors.assertTrue;

@Slf4j
@SpringBootTest
class HelloSpringAiApplicationTests {
  @Autowired private MovieRecommendationService movieRecommendationService;
  @Autowired private VisionService visionService;

  @Test
  public void testRecommendation() {
    String recommend = movieRecommendationService.recommend("romance");
    log.info("recommend:{}", recommend);
    assertTrue("recommendation should not empty",!recommend.isEmpty());
  }

  @Test
  public void testRecommendationWithList() {
    String recommend = movieRecommendationService.recommend("thriller",
            List.of(
                    "Heat",
            "Training Day",
            "Eyes Wide Shut"
            )
    );
    log.info("recommendation with list :{}", recommend);
    assertTrue("recommendation should not empty",!recommend.isEmpty());
  }

  @Test
  public void testVision() {
    Resource imageResource = new ClassPathResource("spring_ai_rag.png");
    visionService.recognize(imageResource);
  }
}
