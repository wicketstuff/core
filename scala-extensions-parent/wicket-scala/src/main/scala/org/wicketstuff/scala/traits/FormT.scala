package org.wicketstuff.scala.traits

import org.apache.wicket.MarkupContainer
import org.apache.wicket.model.IModel
import org.wicketstuff.scala.markup.html.form.{ScalaNumberField, ScalaPasswordField, ScalaTextArea, ScalaTextField, ScalaStatelessForm, ScalaForm}

/**
 *
 */
trait FormT
  extends ScalaComponentT {

  override val self: MarkupContainer = this match {
    case c: MarkupContainer => c
    case _ => null
  }

  def form[T](id: String,
              model: IModel[T] = null,
              actions: Map[String, (ScalaForm[T]) => Unit] = Map.empty[String, (ScalaForm[T]) => Unit]): ScalaForm[T] = {
    val form = new ScalaForm[T](id, model, actions)
    self.add(form)
    form
  }

  def statelessForm[T](id: String,
                       model: IModel[T] = null,
                       actions: Map[String, (ScalaStatelessForm[T]) => Unit] = Map.empty[String, (ScalaStatelessForm[T]) => Unit]): ScalaStatelessForm[T] = {
    val form = new ScalaStatelessForm[T](id, model, actions)
    self.add(form)
    form
  }

  def text[T](id: String, model: IModel[T] = null): ScalaTextField[T] = {
    val text = new ScalaTextField[T](id, model)
    self.add(text)
    text
  }

  def textarea[T](id: String, model: IModel[T] = null): ScalaTextArea[T] = {
    val textarea = new ScalaTextArea[T](id, model)
    self.add(textarea)
    textarea
  }

  def password(id: String, model: IModel[String] = null): ScalaPasswordField = {
    val password = new ScalaPasswordField(id, model)
    self.add(password)
    password
  }

  def number[T <: Number with Comparable[T]](id: String, model: IModel[T] = null): ScalaNumberField[T] = {
    val number = new ScalaNumberField[T](id, model)
    self.add(number)
    number
  }

}
