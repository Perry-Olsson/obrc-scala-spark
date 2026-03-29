build:
    sbt package

test-it:
    echo "not yet implemented"

test:
    sbt test

up:
    docker compose up --build -d

down:
    docker compose down

logs: 
    docker compose logs -f

bash:
    docker exec -it master /bin/bash

run:
    docker exec master "/app/bin/submit.sh"
