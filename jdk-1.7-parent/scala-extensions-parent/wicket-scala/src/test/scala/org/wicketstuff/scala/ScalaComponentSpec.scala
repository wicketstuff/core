package org.wicketstuff.scala

import _root_.java.util.concurrent.atomic.AtomicBoolean

import org.apache.wicket.markup.html.{WebMarkupContainer, WebComponent}
import org.apache.wicket.model.Model
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

/**
 * Tests for ScalaComponent
 */
@RunWith(classOf[JUnitRunner])
class ScalaComponentSpec
  extends WicketSpec {

  test("An implicit component should have the extra methods from ScalaComponent") {
    val web = new WebComponent("web")
    web.hide()
    web.isVisibilityAllowed mustBe false
    web.show()
    web.isVisibilityAllowed mustBe true
  }

  test("An implicit markup container should have the extra methods from ScalaMarkupContainer") {
    val web = new WebMarkupContainer("web")
    val label = web.label("label", Model.of("A child"))
    label mustNot be (null)
    label.getDefaultModelObjectAsString mustBe "A child"
  }

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
