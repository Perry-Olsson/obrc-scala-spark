class Config(
  val env: String,
  val fileName: String,
  val dataAccessDriver: String
)

object Config {
  def build(args: Array[String]): Config = {
    var fileName = sys.env("DATA_FILE")
    if (args.length > 0) {
      fileName = args(0)
    }
    var dataAccessDriver = sys.env.getOrElse("DATA_ACCESS_DRIVER", "local")
    new Config(
      env = sys.env("ENV"),
      fileName = fileName,
      dataAccessDriver = dataAccessDriver
    );
  }
}
