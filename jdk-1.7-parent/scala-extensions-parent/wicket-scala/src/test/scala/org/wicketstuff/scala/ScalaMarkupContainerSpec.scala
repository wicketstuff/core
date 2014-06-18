package org.wicketstuff.scala

import org.apache.wicket.markup.html.{WebComponent, WebMarkupContainer}

/**
 * Tests for ScalaMarkupContainer
 */
class ScalaMarkupContainerSpec extends WicketSpec {

  test("MarkupContainer#add() is overloaded with operator +") {
    val parent = new WebMarkupContainer("parent") with ScalaMarkupContainer
    val child = new WebComponent("child")

    parent.size() mustBe 0
    child.getParent mustBe null

    parent + child
    parent.size() mustBe 1
    child.getParent mustBe parent
  }

  test("MarkupContainer#remove() is overloaded with operator -") {
    val parent = new WebMarkupContainer("parent") with ScalaMarkupContainer
    val child = new WebComponent("child")

    parent + child
    parent.size() mustBe 1
    child.getParent mustBe parent

    parent - child
    parent.size() mustBe 0
    child.getParent mustBe null
  }
}
