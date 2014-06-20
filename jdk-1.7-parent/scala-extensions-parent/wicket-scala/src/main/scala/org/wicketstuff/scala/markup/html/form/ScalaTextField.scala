package org.wicketstuff.scala.markup.html.form

import org.apache.wicket.markup.html.form.TextField
import org.wicketstuff.scala.model.Fodel

/**
 *
 */
class ScalaTextField[T](id: String, fodel: Fodel[T])
  extends TextField[T](id, fodel) {

  def this(id:String, getter: ⇒ T, setter:(T) ⇒ Unit) = this(id, new Fodel[T](getter, setter ) )

}
