package org.wicketstuff.scala.markup.html.form

import org.apache.wicket.markup.html.form.PasswordTextField
import org.apache.wicket.model.IModel
import org.wicketstuff.scala.ScalaMarkupContainer
import org.wicketstuff.scala.model.Fodel

/**
 *
 */
class ScalaPasswordField(id: String, model: IModel[String])
  extends PasswordTextField(id, model)
  with ScalaMarkupContainer {

  def this(id:String, getter: ⇒ String, setter:(String) ⇒ Unit) = this(id, new Fodel[String](getter, setter))
}
