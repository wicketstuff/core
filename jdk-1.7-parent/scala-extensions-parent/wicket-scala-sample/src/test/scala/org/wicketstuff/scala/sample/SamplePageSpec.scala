package org.wicketstuff.scala.sample

import org.apache.wicket.util.tester.WicketTester
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{MustMatchers, WordSpec}

@RunWith(classOf[JUnitRunner])
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
