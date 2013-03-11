package org.wicketstuff.scala.sample

import org.apache.wicket.util.tester.WicketTester
import org.specs2.mutable.SpecificationWithJUnit

class SamplePageSpec
  extends SpecificationWithJUnit {

  "HomePage" should {
    "construct without error" in {
      val wt = new WicketTester
      val p = wt.startPage(classOf[HomePage])
      p must not beNull
    }
  }
  
}
