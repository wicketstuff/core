import sbt._

class SampleProject(info: ProjectInfo) extends DefaultWebProject(info)
{
  val localRepo = "Local Maven Repository" at "file://"+Path.userHome+"/.m2/repository"

  val wicketScala = "org.wicketstuff.scala" % "wicket-scala" % "1.5-SNAPSHOT"
  val junit = "junit" % "junit" % "4.8.2" % "test->default"
  val jettyD = "org.mortbay.jetty" % "jetty" % "6.1.26" % "test->default"
  val servletD = "javax.servlet" % "servlet-api" % "2.5" 

  override def mainResources = super.mainResources +++ descendents(mainScalaSourcePath ##, -sourceExtensions)

}
