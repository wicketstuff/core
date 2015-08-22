package org.wicketstuff.scala.markup.html.form

import org.apache.wicket.markup.html.form.StatelessForm
import org.apache.wicket.model.IModel
import org.wicketstuff.scala.traits.ScalaMarkupContainerT

/**
 *
 */
class ScalaStatelessForm[T](id: String,
                   model: IModel[T] = null,
                   actions: Map[String, (ScalaStatelessForm[T]) => Unit] = Map.empty[String, (ScalaStatelessForm[T]) => Unit])
  extends StatelessForm[T](id, model)
  with ScalaMarkupContainerT {

  override val self: ScalaStatelessForm[T] = this

  override def onSubmit(): Unit = actions.get("submit").fold(super.onSubmit())(_(ScalaStatelessForm.this))

  override def onError(): Unit = actions.get("error").fold(super.onError())(_(ScalaStatelessForm.this))

}
