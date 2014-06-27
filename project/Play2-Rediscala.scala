import sbt._
import sbt.Keys._

object BuildSettings {
  val buildVersion = "1.0.2"

  val buildSettings = Defaults.defaultSettings ++ Seq(
    organization := "fr.njin",
    version := buildVersion,
    scalaVersion := "2.10.4",
    crossScalaVersions := Seq("2.10.4", "2.11.0"),
    crossVersion := CrossVersion.binary
  ) ++ Publish.settings
}

object Publish {
  lazy val settings = bintray.Plugin.bintrayPublishSettings
}

object RediscalaBuild extends Build {
  import BuildSettings._

  lazy val play2Rediscala = Project(
    "Play2-Rediscala",
    file("."),
    settings = buildSettings ++ Seq(
      resolvers := Seq(
        "Sonatype" at "http://oss.sonatype.org/content/groups/public/",
        //"Sonatype snapshots" at "http://oss.sonatype.org/content/repositories/snapshots/",
        "Typesafe repository releases" at "http://repo.typesafe.com/typesafe/releases/",
        "rediscala" at "https://github.com/etaty/rediscala-mvn/raw/master/releases/",
        "scct-github-repository" at "http://mtkopone.github.com/scct/maven-repo"
      ),
      libraryDependencies ++= Seq(
        "com.etaty.rediscala" %% "rediscala" % "1.3.1" cross CrossVersion.binary,
        "com.typesafe.play" %% "play" % "2.3.0" cross CrossVersion.binary,
        "com.typesafe.play" %% "play-test" % "2.3.0" % "test" cross CrossVersion.binary,
        "org.specs2" %% "specs2" % "2.1.1" % "test" cross CrossVersion.binary
      )
    )
  )
}
