package org.feuyeux.ai.hello;

import lombok.extern.slf4j.Slf4j;
import org.feuyeux.ai.hello.service.MovieRecommendationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class HelloSpringAiApplicationTests {
    @Autowired
    private MovieRecommendationService movieRecommendationService;

    @Test
    public void testRecommendation() {
        String recommend = movieRecommendationService.recommend("thriller");
        log.info("recommend:{}", recommend);
    }

    @Test
    public void test() {
        log.info("Hello Spring AI");
    }
}
