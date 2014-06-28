package org.wicketstuff.scala

import _root_.java.util.concurrent.atomic.AtomicBoolean

import org.apache.wicket.markup.html.WebComponent
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

/**
 * Tests for ScalaComponent
 */
@RunWith(classOf[JUnitRunner])
class ScalaComponentSpec
  extends WicketSpec {

  test("updateable should set outputMarkupId to true") {
    val component = new WebComponent("test") with ScalaComponent
    component.updateable()
    component.getOutputMarkupId mustBe true
  }

  test("hide") {
    val component = new WebComponent("test") with ScalaComponent
    component.isVisibleInHierarchy mustBe true
    component.hide()
    component.isVisibleInHierarchy mustBe false
  }

  test("show") {
    val component = new WebComponent("test") with ScalaComponent
    component.isVisibleInHierarchy mustBe true
    component.hide()
    component.isVisibleInHierarchy mustBe false
    component.show()
    component.isVisibleInHierarchy mustBe true
  }

  test("on('click')") {
    val component = new WebComponent("test") with ScalaComponent
    val clicked = new AtomicBoolean(false)
    component.on("click", { target =>
      clicked.set(true)
    })
    tester.startComponentInPage(component)
    clicked.get() mustBe false
    tester.executeAjaxEvent(component, "click")
    clicked.get() mustBe true
  }
}
