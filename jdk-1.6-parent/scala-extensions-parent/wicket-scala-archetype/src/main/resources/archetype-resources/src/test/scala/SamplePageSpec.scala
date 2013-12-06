package ${package}

import org.apache.wicket.util.tester.WicketTester
import org.scalatest.FunSuite
import org.scalatest.{ShouldMatchers, FunSuite}

class SamplePageSpec
  extends FunSuite
  with ShouldMatchers {

  test("construct without error") {
    val wt = new WicketTester(new HelloWicketWorld)
    val p = wt.startPage(classOf[com.mycompany.HomePage])
    p should not be (null)
  }

}
