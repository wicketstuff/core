package org.wicketstuff.scala.model

import org.apache.wicket.util.time.Duration
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.wicketstuff.scala.WicketSpec

import scala.concurrent.TimeoutException
import scala.concurrent.duration._
import scala.util.{Failure, Try}

/**
 * Tests for ScalaModel
 */
@RunWith(classOf[JUnitRunner])
class ScalaModelSpec
  extends WicketSpec
  with ScalaModel {

  test("Loadable detachable model") {
    val model = ldM({1 + 2})
    model.getObject mustBe 3
  }

  test("Future model with simple result") {
    val model = futureM({1 + 2})
    model.getObject mustBe 3
  }

  test("Future model with timeout") {
    val model: FutureModel[String] = futureM({
      Duration.milliseconds(1500).sleep()
      "result"
    }, 500.millis)
    val result = Try(model.getObject)
    result match {
      case Failure(x) if x.isInstanceOf[TimeoutException] => true mustBe true
      case wrong: Any => fail(s"Wrong result: $wrong")
    }
  }
}
