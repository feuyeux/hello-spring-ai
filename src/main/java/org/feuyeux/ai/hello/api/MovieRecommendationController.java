package org.feuyeux.ai.hello.api;

import static org.springframework.util.CollectionUtils.isEmpty;

import lombok.RequiredArgsConstructor;
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
public class MovieRecommendationController {

  private final MovieRecommendationService movieRecommendationService;

  @PostMapping("/recommend")
  public MovieRecommendationResponse recommend(@RequestBody MovieRecommendationRequest request) {
    if (request.getGenre() == null || request.getGenre().isEmpty()) {
      throw new IllegalArgumentException("Parameter genre is mandatory to recommend movies");
    }

    if (!isEmpty(request.getMovies())) {
      return new MovieRecommendationResponse(
          movieRecommendationService.recommend(request.getGenre(), request.getMovies()));
    }

    return new MovieRecommendationResponse(
        movieRecommendationService.recommend(request.getGenre()));
  }
}
