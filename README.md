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
          model: llama3.2
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

```sh
$ curl -s 'http://localhost:8080/movies/recommend' --header 'Content-Type: application/json' --data '{
    "genre": "thriller",
    "movies": [
        "Heat",
        "Training Day",
        "Eyes Wide Shut"
    ]
}' | jq '.message' | sed 's/\\n/\n/g; s/\\t/\t/g'

"You're a movie recommendation system. Recommend exactly 5 movies on `movie_genre`=thriller.

Here are my top recommendations for you:

**Movie 1:**
Se7en:
Synopsis: Two detectives, one a veteran and the other a rookie, hunt down a serial killer who is using the seven deadly sins as a motif for his murders.
Cast: Morgan Freeman, Brad Pitt, Gwyneth Paltrow

**Movie 2:**
Memento:
Synopsis: A former insurance investigator suffers from short-term memory loss and sets out to avenge his wife's murder, using a system of tattoos and notes to hunt down her killer.
Cast: Guy Pearce, Carrie-Anne Moss, Joe Pantoliano

**Movie 3:**
Gone Girl:
Synopsis: A couple's seemingly perfect marriage turns out to be a facade when the wife goes missing, and the husband becomes the prime suspect in her disappearance.
Cast: Ben Affleck, Rosamund Pike, Neil Patrick Harris

**Movie 4:**
Prisoners:
Synopsis: Two families are torn apart by the abduction of their daughters, leading the desperate fathers to take matters into their own hands and seek justice.
Cast: Hugh Jackman, Jake Gyllenhaal, Viola Davis

**Movie 5:**
Shutter Island:
Synopsis: A U.S. Marshal investigates a mental institution where patients have been mysteriously disappearing, but he soon discovers that his own sanity is at stake.
Cast: Leonardo DiCaprio, Mark Ruffalo, Ben Kingsley"
```

## Reference

- <https://docs.spring.io/spring-ai/reference>
- https://github.com/spring-projects/spring-ai