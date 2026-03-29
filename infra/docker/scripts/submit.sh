#!/bin/sh

MASTER="local[*]"
$SPARK_HOME/bin/spark-submit --class "SimpleApp" \
    --master $MASTER \
    /app/local-target/spark-examples_2.13-0.1.0-SNAPSHOT.jar "$@"
