val scala3Version = "2.13.17"

lazy val root = project
  .in(file("."))
  .settings(
    name := "spark-examples",
    version := "0.1.0-SNAPSHOT",

    scalaVersion := scala3Version,

    libraryDependencies += "org.scalameta" %% "munit" % "1.2.4" % Test,
    libraryDependencies += "org.apache.spark" %% "spark-sql" % "4.1.1",
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.19" % Test
  )
