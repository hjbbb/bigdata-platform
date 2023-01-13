export JAVA_HOME=/usr/lib/jvm/jdk1.8.0_301
export JRE_HOME=${JAVA_HOME}/jre
export CLASSPATH=.:${JAVA_HOME}/lib:${JRE_HOME}/lib
export PATH=${JAVA_HOME}/bin:$PATH
export PATH=$PATH:$JAVA_HOME

#scala
export SCALA_HOME=/usr/local/scala-2.11.12
export PATH=$PATH:$SCALA_HOME/bin

#spark
export SPARK_HOME=/usr/local/spark-2.4.0
export PATH=$PATH:$SPARK_HOME/bin

#zookeeper
export ZK_HOME=/usr/local/zookeeper
export PATH=$PATH:$ZK_HOME/bin

#kafka
export KAFKA_HOME=/usr/local/kafka
export PATH=$PATH:$KAFKA_HOME/bin

#Hbase
export HBASE_HOME=/usr/local/hbase
export PATH=$PATH:$HBASE_HOME/bin