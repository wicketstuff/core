package org.wicketstuff.scala

import java.util.concurrent.atomic.AtomicBoolean

import org.apache.wicket.MarkupContainer
import org.apache.wicket.ajax.AjaxRequestTarget
import org.apache.wicket.markup.IMarkupResourceStreamProvider
import org.apache.wicket.markup.html.{WebPage, WebComponent}
import org.apache.wicket.util.resource.StringResourceStream

/**
 * Tests for ScalaComponent
 */
class ScalaComponentSpec extends WicketSpec {

  test("updateable should set outputMarkupId to true") {
    val component = new WebComponent("test") with ScalaComponent
    component.updateable
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
//    val page = new TestPage
    val component = new WebComponent("test") with ScalaComponent
//    page + component
    val clicked = new AtomicBoolean(false)
    component.on("click", (target:AjaxRequestTarget) => {
      clicked.set(true)
    })
    tester.startComponentInPage(component)
    tester.executeAjaxEvent(component, "click")
    clicked.get() mustBe true
  }

  class TestPage extends WebPage with IMarkupResourceStreamProvider with ScalaMarkupContainer {
    override def getMarkupResourceStream(container: MarkupContainer, containerClass: Class[_]) = {
      new StringResourceStream("""<body><div wicket:id="test"></div></body>""")
    }
  }
}
