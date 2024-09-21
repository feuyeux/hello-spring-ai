package org.feuyeux.ai.hello.info;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieRecommendationRequest {
  @JsonProperty("genre")
  private String genre;

  @JsonProperty("movies")
  private List<String> movies;
}
