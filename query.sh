curl -s 'http://localhost:8080/movies/recommend' --header 'Content-Type: application/json' --data '{
    "genre": "thriller",
    "movies": [
        "Heat",
        "Training Day",
        "Eyes Wide Shut"
    ]
}' | jq '.message' | sed 's/\\n/\n/g; s/\\t/\t/g'