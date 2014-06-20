package org.wicketstuff.scala

import org.apache.wicket.markup.html.list.ListItem
import org.apache.wicket.model.IModel
import org.apache.wicket.{Component, MarkupContainer}
import org.wicketstuff.scala.markup.html.ScalaWebMarkupContainer
import org.wicketstuff.scala.markup.html.form.{ScalaNumberField, ScalaTextArea, ScalaTextField}
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

  def div[T](id: String, model: IModel[T] = null): ScalaWebMarkupContainer[T] = {
    new ScalaWebMarkupContainer[T](id, model)
  }

  def text[T](id: String, model: IModel[T] = null): ScalaTextField[T] = {
    new ScalaTextField[T](id, model)
  }

  def textarea[T](id: String, model: IModel[T] = null): ScalaTextArea[T] = {
    new ScalaTextArea[T](id, model)
  }

  def password[T](id: String, model: IModel[T] = null): ScalaTextField[T] = {
    new ScalaTextField[T](id, model)
  }

  def number[T](id: String, model: IModel[T] = null): ScalaNumberField[T] = {
    new ScalaNumberField[T](id, model)
  }

  def link[T](id: String, onClickFunc: ⇒ Unit, model: IModel[T] = null): ScalaLink[T] = {
    new ScalaLink[T](id, onClickFunc, model)
  }

  def listView[T](id:String, list: IModel[java.util.List[T]], populateItemFunc:(ListItem[T]) ⇒ Unit): ScalaListView[T] = {
    new ScalaListView[T](id, list, populateItemFunc)
  }

  def listView[T](id:String, list: java.util.List[T], populateItemFunc:(ListItem[T]) ⇒ Unit): ScalaListView[T] = {
    new ScalaListView[T](id, list, populateItemFunc)
  }
}
