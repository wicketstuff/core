package org.wicketstuff.scala

import org.apache.wicket.util.tester.WicketTester
import org.scalatest.{MustMatchers, BeforeAndAfterAll, FunSuite}

/**
 *
 */
trait WicketSpec extends FunSuite with BeforeAndAfterAll with MustMatchers {

  protected var tester: WicketTester = _

  override protected def beforeAll() = {
    super.beforeAll()

    tester = new WicketTester()
  }

  override protected def afterAll() = {
    tester.destroy()
    tester = null
    super.afterAll()
  }
}
