package org.wicketstuff.scala.traits

import java.util.concurrent.atomic.AtomicBoolean

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.wicketstuff.scala.markup.html.ScalaWebMarkupContainer
import org.wicketstuff.scala._

/**
 * Tests for methods defined at LinkT trait
 */
@RunWith(classOf[JUnitRunner])
class LinkTSpec
  extends WicketSpec {

  test("link") {
    val executed = new AtomicBoolean(false)
    val container = new ScalaWebMarkupContainer[Unit]("container")
    val link = container.link("link") {
      executed.set(true)
    }
    tester.startComponentInPage(container, """<div wicket:id="container"><a wicket:id="link">Link</a></div>""")
    val isAjax = false
    executed.get() mustBe false
    tester.clickLink(link.getPageRelativePath, isAjax)
    executed.get() mustBe true
  }

  test("statelessLink") {
    val executed = new AtomicBoolean(false)
    val container = new ScalaWebMarkupContainer[Unit]("container")
    val link = container.statelessLink("link") {
      executed.set(true)
    }
    tester.startComponentInPage(container, """<div wicket:id="container"><a wicket:id="link">Link</a></div>""")
    val isAjax = false
    executed.get() mustBe false
    tester.clickLink(link.getPageRelativePath, isAjax)
    executed.get() mustBe true
  }

  test("ajaxLink") {
    val executed = new AtomicBoolean(false)
    val container = new ScalaWebMarkupContainer[Unit]("container")
    val link = container.ajaxLink("link") { target =>
      executed.set(true)
    }
    tester.startComponentInPage(container, """<div wicket:id="container"><a wicket:id="link">Link</a></div>""")
    val isAjax = true
    executed.get() mustBe false
    tester.clickLink(link.getPageRelativePath, isAjax)
    executed.get() mustBe true
  }

  test("fallbackLink - Ajax mode") {
    val isSome = new AtomicBoolean(false)
    val container = new ScalaWebMarkupContainer[Unit]("container")
    val link = container.fallbackLink("link") { targetOpt =>
      isSome.set(targetOpt.isDefined)
    }
    tester.startComponentInPage(container, """<div wicket:id="container"><a wicket:id="link">Link</a></div>""")
    val isAjax = true
    isSome.get() mustBe false
    tester.clickLink(link.getPageRelativePath, isAjax)
    isSome.get() mustBe true
  }

  test("fallbackLink - non-Ajax mode") {
    val isSome = new AtomicBoolean(true)
    val container = new ScalaWebMarkupContainer[Unit]("container")
    val link = container.fallbackLink("link") { targetOpt =>
      isSome.set(targetOpt.isDefined)
    }
    tester.startComponentInPage(container, """<div wicket:id="container"><a wicket:id="link">Link</a></div>""")
    val isAjax = false
    isSome.get() mustBe true
    tester.clickLink(link.getPageRelativePath, isAjax)
    isSome.get() mustBe false
  }
}
