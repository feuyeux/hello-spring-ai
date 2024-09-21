# hello-spring-ai

```sh
$ ollama run llama3
```

```sh
$ mvn clean spring-boot:run
```

```sh
$ curl --location 'http://localhost:8080/movies/recommend' \
--header 'Content-Type: application/json' \
--data '{
    "genre": "thriller",
    "movies": [
        "Heat",
        "Training Day",
        "Eyes Wide Shut"
    ]
}'
```

## Reference

<https://docs.spring.io/spring-ai/reference/1.0/index.html>
