package org.wicketstuff.scala.markup.html.form

import org.apache.wicket.markup.html.form.TextField
import org.apache.wicket.model.IModel
import org.wicketstuff.scala.ScalaMarkupContainer

/**
 *
 */
class ScalaTextField[T](id: String, model: IModel[T])
  extends TextField[T](id, model)
  with ScalaMarkupContainer {

  override val self: ScalaTextField[T] = this
}
