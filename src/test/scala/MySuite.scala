import org.apache.spark.sql.SparkSession
import org.apache.log4j.Logger
import org.scalatest.BeforeAndAfterAll
import org.scalatest.Suite
import org.scalatest.funsuite.AnyFunSuite
import org.apache.spark.rdd.RDD
// For more information on writing tests, see
// https://scalameta.org/munit/docs/getting-started.html
class MySuite extends AnyFunSuite with SparkTestSession {
  import spark.implicits._

  test("another") {
    var testData = Seq(
      "Alice Springs;1.0",
      "Alice Springs;1.0",
      "Alice Springs;1.0"
    )
    val dataAccess = new TestDataAccess(spark, testData)
    SimpleApp.obrc(dataAccess)
    val result = dataAccess.getOutput()
    assert(result == "{Alice Springs=1.0/1.0/1.0}")
  }
}

trait SparkTestSession extends BeforeAndAfterAll { self: Suite =>

  lazy val spark: SparkSession = SparkSession.builder()
    .master("local[*]")
    .appName("test")
    .getOrCreate()

  override def afterAll(): Unit = {
    spark.stop()
    super.afterAll()
  }
}

class TestDataAccess(spark: SparkSession, input: Seq[String]) extends DataAccess[RDD[String], String] {
  var output: String = null

  override def readData(): RDD[String] = {
    return spark.sparkContext.parallelize(input)
  }

  override def writeData(data: String): Unit = {
    this.output = data
  }

  def getOutput(): String = {
    return this.output
  }
}

