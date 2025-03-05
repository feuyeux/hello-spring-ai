package org.feuyeux.ai.hello.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import org.feuyeux.ai.hello.info.MovieRecommendationRequest;
import org.feuyeux.ai.hello.service.MovieRecommendationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class MovieRecommendationControllerTest {

  @Mock private MovieRecommendationService movieRecommendationService;

  @InjectMocks private MovieRecommendationController movieRecommendationController;

  @Test
  void shouldRecommendMoviesWithGenreOnly() {
    // Given
    String expectedRecommendation = "Test recommendation";
    MovieRecommendationRequest request = new MovieRecommendationRequest();
    request.setGenre("action");
    when(movieRecommendationService.recommend(anyString())).thenReturn(expectedRecommendation);

    // When
    var response = movieRecommendationController.recommend(request);

    // Then
    assertEquals(expectedRecommendation, response.getMessage());
  }

  @Test
  void shouldRecommendMoviesWithGenreAndMoviesList() {
    // Given
    String expectedRecommendation = "Test recommendation with movies list";
    MovieRecommendationRequest request = new MovieRecommendationRequest();
    request.setGenre("action");
    request.setMovies(Arrays.asList("Movie1", "Movie2"));
    when(movieRecommendationService.recommend(anyString(), anyList()))
        .thenReturn(expectedRecommendation);

    // When
    var response = movieRecommendationController.recommend(request);

    // Then
    assertEquals(expectedRecommendation, response.getMessage());
  }

  @Test
  void shouldThrowExceptionWhenGenreIsNull() {
    // Given
    MovieRecommendationRequest request = new MovieRecommendationRequest();
    request.setMovies(Collections.emptyList());

    // When & Then
    assertThrows(
        IllegalArgumentException.class, () -> movieRecommendationController.recommend(request));
  }

  @Test
  void shouldThrowExceptionWhenGenreIsEmpty() {
    // Given
    MovieRecommendationRequest request = new MovieRecommendationRequest();
    request.setGenre("");
    request.setMovies(Collections.emptyList());

    // When & Then
    assertThrows(
        IllegalArgumentException.class, () -> movieRecommendationController.recommend(request));
  }
}
