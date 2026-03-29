build:
    sbt package

test:
    sbt test

test-it:
    echo "not yet implemented"

up:
    docker compose up --build -d

down:
    docker compose down

run FILE="measurements.txt":
    docker exec master /app/bin/submit.sh {{FILE}}

logs: 
    docker compose logs -f

bash:
    docker exec -it master /bin/bash
