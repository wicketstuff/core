package org.wicketstuff.scala

import _root_.java.util.concurrent.atomic.AtomicBoolean

import org.apache.wicket.markup.html.{WebMarkupContainer, WebComponent}
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.wicketstuff.scala.traits.ScalaComponentT

/**
 * Tests for ScalaComponent
 */
@RunWith(classOf[JUnitRunner])
class ScalaComponentSpec
  extends WicketSpec {

  test("A (Java) component should have the extra methods from ScalaComponent imported implicitly") {
    val web = new WebComponent("web")
    web.hide()
    web.isVisible mustBe false
    web.show()
    web.isVisible mustBe true
  }

  test("A (Java) markup container should have the extra methods from ScalaMarkupContainer imported implicitly") {
    val web = new WebMarkupContainer("web")
    val label = web.label("label", "A child")
    label mustNot be (null)
    label.getDefaultModelObjectAsString mustBe "A child"
  }

  test("updateable should set outputMarkupId to true") {
    val component = new WebComponent("test") with ScalaComponentT
    component.getOutputMarkupId mustBe false
    component.updateable()
    component.getOutputMarkupId mustBe true
  }

  test("hide") {
    val component = new WebComponent("test") with ScalaComponentT
    component.isVisibleInHierarchy mustBe true
    component.hide()
    component.isVisibleInHierarchy mustBe false
  }

  test("show") {
    val component = new WebComponent("test") with ScalaComponentT
    component.isVisibleInHierarchy mustBe true
    component.hide()
    component.isVisibleInHierarchy mustBe false
    component.show()
    component.isVisibleInHierarchy mustBe true
  }

  test("on('click')") {
    val component = new WebComponent("test")
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
