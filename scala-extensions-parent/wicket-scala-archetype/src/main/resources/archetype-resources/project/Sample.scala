import sbt._
import Keys._
import scala._

object Dependencies {

  val ResolutionRepos = Seq(
    "Local Maven Repository" at "file://"+Path.userHome.absolutePath+"/.m2/repository",
    "central" at "http://central.maven.org/maven2/",
    "Typesafe repo" at "http://repo.typesafe.com/typesafe/releases/"
  )

  object V {
    val Wicket  = "6.12.0"
    val Jetty   = "8.1.14.v20131031"
    val Slf4j   = "1.7.5"
  }

  val Compile = Seq(
    "org.apache.wicket" %  "wicket-core"      % V.Wicket    % "compile",
    "org.wicketstuff.scala" % "wicketstuff-scala" % "6.0-SNAPSHOT" % "compile",
    "org.slf4j" % "slf4j-reload4j" % V.Slf4j % "compile",
    "log4j" % "log4j" % "1.2.17"
  )

  val Testing = Seq(
    "junit" % "junit" % "4.11" % "test",
    "org.eclipse.jetty.aggregate" % "jetty-all-server" % V.Jetty   % "test",
    "org.scalatest" % "scalatest_2.10" % "2.0" % "test"
  )

  val Provided = Seq(
    "jakarta.servlet" % "jakarta.servlet-api" % "6.0.0" % "provided"
  )
}

object Sample extends sbt.Build {

  lazy val Myproject = Project("myproject", file("."))
    .settings(
      organization  := "com.myproject",
      version       := "1.0-SNAPSHOT",
      scalaVersion  := "2.10.3",
      scalacOptions := Seq("-deprecation", "-encoding", "utf8"),
      resolvers     ++= Dependencies.ResolutionRepos,
      libraryDependencies ++= Dependencies.Compile,
      libraryDependencies ++= Dependencies.Provided
    )

    // testing
    .settings(
      libraryDependencies ++= Dependencies.Testing,
      testOptions in Test := Seq(Tests.Filter(s => s.endsWith("Spec")))
    )

}

