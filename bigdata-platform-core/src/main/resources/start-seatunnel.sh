#!/bin/sh

# -c [配置文件位置]

export SEATUNNEL_HOME=/usr/local/seatunnel-xd
configLocation=$SEATUNNEL_HOME/config/spark.streaming.conf.template

usage() {
    echo "Usage: sh start-seatunnel.sh -c [config location]"
    exit 1
}

start() {
  echo "Start SeaTunnel using " "$1"
  configLocation=$1
  nohup $SEATUNNEL_HOME/bin/start-seatunnel-spark.sh --master yarn --deploy-mode client --config "${configLocation}" > /home/zlx/test.log 2>&1 &
}

if [ $# -lt 1 ]
then
  usage
  exit
fi

while getopts ":c:" opt
do 
  case $opt in
    c)
      echo "config file locate in $OPTARG"
      start $OPTARG
      exit 1
    ;;
  esac
done