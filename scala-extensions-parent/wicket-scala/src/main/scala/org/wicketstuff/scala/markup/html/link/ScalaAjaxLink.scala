package org.wicketstuff.scala.markup.html.link

import org.apache.wicket.ajax.AjaxRequestTarget
import org.apache.wicket.ajax.markup.html.AjaxLink
import org.apache.wicket.model.IModel
import org.wicketstuff.scala.traits.ScalaMarkupContainerT

class ScalaAjaxLink[T](id: String, model: IModel[T] = null, f: (AjaxRequestTarget) ⇒ Unit)
  extends AjaxLink[T](id, model)
  with ScalaMarkupContainerT {

  override val self: ScalaAjaxLink[T] = this

  override def onClick(target: AjaxRequestTarget): Unit = f(target)
}
