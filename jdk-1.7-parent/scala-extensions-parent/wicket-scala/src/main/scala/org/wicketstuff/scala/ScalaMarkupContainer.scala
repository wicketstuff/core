package org.wicketstuff.scala

import _root_.java.util.{List => JList}

import org.apache.wicket.ajax.AjaxRequestTarget
import org.apache.wicket.feedback.IFeedbackMessageFilter
import org.apache.wicket.markup.html.list.ListItem
import org.apache.wicket.markup.html.panel.FeedbackPanel
import org.apache.wicket.model.IModel
import org.apache.wicket.request.mapper.parameter.PageParameters
import org.apache.wicket.{Component, MarkupContainer, Page}
import org.wicketstuff.scala.markup.html.ScalaWebMarkupContainer
import org.wicketstuff.scala.markup.html.basic.ScalaLabel
import org.wicketstuff.scala.markup.html.form.{ScalaForm, ScalaNumberField, ScalaPasswordField, ScalaStatelessForm, ScalaTextArea, ScalaTextField}
import org.wicketstuff.scala.markup.html.link.{ScalaAjaxFallbackLink, ScalaAjaxLink, ScalaBookmarkablePageLink, ScalaLink, ScalaStatelessLink}
import org.wicketstuff.scala.markup.html.list.ScalaListView

/**
 *
 */
trait ScalaMarkupContainer extends ScalaComponent {

  override val self: MarkupContainer = this match {
    case c: MarkupContainer => c
    case _ => null
  }

  def $plus(child: Component*): self.type = {
    self.add(child: _*)
    self
  }

  def $minus(child: Component): self.type = {
    self.remove(child)
    self
  }

  def label[T](id: String, model: IModel[T] = null): ScalaLabel[T] = {
    val label = new ScalaLabel(id, model)
    self.add(label)
    label
  }

  def div[T](id: String, model: IModel[T] = null): ScalaWebMarkupContainer[T] = {
    val div = new ScalaWebMarkupContainer[T](id, model)
    self.add(div)
    div
  }

  def form[T](id: String, model: IModel[T] = null, actions: Map[String, (ScalaForm[T]) => Unit] = Map.empty[String, (ScalaForm[T]) => Unit]): ScalaForm[T] = {
    val form = new ScalaForm[T](id, model, actions)
    self.add(form)
    form
  }

  def statelessForm[T](id: String,
                       model: IModel[T] = null,
                       actions: Map[String, (ScalaStatelessForm[T]) => Unit] = Map.empty[String, (ScalaStatelessForm[T]) => Unit]): ScalaStatelessForm[T] = {
    val form = new ScalaStatelessForm[T](id, model, actions)
    self.add(form)
    form
  }

  def text[T](id: String, model: IModel[T] = null): ScalaTextField[T] = {
    val text = new ScalaTextField[T](id, model)
    self.add(text)
    text
  }

  def textarea[T](id: String, model: IModel[T] = null): ScalaTextArea[T] = {
    val textarea = new ScalaTextArea[T](id, model)
    self.add(textarea)
    textarea
  }

  def password(id: String, model: IModel[String] = null): ScalaPasswordField = {
    val password = new ScalaPasswordField(id, model)
    self.add(password)
    password
  }

  def number[T <: Number with Comparable[T]](id: String, model: IModel[T] = null): ScalaNumberField[T] = {
    val number = new ScalaNumberField[T](id, model)
    self.add(number)
    number
  }

  def link[T](id: String, f: ⇒ Unit, model: IModel[T] = null): ScalaLink[T] = {
    val link = new ScalaLink[T](id, f, model)
    self.add(link)
    link
  }

  def statelessLink[T](id: String, f: ⇒ Unit): ScalaStatelessLink[T] = {
    val link = new ScalaStatelessLink[T](id, f)
    self.add(link)
    link
  }

  def pageLink(id: String, clazz: Class[_ <: Page], parameters: PageParameters = null): ScalaBookmarkablePageLink = {
    val link = new ScalaBookmarkablePageLink(id, clazz, parameters)
    self.add(link)
    link
  }

  def ajaxLink[T](id: String, f: (AjaxRequestTarget) ⇒ Unit, model: IModel[T] = null): ScalaAjaxLink[T] = {
    val link = new ScalaAjaxLink[T](id, f, model)
    self.add(link)
    link
  }

  def fallbackLink[T](id: String, f: (Option[AjaxRequestTarget]) ⇒ Unit, model: IModel[T] = null): ScalaAjaxFallbackLink[T] = {
    val link = new ScalaAjaxFallbackLink[T](id, f, model)
    self.add(link)
    link
  }

  def listView[T](id:String, list: IModel[JList[T]], populateItemFunc:(ListItem[T]) ⇒ Unit): ScalaListView[T] = {
    val listView = new ScalaListView[T](id, list, populateItemFunc)
    self.add(listView)
    listView
  }

  def listView[T](id:String, list: JList[T], populateItemFunc:(ListItem[T]) ⇒ Unit): ScalaListView[T] = {
    val listView = new ScalaListView[T](id, list, populateItemFunc)
    self.add(listView)
    listView
  }

  def feedback(id: String, filter: IFeedbackMessageFilter = null): FeedbackPanel = {
    val feedback = new FeedbackPanel(id, filter)
    self.add(feedback)
    feedback
  }
}
