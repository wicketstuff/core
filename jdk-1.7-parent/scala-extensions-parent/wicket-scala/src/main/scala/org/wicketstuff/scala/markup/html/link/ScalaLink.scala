package org.wicketstuff.scala.markup.html.link

import org.apache.wicket.markup.html.link.Link

class ScalaLink(id:String, onClickFunc: ⇒ Unit)
  extends Link(id) {

  override def onClick(): Unit = onClickFunc
}
