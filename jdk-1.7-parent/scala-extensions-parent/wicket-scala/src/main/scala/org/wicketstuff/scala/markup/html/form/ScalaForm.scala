package org.wicketstuff.scala.markup.html.form

import org.apache.wicket.markup.html.form.Form
import org.apache.wicket.model.IModel

/**
 *
 */
class ScalaForm[T](id:String, model:IModel[T], onSubmitFunc: ⇒ Unit)
  extends Form[T](id, model:IModel[T]) {

  override def onSubmit(): Unit = onSubmitFunc
}
