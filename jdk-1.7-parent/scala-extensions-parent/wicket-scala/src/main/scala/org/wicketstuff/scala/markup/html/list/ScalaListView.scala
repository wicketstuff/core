package org.wicketstuff.scala.markup.html.list

import org.apache.wicket.markup.html.list.{ListItem, ListView}
import org.apache.wicket.model.IModel
import org.apache.wicket.model.util.ListModel
import org.wicketstuff.scala.traits.ScalaMarkupContainerT

/**
 *
 */
class ScalaListView[T](id: String,
                       list: IModel[java.util.List[T]],
                       populateItemFunc:(ListItem[T]) ⇒ Unit)
  extends ListView[T](id, list)
  with ScalaMarkupContainerT {

  override val self: ScalaListView[T] = this

  def this(id:String, list: java.util.List[T], populateItemFunc:(ListItem[T]) ⇒ Unit) =
    this(id, new ListModel[T](list), populateItemFunc)

  def this(id:String, populateItemFunc:(ListItem[T]) ⇒ Unit) =
    this(id, null.asInstanceOf[java.util.List[T]], populateItemFunc)

  override def populateItem(li:ListItem[T]) = populateItemFunc(li)
}
