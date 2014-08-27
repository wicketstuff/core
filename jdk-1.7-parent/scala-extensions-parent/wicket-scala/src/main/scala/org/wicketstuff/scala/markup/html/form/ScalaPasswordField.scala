package org.wicketstuff.scala.markup.html.form

import org.apache.wicket.markup.html.form.PasswordTextField
import org.apache.wicket.model.IModel
import org.wicketstuff.scala.model.Fodel
import org.wicketstuff.scala.traits.ScalaMarkupContainerT

/**
 *
 */
class ScalaPasswordField(id: String, model: IModel[String])
  extends PasswordTextField(id, model)
  with ScalaMarkupContainerT {

  override val self: ScalaPasswordField = this

  def this(id:String, getter: ⇒ String, setter:(String) ⇒ Unit) = this(id, new Fodel[String](getter, setter))
}
