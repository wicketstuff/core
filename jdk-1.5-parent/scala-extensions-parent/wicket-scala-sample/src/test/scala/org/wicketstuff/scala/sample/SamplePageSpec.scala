package org.wicketstuff.scala.sample

import org.specs._
import org.specs.runner._
import org.apache.wicket.util.tester.WicketTester

class SamplePageSpec extends SpecificationWithJUnit with ScalaTest {

  "HomePage" should {
    "construct without error" in {
      val wt = new WicketTester
      val p = wt.startPage(classOf[HomePage])
      p must notBeNull
    }
  }
  
}
