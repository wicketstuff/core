import sbt._

class WicketScalaProject(info: ProjectInfo) extends DefaultWebProject(info)
{
  val localRepo = "Local Maven Repository" at "file://"+Path.userHome+"/.m2/repository"
  val wicketScala = "org.wicketstuff.scala" % "wicket-scala" % "1.4-SNAPSHOT"
  val junit = "junit" % "junit" % "4.6" % "test->default"
  val jettyD = "org.mortbay.jetty" % "jetty" % "6.1.11" % "test->default"

  val specsD = "org.scala-tools.testing" % "specs" % "1.5.0"  
  val scalaLibD = "org.scala-lang" % "scala-library" % "2.7.5"
  val scalaTestD = "org.scala-tools.testing" % "scalatest" % "0.9.5" % "test->default"
  val wicketD = "org.apache.wicket" % "wicket" % "1.4.0"
  val wicketExtD = "org.apache.wicket" % "wicket-extensions" % "1.4.0"
  val sevletApiD = "javax.servlet" % "servlet-api" % "2.4" % "test->default"
  val slf4jD = "org.slf4j" % "slf4j-log4j12" % "1.4.2"
  
  override def mainResources = super.mainResources +++ descendents(mainScalaSourcePath ##, -sourceExtensions)

}
