package org.wicketstuff.scala.traits

import org.apache.wicket.ajax.AjaxRequestTarget
import org.apache.wicket.request.mapper.parameter.PageParameters
import org.apache.wicket.{Page, MarkupContainer}
import org.apache.wicket.model.IModel
import org.wicketstuff.scala.markup.html.link.{ScalaAjaxFallbackLink, ScalaAjaxLink, ScalaBookmarkablePageLink, ScalaStatelessLink, ScalaLink}

/**
 *
 */
trait LinkT
  extends ScalaComponentT {

  override val self: MarkupContainer = this match {
    case c: MarkupContainer => c
    case _ => null
  }

  def link[T](id: String, model: IModel[T] = null)(f: ⇒ Unit): ScalaLink[T] = {
    val link = new ScalaLink[T](id, model, f)
    self.add(link)
    link
  }

  def statelessLink[T](id: String)(f: ⇒ Unit): ScalaStatelessLink[T] = {
    val link = new ScalaStatelessLink[T](id, f)
    self.add(link)
    link
  }

  def pageLink(id: String, clazz: Class[_ <: Page], parameters: PageParameters = null): ScalaBookmarkablePageLink = {
    val link = new ScalaBookmarkablePageLink(id, clazz, parameters)
    self.add(link)
    link
  }

  def ajaxLink[T](id: String, model: IModel[T] = null)(f: (AjaxRequestTarget) ⇒ Unit): ScalaAjaxLink[T] = {
    val link = new ScalaAjaxLink[T](id, model, f)
    self.add(link)
    link
  }

  def fallbackLink[T](id: String, model: IModel[T] = null)(f: (Option[AjaxRequestTarget]) ⇒ Unit): ScalaAjaxFallbackLink[T] = {
    val link = new ScalaAjaxFallbackLink[T](id, model, f)
    self.add(link)
    link
  }

}
