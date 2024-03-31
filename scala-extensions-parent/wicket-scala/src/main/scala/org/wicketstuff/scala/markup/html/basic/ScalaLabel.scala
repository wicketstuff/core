package org.wicketstuff.scala.markup.html.basic

import org.apache.wicket.markup.html.basic.Label
import org.apache.wicket.model.IModel
import org.wicketstuff.scala.traits.ScalaComponentT

/**
 *
 */
class ScalaLabel[T](id: String, model: IModel[T] = null)
  extends Label(id, model)
  with ScalaComponentT {

  override val self: ScalaLabel[T] = this

}
