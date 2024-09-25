package org.feuyeux.ai.hello.api;

import static org.springframework.util.CollectionUtils.isEmpty;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.feuyeux.ai.hello.info.MovieRecommendationRequest;
import org.feuyeux.ai.hello.info.MovieRecommendationResponse;
import org.feuyeux.ai.hello.service.MovieRecommendationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/movies")
@Slf4j
public class MovieRecommendationController {

  private final MovieRecommendationService movieRecommendationService;

  @PostMapping("/recommend")
  public MovieRecommendationResponse recommend(@RequestBody MovieRecommendationRequest request) {
    if (request.getGenre() == null || request.getGenre().isEmpty()) {
      throw new IllegalArgumentException("Parameter genre is mandatory to recommend movies");
    }
    MovieRecommendationResponse movieRecommendationResponse;
    long start = System.currentTimeMillis();
    log.info("Recommend movies for genre: {}", request.getGenre());
    if (!isEmpty(request.getMovies())) {
      movieRecommendationResponse =
          new MovieRecommendationResponse(
              movieRecommendationService.recommend(request.getGenre(), request.getMovies()));
    } else {
      movieRecommendationResponse =
          new MovieRecommendationResponse(movieRecommendationService.recommend(request.getGenre()));
    }
    long end = System.currentTimeMillis();
    log.info("Recommendation:{}, took {} ms", movieRecommendationResponse, end - start);
    return movieRecommendationResponse;
  }
}
