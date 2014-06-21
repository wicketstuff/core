package org.wicketstuff.scala

import org.apache.wicket.markup.html.list.ListItem
import org.apache.wicket.model.IModel
import org.apache.wicket.{Component, MarkupContainer}
import org.wicketstuff.scala.markup.html.ScalaWebMarkupContainer
import org.wicketstuff.scala.markup.html.basic.ScalaLabel
import org.wicketstuff.scala.markup.html.form._
import org.wicketstuff.scala.markup.html.link.ScalaLink
import org.wicketstuff.scala.markup.html.list.ScalaListView

/**
 *
 */
trait ScalaMarkupContainer extends ScalaComponent {
  self: MarkupContainer =>

  def $plus(child: Component*): this.type = {
    add(child: _*)
    this
  }

  def $minus(child: Component): this.type = {
    remove(child)
    this
  }

  def label[T](id: String, model: IModel[T] = null): ScalaLabel[T] = {
    val label = new ScalaLabel(id, model)
    add(label)
    label
  }

  def div[T](id: String, model: IModel[T] = null): ScalaWebMarkupContainer[T] = {
    val div = new ScalaWebMarkupContainer[T](id, model)
    add(div)
    div
  }

  def form[T](id: String, model: IModel[T] = null, actions: Map[String, (ScalaForm[T]) => Unit] = Map.empty[String, (ScalaForm[T]) => Unit]): ScalaForm[T] = {
    val form = new ScalaForm[T](id, model, actions)
    add(form)
    form
  }

  def text[T](id: String, model: IModel[T] = null): ScalaTextField[T] = {
    val text = new ScalaTextField[T](id, model)
    add(text)
    text
  }

  def textarea[T](id: String, model: IModel[T] = null): ScalaTextArea[T] = {
    val textarea = new ScalaTextArea[T](id, model)
    add(textarea)
    textarea
  }

  def password(id: String, model: IModel[String] = null): ScalaPasswordField = {
    val password = new ScalaPasswordField(id, model)
    add(password)
    password
  }

  def number[T <: Number with Comparable[T]](id: String, model: IModel[T] = null): ScalaNumberField[T] = {
    val number = new ScalaNumberField[T](id, model)
    add(number)
    number
  }

  def link[T](id: String, onClickFunc: ⇒ Unit, model: IModel[T] = null): ScalaLink[T] = {
    val link = new ScalaLink[T](id, onClickFunc, model)
    add(link)
    link
  }

  def listView[T](id:String, list: IModel[java.util.List[T]], populateItemFunc:(ListItem[T]) ⇒ Unit): ScalaListView[T] = {
    val listView = new ScalaListView[T](id, list, populateItemFunc)
    add(listView)
    listView
  }

  def listView[T](id:String, list: java.util.List[T], populateItemFunc:(ListItem[T]) ⇒ Unit): ScalaListView[T] = {
    val listView = new ScalaListView[T](id, list, populateItemFunc)
    add(listView)
    listView
  }
}
