import sbt._

class SampleProject(info: ProjectInfo) extends DefaultWebProject(info)
{
  val localRepoR = "Local Maven Repository" at "file://"+Path.userHome+"/.m2/repository"
  val wicketScalaD = "org.wicketstuff.scala" % "wicket-scala" % "1.5-SNAPSHOT"
  //val wicketD = "org.apache.wicket" % "wicket-core" % "1.5-SNAPSHOT"
  val junitD = "junit" % "junit" % "4.8.2" % "test->default"
  val jettyD = "org.mortbay.jetty" % "jetty" % "6.1.26" % "test->default"

  override def mainResources = super.mainResources +++ descendents(mainScalaSourcePath ##, -sourceExtensions)

}
