import sbt._
import Keys._
import xerial.sbt.Sonatype.sonatypeSettings

object Publish {
  lazy val settings = sonatypeSettings :+ (pomExtra :=
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
      </developers>)
}