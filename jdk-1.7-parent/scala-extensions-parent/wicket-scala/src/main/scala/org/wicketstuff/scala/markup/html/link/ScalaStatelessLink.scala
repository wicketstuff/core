package org.wicketstuff.scala.markup.html.link

import org.apache.wicket.markup.html.link.StatelessLink
import org.wicketstuff.scala.ScalaMarkupContainer

class ScalaStatelessLink[T](id:String, f: ⇒ Unit)
  extends StatelessLink(id)
  with ScalaMarkupContainer {

  override def onClick(): Unit = f
}
