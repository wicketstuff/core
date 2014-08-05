package org.wicketstuff.scala.traits

import org.apache.wicket.Component

/**
 *
 */
trait ScalaMarkupContainerT
  extends ScalaComponentT
  with BasicT
  with FormT
  with LinkT
  with ListViewT {


  def $plus(child: Component*): self.type = {
    self.add(child: _*)
    self
  }

  def $minus(child: Component): self.type = {
    self.remove(child)
    self
  }

}
