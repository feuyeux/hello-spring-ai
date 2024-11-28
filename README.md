# hello-spring-ai

![spring-ai-integration-diagram-3](https://docs.spring.io/spring-ai/reference/_images/spring-ai-integration-diagram-3.svg)

RAG

![](https://docs.spring.io/spring-ai/reference/_images/spring-ai-rag.jpg)

Function Calling

![](https://docs.spring.io/spring-ai/reference/_images/function-calling-basic-flow.jpg)

Spring AI Message API

![](https://docs.spring.io/spring-ai/reference/_images/spring-ai-message-api.jpg)

## Model

config: `src/main/resources/application.yml`

```yaml
spring:
  ai:
    ollama:
      base-url: http://localhost:11434
      chat:
        options:
          model:  x/llama3.2-vision
```

make sure the `ollama` service is running, and the `llama3.2` model is available.

```sh
$ ollama list
```

## Inferencing

```sh
sh build.sh
sh run.sh
```

## Testing

```sh
$ jq --version
jq-1.7.1
```

```text
You're a movie recommendation system. Recommend exactly 5 movies on `movie_genre`=%s.

      Write the final recommendation using the following template:
          Movie Name:
          Synopsis:
          Cast:
```

```text
Use the `movies_list`
below to read each `movie_name`.
Recommend similar movies to the ones presented in `movies_list`
that falls exactly or close to the `movie_genre`provided.
`movies_list`:
%s
```

```sh
$ curl -s 'http://localhost:8080/movies/recommend' --header 'Content-Type: application/json' --data '{
    "genre": "thriller",
    "movies": [
        "Heat",
        "Training Day",
        "Eyes Wide Shut"
    ]
}' | jq '.message' | sed 's/\\n/\n/g; s/\\t/\t/g'
```

## Reference

- <https://docs.spring.io/spring-ai/reference>
- <https://github.com/spring-projects/spring-ai>
- <https://pedrolopesdev.com/intro-to-spring-ai-ollama/>
- <https://github.com/danvega/awesome-spring-ai>