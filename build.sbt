name := """dbflute-example-on-play2java"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  javaWs,
  "org.dbflute" % "dbflute-runtime" % "1.2.6",
  "mysql" % "mysql-connector-java" % "5.1.47",
  "com.google.inject" % "guice" % "3.0",
  "org.springframework" % "spring-jdbc" % "4.3.4.RELEASE",
  "org.springframework" % "spring-aop" % "4.3.4.RELEASE"
)

PlayKeys.ebeanEnabled := false

EclipseKeys.withSource := true

EclipseKeys.eclipseOutput := Some(".target")

// to speed up compilation
// ref: https://www.playframework.com/documentation/2.4.x/SBTCookbook
sources in (Compile, doc) := Seq.empty

// also to speed up compilation
publishArtifact in (Compile, packageDoc) := false
