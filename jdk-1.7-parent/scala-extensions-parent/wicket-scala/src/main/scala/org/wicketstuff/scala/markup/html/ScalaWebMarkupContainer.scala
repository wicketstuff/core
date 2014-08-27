package org.wicketstuff.scala.markup.html

import org.apache.wicket.markup.html.WebMarkupContainer
import org.apache.wicket.model.IModel
import org.wicketstuff.scala.traits.ScalaMarkupContainerT

/**
 *
 */
class ScalaWebMarkupContainer[T](id: String, model: IModel[T] = null)
  extends WebMarkupContainer(id, model)
  with ScalaMarkupContainerT {

  override val self: ScalaWebMarkupContainer[T] = this

}
