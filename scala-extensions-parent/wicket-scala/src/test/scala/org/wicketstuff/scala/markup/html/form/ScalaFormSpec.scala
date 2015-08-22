package org.wicketstuff.scala.markup.html.form

import java.util.concurrent.atomic.AtomicBoolean

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.wicketstuff.scala._

/**
 * Tests for ScalaForm
 */
@RunWith(classOf[JUnitRunner])
class ScalaFormSpec
  extends WicketSpec {

  test("submit a normal/java form") {

    val submitted = new AtomicBoolean(false)

    val form = new ScalaForm[Unit]("form") {
      override def onSubmit() = {
        super.onSubmit()
        submitted.set(true)
      }
    }

    tester.startComponentInPage(form, """<form wicket:id="form"></form>""")
    val formTester = tester.newFormTester("form")
    submitted.get() mustBe false
    formTester.submit()

    submitted.get() mustBe true
  }

  test("submit a scala form with submit action") {

    val submittedWithAction = new AtomicBoolean(false)

    val actions = Map("submit" -> { (theForm: ScalaForm[Unit]) =>
      submittedWithAction.set(true)
    })
    val form = new ScalaForm[Unit]("form", actions = actions)

    tester.startComponentInPage(form, """<form wicket:id="form"></form>""")
    val formTester = tester.newFormTester("form")
    submittedWithAction.get() mustBe false
    formTester.submit()

    submittedWithAction.get() mustBe true
  }

}
