package org.wicketstuff.scala.traits

import _root_.java.util.{List => JList}

import org.apache.wicket.MarkupContainer
import org.apache.wicket.markup.html.list.ListItem
import org.apache.wicket.model.IModel
import org.wicketstuff.scala.markup.html.list.ScalaListView

/**
 *
 */
trait ListViewT
  extends ScalaComponentT {

  override val self: MarkupContainer = this match {
    case c: MarkupContainer => c
    case _ => null
  }

  def listView[T](id:String, list: IModel[JList[T]], populateItemFunc:(ListItem[T]) ⇒ Unit): ScalaListView[T] = {
    val listView = new ScalaListView[T](id, list, populateItemFunc)
    self.add(listView)
    listView
  }

  def listView[T](id:String, list: JList[T], populateItemFunc:(ListItem[T]) ⇒ Unit): ScalaListView[T] = {
    val listView = new ScalaListView[T](id, list, populateItemFunc)
    self.add(listView)
    listView
  }

}
