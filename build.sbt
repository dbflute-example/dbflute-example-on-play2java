name := """dbflute-example-on-play2java"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  javaWs,
  "org.dbflute" % "dbflute-runtime" % "1.1.0",
  "mysql" % "mysql-connector-java" % "5.1.33",
  "com.google.inject" % "guice" % "3.0",
  "org.springframework" % "spring-jdbc" % "4.1.1.RELEASE",
  "org.springframework" % "spring-aop" % "4.1.1.RELEASE"
)

// Play2 adjustments
PlayKeys.ebeanEnabled := false

// Eclipse settings
EclipseKeys.withSource := true
