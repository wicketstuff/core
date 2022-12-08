import sbt._
import sbt.Keys._
import Project.Initialize

object WicketScalaParent extends Build
{
	val WicketVersion = "6.13.0-SNAPSHOT"
	val Organization  = "org.wicketstuff"
	val ScalaVersion  = "2.10.3"
	val ScalacOptions = Seq("-deprecation", "-unchecked", "-optimize", "-uniqid",
		"-no-specialization", "-encoding", "UTF-8")

	val ScalaCheckDep = "org.scalacheck" % "scalacheck_2.10" % "1.11.1"
	val JUnitDep      = "junit" % "junit" % "4.11" % "test->default"
	val Specs1Dep     = "org.specs2" % "specs2_2.10" % "1.14" % "test->default"
	val ServletDep    = "javax.servlet" % "servlet-api" % "2.5"
	val Slf4jLog4jDep = "org.slf4j" % "slf4j-reload4j" % "2.0.5"
	val WicketDep     = "org.apache.wicket" % "wicket-core" % WicketVersion
	val WicketExtensionsDep = "org.apache.wicket" % "wicket-extensions" % WicketVersion
	val WicketDatetimeDep = "org.apache.wicket" % "wicket-datetime" % WicketVersion
	val ScalaTestDep  = "org.scalatest" % "scalatest_2.10" % "2.0" % "test->default"

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
			scalacOptions ++= ScalacOptions,

			libraryDependencies ++= Seq(
				ScalaCheckDep,
				JUnitDep,
				Specs1Dep,
				ScalaTestDep,
				WicketDep,
				WicketExtensionsDep,
				WicketDatetimeDep,
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
			scalacOptions ++= ScalacOptions,

			libraryDependencies ++= Seq(
				ScalaTestDep,
				Specs1Dep,
				ScalaCheckDep,
				JUnitDep,
				ServletDep
			)
		)

	) dependsOn(wicketScala)
}
