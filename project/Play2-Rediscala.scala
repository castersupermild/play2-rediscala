import bintray.Plugin
import sbt._
import sbt.Keys._
import bintray.Keys._
import scoverage.ScoverageSbtPlugin
import scoverage.ScoverageSbtPlugin._


object Versions {
  lazy val play = "2.3.7"
  lazy val play2RedisScala = s"$play.0"
}

object BuildSettings {
  val buildVersion = Versions.play2RedisScala

  val settings = Defaults.defaultSettings ++ Seq(
    organization := "fr.njin",
    version := buildVersion,
    scalaVersion := "2.10.4",
    crossScalaVersions := Seq("2.10.4", "2.11.4"),
    crossVersion := CrossVersion.binary
  )
}


object CoverageSettings {
  lazy val settings = ScoverageSbtPlugin.instrumentSettings ++ Seq(
      ScoverageKeys.minimumCoverage := 80,
      ScoverageKeys.failOnMinimumCoverage := false,
      ScoverageKeys.excludedPackages in ScoverageCompile := "<empty>;Reverse.*;"
    ) ++ CoverallsPlugin.coverallsSettings ++ Seq(
      CoverallsPlugin.CoverallsKeys.coverallsToken := Some("ry8vH3uqfSTlpNtbL0Go58d3IpNsAJcVq")
    )
}



object Publish {
  lazy val settings = Plugin.bintrayPublishSettings ++ Seq(
    repository in bintray := "maven",
    bintrayOrganization in bintray := Some("yorrick"),
    licenses += ("Apache-2.0", url("http://www.apache.org/licenses/LICENSE-2.0.html"))
  )
}

object RediscalaBuild extends Build {

  lazy val play2Rediscala = Project(
    "Play2-Rediscala",
    file("."),
    settings = BuildSettings.settings ++
      CoverageSettings.settings ++
      Publish.settings ++
      Seq(
        resolvers := Seq(
        "Sonatype" at "http://oss.sonatype.org/content/groups/public/",
        //"Sonatype snapshots" at "http://oss.sonatype.org/content/repositories/snapshots/",
        "Typesafe repository releases" at "http://repo.typesafe.com/typesafe/releases/",
        "rediscala" at "http://dl.bintray.com/etaty/maven",
        "scct-github-repository" at "http://mtkopone.github.com/scct/maven-repo"
        ),
        libraryDependencies ++= Seq(
          "com.etaty.rediscala" %% "rediscala" % "1.3.1",
          "com.typesafe.play" %% "play" % Versions.play,
          "com.typesafe.play" %% "play-test" % Versions.play % "test"
        ),
        libraryDependencies <+= scalaVersion(specs2(_))
      )
  )

  // Helper method to pattern match against the scala version and return the correct specs version
  def specs2(scalaVersion: String): ModuleID =
    (scalaVersion match {
      case version if version.startsWith("2.11") => "org.specs2" %% "specs2" % "2.3.11"
      case _ => "org.specs2" %% "specs2" % "2.1.1"
    }) % "test"
}
