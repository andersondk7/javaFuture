
// The simplest possible sbt build file is just one line:

scalaVersion := "2.13.8"
name := "java-future"
organization := "org.dka"
version := "1.0"

libraryDependencies += "org.scala-lang.modules" %% "scala-parser-combinators" % "2.1.1"
libraryDependencies += "org.scala-lang.modules" %% "scala-java8-compat" % "1.0.0"

