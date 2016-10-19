import sbt._
import Keys._
import sbtrelease.ReleasePlugin._

object Build extends Build {
  lazy val basicSettings = Seq(
    name := "ScalikeJDBC SQLSyntax Extension",
    organization := "com.github.roundrop",
    description := "ScalikeJDBC's SQLSyntax extension",
    scalaVersion := "2.11.8",
    licenses := Seq(("Apache License, Version 2.0", url("http://www.apache.org/licenses/LICENSE-2.0"))),
    homepage := Some(new URL("https://github.com/roundrop/scalikejdbc-sqlsyntax-ext")),
    startYear := Some(2016),
    scalacOptions := Seq("-encoding", "UTF-8", "-unchecked", "-deprecation", "-feature"),
    libraryDependencies ++= Seq(scalikejdbc, scalatest)
  )

  val scalikejdbc = "org.scalikejdbc" %% "scalikejdbc" % "2.4.2" % "provided"
  val scalatest = "org.scalatest"     %% "scalatest"   % "3.0.0" % "test"

  lazy val root = Project(
    "scalikejdbc-sqlsyntax-ext",
    file("."),
    settings = basicSettings ++ Defaults.defaultSettings ++ releaseSettings  ++ Publish.settings)
}