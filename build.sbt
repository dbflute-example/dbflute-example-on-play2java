name := """dbflute-example-on-play2java"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  javaWs,
  "org.dbflute" % "dbflute-runtime" % "1.1.0-RC1",
  "mysql" % "mysql-connector-java" % "5.1.33",
  "com.google.inject" % "guice" % "3.0"
)
