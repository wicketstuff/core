package org.wicketstuff.scala.markup.html.form

import org.apache.wicket.markup.html.form.NumberTextField
import org.apache.wicket.model.IModel
import org.wicketstuff.scala.model.Fodel
import org.wicketstuff.scala.traits.ScalaMarkupContainerT

/**
 *
 */
class ScalaNumberField[T <: Number with Comparable[T]](id: String, model: IModel[T])
  extends NumberTextField[T](id, model)
  with ScalaMarkupContainerT {

  override val self: ScalaNumberField[T] = this

  def this(id:String, getter: ⇒ T, setter:(T) ⇒ Unit) = this(id, new Fodel[T](getter, setter))
}
