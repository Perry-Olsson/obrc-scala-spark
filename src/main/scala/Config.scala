class Config(
  val env: String,
  val dataDir: String,
  val dataFile: String
)

object Config {
  def build(args: Array[String]): Config = {
    println(args)
    new Config(
      env = sys.env("ENV"),
      dataDir = sys.env("DATA_DIR"),
      dataFile = sys.env("DATA_FILE")
    );
  }
}
