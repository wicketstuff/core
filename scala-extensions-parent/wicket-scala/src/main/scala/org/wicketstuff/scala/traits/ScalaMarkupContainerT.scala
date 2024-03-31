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

  /**
   * An alias for MarkupContainer#add(Component...)
   *
   * @param children The children to add
   * @return this instance
   */
  def $plus(children: Component*): self.type = {
    self.add(children: _*)
    self
  }

  /**
   * An alias for MarkupContainer#remove(Component)
   *
   * @param child The children to remove
   * @return this instance
   */
  def $minus(child: Component): self.type = {
    self.remove(child)
    self
  }

  /**
   * An alias for MarkupContainer#addOrReplace()
   *
   * @param children The components to add or replace
   * @return this instance
   */
  def +||>>(children: Component*): self.type = {
    self.addOrReplace(children: _*)
    self
  }

  /**
   * An alias for MarkupContainer#replace(Component)
   *
   * @param child The replacement
   * @return this instance
   */
  def >>(child: Component): self.type = {
    self.replace(child)
    self
  }
}
