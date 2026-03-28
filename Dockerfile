FROM debian:stable-slim

ARG WORKING_DIR=/app
WORKDIR ${WORKING_DIR}

RUN apt update
RUN apt install -y openjdk-21-jdk-headless bash wget procps

RUN wget https://dlcdn.apache.org/spark/spark-4.1.1/spark-4.1.1-bin-hadoop3.tgz
RUN tar -xzvf spark-4.1.1-bin-hadoop3.tgz
RUN rm spark-4.1.1-bin-hadoop3.tgz
ENV SPARK_HOME ${WORKING_DIR}/spark-4.1.1-bin-hadoop3

COPY infra/docker/run.sh .
COPY target/scala-2.13/spark-examples_2.13-0.1.0-SNAPSHOT.jar .
ENV DATA_DIR ${WORKING_DIR}/data/

ENTRYPOINT ["/app/run.sh"]
