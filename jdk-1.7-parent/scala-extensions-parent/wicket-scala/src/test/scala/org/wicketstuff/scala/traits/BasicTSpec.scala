package org.wicketstuff.scala.traits

import org.apache.wicket.model.Model
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.wicketstuff.scala._
import org.wicketstuff.scala.markup.html.ScalaWebMarkupContainer
import org.wicketstuff.scala.markup.html.basic.ScalaLabel

case class Person(name: String)

/**
 * Tests for methods defined at LinkT trait
 */
@RunWith(classOf[JUnitRunner])
class BasicTSpec
  extends WicketSpec {

  test("div/span without models") {
    val container = new ScalaWebMarkupContainer("container")
    container.div[Nothing]("div") { d1 =>
      d1.span[Nothing]("span")
    }
    tester.startComponentInPage(container, """<div wicket:id="container"><div wicket:id="div"><span wicket:id="span"></span></div></div>""")
    container get "div" mustBe an[ScalaWebMarkupContainer[_]]
    container get "div:span" mustBe an[ScalaWebMarkupContainer[_]]
  }

  test("div/span with models") {
    val container = new ScalaWebMarkupContainer("container")
    container.div("div", Model.of("Div")) { self =>
      self.label("label", Model.of("Label"))
    }
    tester.startComponentInPage(container, """<div wicket:id="container"><div wicket:id="div"><span wicket:id="label"></span></div></div>""")
    container get "div" mustBe an[ScalaWebMarkupContainer[_]]
    container get "div:label" mustBe an[ScalaLabel[_]]
  }

}
