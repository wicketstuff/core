package org.wicketstuff.scala.markup.html.list

import org.apache.wicket.markup.html.list.{ListView, ListItem}

/**
 *
 */
class ScalaListView[T](id:String, list:java.util.List[T], populateItemFunc:(ListItem[T]) ⇒ Unit)
    extends ListView[T](id, list) {

    override def populateItem(li:ListItem[T]) = populateItemFunc(li)
  }
