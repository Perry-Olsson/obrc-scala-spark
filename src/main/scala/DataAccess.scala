import org.apache.spark.rdd.RDD
import org.apache.spark.SparkContext
import org.apache.spark.SparkConf

object DataAccessFactory {
  def getDataAccess(config: Config, spark: SparkContext): DataAccess[RDD[String], String] = {
    return new LocalDevDataAccess(spark, config)
  }
}

class LocalDevDataAccess(spark: SparkContext, config: Config) extends DataAccess[RDD[String], String] {
    override def readData(): RDD[String] = {
      spark.textFile(f"${config.dataDir}/${config.dataFile}")
    }
    override def writeData(data: String): Unit = {
      println(data)
    }
}

trait DataAccess[T, K]{
  def readData(): T

  def writeData(data: K): Unit 
}
