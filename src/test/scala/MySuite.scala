import org.apache.spark.sql.SparkSession
import org.apache.log4j.Logger
import org.scalatest.BeforeAndAfterAll
import org.scalatest.Suite
import org.scalatest.funsuite.AnyFunSuite
// For more information on writing tests, see
// https://scalameta.org/munit/docs/getting-started.html
class MySuite extends AnyFunSuite with SparkTestSession {
  import spark.implicits._

  test("example spark") {
    val input = Seq(1, 2, 3).toDF("value")

    val result = input.filter($"value" > 1)

    val collected = result.as[Int].collect()
    assert(collected.sameElements(Array(2, 3)))
  }

  test("another") {
    val lines = spark.sparkContext.parallelize(Seq("a", "b", "c"))
    val length = lines.map(s => s.length)
      .reduce((a, b) => a + b)
    assert(length == 3)
  }

  test("a third") {
    val lines = spark.sparkContext.parallelize(Seq("a", "b", "c", "c"))
    val length = lines.map(s => s.length)
      .reduce((a, b) => a + b)
    assert(length == 4)
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
