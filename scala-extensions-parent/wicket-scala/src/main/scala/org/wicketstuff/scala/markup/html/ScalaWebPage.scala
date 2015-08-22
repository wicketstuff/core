package org.wicketstuff.scala.markup.html

import org.apache.wicket.markup.html.WebPage
import org.apache.wicket.request.mapper.parameter.PageParameters
import org.wicketstuff.scala.traits.ScalaMarkupContainerT

/**
 *
 */
class ScalaWebPage(parameters: PageParameters)
  extends WebPage(parameters)
  with ScalaMarkupContainerT {

  override val self: ScalaWebPage = this

  def this() = this(new PageParameters())
}
