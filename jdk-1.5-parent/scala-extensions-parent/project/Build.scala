import sbt._
import sbt.Keys._
import Project.Initialize

object WicketScalaParent extends Build
{
	val WicketVersion = "1.5-SNAPSHOT"
	val Organization  = "org.wicketstuff"
	val ScalaVersion  = "2.9.0-1"

	val ScalaCheckDep = "org.scala-tools.testing" %% "scalacheck" % "1.9"
	val JUnitDep      = "junit" % "junit" % "4.8.2" % "test->default"
	val Specs1Dep     = "org.scala-tools.testing" % "specs_2.9.0-1" % "1.6.8" % "test->default"
	val ServletDep    = "javax.servlet" % "servlet-api" % "2.5"
	val Slf4jLog4jDep = "org.slf4j" % "slf4j-log4j12" % "1.6.1"
	val WicketExtensionsDep = "org.apache.wicket" % "wicket-extensions" % "1.5-SNAPSHOT"
	val WicketDep     = "org.apache.wicket" % "wicket" % "1.5-SNAPSHOT"
	val ScalaTestDep  = "org.scalatest" % "scalatest" % "1.3" % "test->default"

	lazy val parent = Project("parent", file("."),
		settings = Defaults.defaultSettings ++ Seq(
			name := "Wicket Scala Parent",
			version := WicketVersion,
			organization := Organization,
			scalaVersion := ScalaVersion,
			publishMavenStyle := true,
			resolvers += "Apache Snapshots" at "http://repository.apache.org/snapshots"
		)
	) aggregate(wicketScala, wicketScalaSample)


	lazy val wicketScala = Project("wicketScala", file("wicket-scala"),
		settings = Defaults.defaultSettings ++ Seq(
			name := "Wicket Scala",
			version := WicketVersion,
			organization := Organization,
			scalaVersion := ScalaVersion,
			publishMavenStyle := true,
			resolvers += "Apache Snapshots" at "http://repository.apache.org/snapshots",
			retrieveManaged := true,

			libraryDependencies ++= Seq(
				ScalaCheckDep,
				JUnitDep,
				Specs1Dep,
				ScalaTestDep,
				WicketDep,
				WicketExtensionsDep,
				ServletDep % "provided->default",
				Slf4jLog4jDep
			)
		)

	)

	lazy val wicketScalaSample = Project("wicketScalaSample", file("wicket-scala-sample"),
		settings = Defaults.defaultSettings ++ Seq(
			name := "Wicket Scala Sample",
			version := WicketVersion,
			organization := Organization,
			scalaVersion := ScalaVersion,
			publishMavenStyle := true,
			resolvers += "Apache Snapshots" at "http://repository.apache.org/snapshots",

			libraryDependencies ++= Seq(
				Specs1Dep,
				ScalaCheckDep,
				JUnitDep,
				ServletDep
			)
		)

	) dependsOn(wicketScala)
}
