package org.wicketstuff.scala.markup.html.link

import org.apache.wicket.markup.html.link.Link
import org.apache.wicket.model.IModel
import org.wicketstuff.scala.ScalaMarkupContainer

class ScalaLink[T](id:String, onClickFunc: ⇒ Unit, model: IModel[T] = null)
  extends Link(id, model)
  with ScalaMarkupContainer {

  override def onClick(): Unit = onClickFunc
}
