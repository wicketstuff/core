package org.wicketstuff.scala.markup.html.panel

import org.apache.wicket.markup.html.panel.GenericPanel
import org.apache.wicket.model.IModel
import org.wicketstuff.scala.ScalaMarkupContainer

/**
 *
 */
class ScalaPanel[T](id: String, model: IModel[T] = null)
  extends GenericPanel[T](id, model)
  with ScalaMarkupContainer
