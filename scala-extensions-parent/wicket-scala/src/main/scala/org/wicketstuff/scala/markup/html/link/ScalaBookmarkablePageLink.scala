package org.wicketstuff.scala.markup.html.link

import org.apache.wicket.Page
import org.apache.wicket.markup.html.link.BookmarkablePageLink
import org.apache.wicket.request.mapper.parameter.PageParameters
import org.wicketstuff.scala.traits.ScalaMarkupContainerT

class ScalaBookmarkablePageLink(id:String, clazz: Class[_ <: Page], parameters: PageParameters = null)
  extends BookmarkablePageLink[Unit](id, clazz, parameters)
  with ScalaMarkupContainerT {

  override val self: ScalaBookmarkablePageLink = this
}
