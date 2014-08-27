package org.wicketstuff.scala.markup.html.link

import org.apache.wicket.markup.html.link.StatelessLink
import org.wicketstuff.scala.traits.ScalaMarkupContainerT

class ScalaStatelessLink[T](id:String, f: ⇒ Unit)
  extends StatelessLink[T](id)
  with ScalaMarkupContainerT {

  override val self: ScalaStatelessLink[T] = this

  override def onClick(): Unit = f
}
