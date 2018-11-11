package org.wicketstuff.scala.sample

import org.apache.wicket.util.tester.WicketTester
import org.scalatest.{MustMatchers, WordSpec}

class SamplePageSpec
  extends WordSpec
  with MustMatchers {

  "HomePage" should {
    "construct without error" in {
      val wt = new WicketTester
      val p = wt.startPage(classOf[HomePage])
      p mustNot be (null)
      wt.destroy()
    }
  }
  
}
