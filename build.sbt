name := "scalikejdbc-sqlsyntax-ext"

organization := "com.github.roundrop"

description := "ScalikeJDBC's SQLSyntax extension"

scalaVersion := "2.12.0"

crossScalaVersions := Seq("2.12.0", "2.11.8")

licenses := Seq(("Apache License, Version 2.0", url("http://www.apache.org/licenses/LICENSE-2.0")))

homepage := Some(new URL("https://github.com/roundrop/scalikejdbc-sqlsyntax-ext"))

startYear := Some(2016)

scalacOptions := Seq(
  "-encoding", "UTF-8",
  "-feature",
  "-unchecked",
  "-deprecation",
  "-Xfatal-warnings",
  "-Xlint",
  "-Yno-adapted-args",
  "-Ywarn-dead-code",
  "-Ywarn-numeric-widen",
  "-Xfuture")

libraryDependencies ++= Seq(
  "org.scalikejdbc" %% "scalikejdbc" % "2.5.0" % Provided,
  "org.scalatest" %% "scalatest" % "3.0.0" % Test)

pomExtra in Global := {
  <scm>
    <url>git@github.com:roundrop/scalikejdbc-sqlsyntax-ext.git</url>
    <connection>scm:git:git@github.com:roundrop/scalikejdbc-sqlsyntax-ext.git</connection>
    <developerConnection>scm:git:git@github.com:roundrop/scalikejdbc-sqlsyntax-ext.git</developerConnection>
  </scm>
    <developers>
      <developer>
        <id>roundrop</id>
        <name>Ryuji Yamashita</name>
        <email>roundrop@gmail.com</email>
      </developer>
    </developers>
}