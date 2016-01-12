package org.wicketstuff.scala

import org.apache.wicket.markup.html.{WebComponent, WebMarkupContainer}
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.wicketstuff.scala.traits.ScalaMarkupContainerT

/**
 * Tests for ScalaMarkupContainer
 */
@RunWith(classOf[JUnitRunner])
class ScalaMarkupContainerSpec
  extends WicketSpec {

  test("MarkupContainer#add() is aliased with operator +") {
    val parent = new WebMarkupContainer("parent") with ScalaMarkupContainerT
    val child = new WebComponent("child")

    parent.size() mustBe 0
    child.getParent mustBe null

    parent + child
    parent.size() mustBe 1
    child.getParent mustBe parent
  }

  test("MarkupContainer#remove() is aliased with operator -") {
    val parent = new WebMarkupContainer("parent") with ScalaMarkupContainerT
    val child = new WebComponent("child")

    parent + child
    parent.size() mustBe 1
    child.getParent mustBe parent

    parent - child
    parent.size() mustBe 0
    child.getParent mustBe null
  }
}
