package org.wicketstuff.scala.markup.html.basic

import org.apache.wicket.markup.html.basic.Label
import org.apache.wicket.model.IModel
import org.wicketstuff.scala.ScalaComponent

/**
 *
 */
class ScalaLabel[T](id: String, model: IModel[T] = null)
  extends Label(id, model)
  with ScalaComponent
