package org.wicketstuff.scala.markup.html.link

import org.apache.wicket.ajax.AjaxRequestTarget
import org.apache.wicket.ajax.markup.html.AjaxLink
import org.apache.wicket.model.IModel
import org.wicketstuff.scala.ScalaMarkupContainer

class ScalaAjaxLink[T](id: String, f: (AjaxRequestTarget) ⇒ Unit, model: IModel[T] = null)
  extends AjaxLink[T](id, model)
  with ScalaMarkupContainer {

  override val self: ScalaAjaxLink[T] = this

  override def onClick(target: AjaxRequestTarget): Unit = f(target)
}
