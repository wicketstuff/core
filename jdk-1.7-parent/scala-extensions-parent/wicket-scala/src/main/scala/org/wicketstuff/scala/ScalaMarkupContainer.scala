package org.wicketstuff.scala

import org.apache.wicket.{Component, MarkupContainer}

/**
 *
 */
trait ScalaMarkupContainer extends ScalaComponent {
  self: MarkupContainer =>

  def $plus(child: Component*): this.type = {
    add(child: _*)
    this
  }

  def $minus(child: Component): this.type = {
    remove(child)
    this
  }

}
