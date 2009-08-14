import sbt._

class SampleProject(info: ProjectInfo) extends DefaultWebProject(info)
{
  val localRepoR = "Local Maven Repository" at "file://"+Path.userHome+"/.m2/repository"
  val wicketScalaD = "org.wicketstuff.scala" % "wicket-scala" % "1.4-SNAPSHOT"
  val wicketD = "org.apache.wicket" % "wicket" % "1.4.0"
  val junitD = "junit" % "junit" % "4.6" % "test->default"
  val jettyD = "org.mortbay.jetty" % "jetty" % "6.1.11" % "test->default"

  override def mainResources = super.mainResources +++ descendents(mainScalaSourcePath ##, -sourceExtensions)

}
