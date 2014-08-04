package org.wicketstuff.scala.markup.html.link

import org.apache.wicket.markup.html.link.Link
import org.apache.wicket.model.IModel
import org.wicketstuff.scala.ScalaMarkupContainer

class ScalaLink[T](id:String, f: ⇒ Unit, model: IModel[T] = null)
  extends Link[T](id, model)
  with ScalaMarkupContainer {

  override val self: ScalaLink[T] = this

  override def onClick(): Unit = f
}
