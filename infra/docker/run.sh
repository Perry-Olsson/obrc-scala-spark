#!/bin/sh

MASTER="local[*]"


$SPARK_HOME/bin/spark-submit --class "SimpleApp" --master $MASTER spark-examples_2.13-0.1.0-SNAPSHOT.jar

