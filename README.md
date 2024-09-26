# hello-spring-ai

![](https://images.ctfassets.net/mnrwi97vnhts/3Q5FXZrrY37SmOkOqyc7r6/1f47075ab3df565fd6402473a2514045/Restored_Spring_AI__1_.jpg)

## Model

config: `src/main/resources/application.yml`

```yaml
spring:
  ai:
    ollama:
      base-url: http://localhost:11434
      chat:
        options:
          model: llama3
```

make sure the `ollama` service is running, and the `llama3` model is available.

```sh
$ ollama list
NAME                ID              SIZE      MODIFIED
llama3.1:latest     62757c860e01    4.7 GB    7 weeks ago
gemma2:latest       ff02c3702f32    5.4 GB    7 weeks ago
codellama:latest    8fdf8f752f6e    3.8 GB    3 months ago
codegemma:latest    0c96700aaada    5.0 GB    3 months ago
llava:latest        8dd30f6b0cb1    4.7 GB    3 months ago
llama3:latest       365c0bd3c000    4.7 GB    3 months ago
qwen2:latest        e0d4e1163c58    4.4 GB    3 months ago
```

## Inferencing

```sh
$ mvn clean spring-boot:run
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

<https://docs.spring.io/spring-ai/reference/1.0/index.html>
