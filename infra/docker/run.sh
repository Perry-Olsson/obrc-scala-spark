#!/bin/sh

export SPARK_WORKER_INSTANCES=2
export SPARK_WORKER_CORES=1
export SPARK_MASTER_HOST=localhost

$SPARK_HOME/sbin/start-master.sh

$SPARK_HOME/sbin/start-worker.sh --cores $SPARK_WORKER_CORES spark://localhost:7077

sleep infinity
