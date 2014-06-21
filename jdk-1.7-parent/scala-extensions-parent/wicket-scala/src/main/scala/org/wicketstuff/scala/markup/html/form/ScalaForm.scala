package org.wicketstuff.scala.markup.html.form

import org.apache.wicket.markup.html.form.Form
import org.apache.wicket.model.IModel
import org.wicketstuff.scala.ScalaMarkupContainer

/**
 *
 */
class ScalaForm[T](id: String,
                   model: IModel[T] = null,
                   actions: Map[String, (ScalaForm[T]) => Unit] = Map.empty[String, (ScalaForm[T]) => Unit])
  extends Form[T](id, model)
  with ScalaMarkupContainer {

  override def onSubmit(): Unit = actions.getOrElse("submit", thisToUnit _)(ScalaForm.this)

  override def onError(): Unit = actions.getOrElse("error", thisToUnit _)(this)

  protected def thisToUnit(typ: ScalaForm[T]) = (_: ScalaForm[T]) => ()
}
