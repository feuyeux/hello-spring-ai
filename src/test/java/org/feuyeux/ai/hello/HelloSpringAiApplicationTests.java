package org.feuyeux.ai.hello;

import lombok.extern.slf4j.Slf4j;
import org.feuyeux.ai.hello.service.MovieRecommendationService;
import org.feuyeux.ai.hello.service.VisionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.util.List;

import static org.springframework.test.util.AssertionErrors.assertTrue;

@Slf4j
@SpringBootTest
class HelloSpringAiApplicationTests {
    @Autowired
    private MovieRecommendationService movieRecommendationService;
    @Autowired
    private VisionService visionService;

    @Test
    public void testRecommendation() {
        long begin = System.currentTimeMillis();
        String recommend = movieRecommendationService.recommend("romance");
        long end = System.currentTimeMillis();
        log.info("recommend[{}ms]:{}", end - begin, recommend);
        assertTrue("recommendation should not empty", !recommend.isEmpty());
    }

    @Test
    public void testRecommendationWithList() {
        long begin = System.currentTimeMillis();
        String recommend =
                movieRecommendationService.recommend(
                        "thriller", List.of("Heat", "Training Day", "Eyes Wide Shut"));
        long end = System.currentTimeMillis();
        log.info("recommendation with list[{}ms]:{}", end - begin, recommend);
        assertTrue("recommendation should not empty", !recommend.isEmpty());
    }

    @Test
    public void testVision() {
        Resource imageResource = new ClassPathResource("p7.jpg");
        visionService.recognize(imageResource);
    }
}
