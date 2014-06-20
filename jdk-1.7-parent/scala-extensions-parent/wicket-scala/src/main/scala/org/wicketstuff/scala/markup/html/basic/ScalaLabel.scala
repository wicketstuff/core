package org.wicketstuff.scala.markup.html.basic

import org.apache.wicket.markup.html.basic.Label
import org.wicketstuff.scala.model.Fodel

/**
 *
 */
class ScalaLabel(id:String, getter: ⇒ String)
    extends Label(id, new Fodel(getter, null))
