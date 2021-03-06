val scala3Version = "3.0.0"

lazy val root = project
  .in(file("."))
  .settings(
    name := "search",
    version := "0.0.1-SNAPSHOT",
    scalaVersion := scala3Version,
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.9" % Test
  )
