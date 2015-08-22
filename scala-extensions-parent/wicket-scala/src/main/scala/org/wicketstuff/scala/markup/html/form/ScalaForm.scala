package org.wicketstuff.scala.markup.html.form

import org.apache.wicket.markup.html.form.Form
import org.apache.wicket.model.IModel
import org.wicketstuff.scala.traits.ScalaMarkupContainerT

/**
 *
 */
class ScalaForm[T](id: String,
                   model: IModel[T] = null,
                   actions: Map[String, (ScalaForm[T]) => Unit] = Map.empty[String, (ScalaForm[T]) => Unit])
  extends Form[T](id, model)
  with ScalaMarkupContainerT {

  override val self: ScalaForm[T] = this

  override def onSubmit(): Unit = actions.get("submit").fold(super.onSubmit())(_(ScalaForm.this))

  override def onError(): Unit = actions.get("error").fold(super.onError())(_(ScalaForm.this))

}
