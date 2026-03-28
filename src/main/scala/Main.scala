/* SimpleApp.scala */
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

object SimpleApp {
  def main(args: Array[String]): Unit = {
    val spark = new SparkContext(
      new SparkConf().setAppName("obrc-scala")
    );
    var data = new DataAccess[RDD[String], String] {
      override def readData(): RDD[String] = {
        spark.textFile(f"${sys.env("DATA_DIR")}/measurements.txt")
      }
      override def writeData(data: String): Unit = {
        println(data)
      }
    }
    obrc(data)
  }

  def obrc(dataAccess: DataAccess[RDD[String], String]): Unit = {
    val data = dataAccess.readData()
    val result = data.map(split)
      .combineByKey(v => new Result(v, v, v, 1), accumulate, merge)
      .sortByKey(ascending = true)
      .map(value => s"${value._1}=${value._2.toDisplayString}")
      .collect()
      .mkString("{", ", ", "}")

    dataAccess.writeData(result)
  }

  def split(s: String): (String, Double) = {
    val split = s.split(";")
    (split(0), split(1).toDouble)
  }

  def accumulate(result: Result, value: Double): Result = {
      result.count += 1
      result.total += value
      result.min = math.min(result.min, value)
      result.max = math.max(result.max, value)
      result
  }

  def merge(a: Result, b: Result): Result = {
      a.min = math.min(a.min, b.min)
      a.max = math.max(a.max, b.max)
      a.total = a.total + b.total
      a.count = a.count + b.count
      a
  }

  class Result (
    var min: Double = Double.MaxValue,
    var max: Double = Double.MinValue,
    var total: Double = 0,
    var count: Int = 0
  ) extends Serializable {
    override def toString: String = {
      s"min: $min, max: $max, total: $total, count: $count"
    }

    def toDisplayString: String = {
      f"$min/$max/${total / count}%.1f"
    }
  }
}

trait DataAccess[T, K]{
  def readData(): T

  def writeData(data: K): Unit 
}
