FROM alpine:3.23.3

RUN apk update

RUN apk add openjdk21-jre-headless bash

ARG WORKING_DIR=/app
WORKDIR ${WORKING_DIR}

RUN wget https://dlcdn.apache.org/spark/spark-4.1.1/spark-4.1.1-bin-hadoop3.tgz
RUN tar -xzvf spark-4.1.1-bin-hadoop3.tgz
RUN rm spark-4.1.1-bin-hadoop3.tgz
ENV SPARK_HOME ${WORKING_DIR}/spark-4.1.1-bin-hadoop3

COPY infra/docker/run.sh .
COPY target/scala-2.13/spark-examples_2.13-0.1.0-SNAPSHOT.jar .
COPY measurements.txt ./data/
ENV DATA_FILE_PATH ${WORKING_DIR}/data/measurements.txt

ENTRYPOINT ["/app/run.sh"]
