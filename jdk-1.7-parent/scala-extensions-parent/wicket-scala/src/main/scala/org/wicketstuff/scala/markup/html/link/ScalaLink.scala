package org.wicketstuff.scala.markup.html.link

import org.apache.wicket.markup.html.link.Link
import org.apache.wicket.model.IModel
import org.wicketstuff.scala.traits.ScalaMarkupContainerT

class ScalaLink[T](id:String, model: IModel[T] = null, f: ⇒ Unit)
  extends Link[T](id, model)
  with ScalaMarkupContainerT {

  override val self: ScalaLink[T] = this

  override def onClick(): Unit = f
}
