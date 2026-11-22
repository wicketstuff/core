package org.wicketstuff.scala

import org.apache.wicket.mock.MockApplication
import org.apache.wicket.protocol.http.WebApplication
import org.apache.wicket.util.tester.WicketTester
import org.scalatest.BeforeAndAfterAll
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.must.Matchers

/**
 *
 */
trait WicketSpec
  extends AnyFunSuite
  with BeforeAndAfterAll
  with Matchers {

  protected var tester: WicketTester = _

  override protected def beforeAll() = {
    super.beforeAll()

    tester = newWicketTester()
  }

  override protected def afterAll() = {
    tester.destroy()
    tester = null
    super.afterAll()
  }

  protected def newWicketTester(): WicketTester = {
    val application: WebApplication = newApplication()
    new WicketTester(application)
  }

  protected def newApplication(): WebApplication = {
    new MockApplication
  }

}
