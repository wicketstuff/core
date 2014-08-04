package org.wicketstuff.scala

import org.apache.wicket.Component
import org.apache.wicket.ajax.{AjaxRequestTarget, AjaxEventBehavior}
import org.apache.wicket.ajax.form.{OnChangeAjaxBehavior, AjaxFormSubmitBehavior}
import org.apache.wicket.markup.head.{JavaScriptHeaderItem, JavaScriptReferenceHeaderItem, CssHeaderItem, CssReferenceHeaderItem}
import org.apache.wicket.request.resource.{JavaScriptResourceReference, CssResourceReference}
import org.wicketstuff.scala.model.ScalaModel
import scala.language.implicitConversions

/**
 * An extension of Wicket's Component class
 */
trait ScalaComponent
  extends ScalaModel {

  val self:Component = this match {
    case c: Component => c
    case _ => null
  }

  protected def noOp() = () => ()
  private[this] def doNothing(target: AjaxRequestTarget) = (_: AjaxRequestTarget) => ()

  implicit def cssRefToHeaderItem(reference: CssResourceReference): CssReferenceHeaderItem = CssHeaderItem.forReference(reference)
  implicit def jsRefToHeaderItem(reference: JavaScriptResourceReference): JavaScriptReferenceHeaderItem =
    JavaScriptHeaderItem.forReference(reference)

  def updateable(): self.type = {
    self.setOutputMarkupId(true)
    self
  }

  def hide(): self.type = {
    self.setVisibilityAllowed(false)
    self
  }

  def show(): self.type = {
    self.setVisibilityAllowed(true)
    self
  }

  def on(eventName: String, onAction: (AjaxRequestTarget) => Unit)(implicit error: (AjaxRequestTarget) => Unit = doNothing): self.type = {
    eventName match {
      case "submit" =>
        self.add(new AjaxFormSubmitBehavior(eventName) {
          override def onSubmit(target: AjaxRequestTarget) = onAction(target)
          override def onError(target: AjaxRequestTarget) = error(target)
        })
      case "change" =>
        self.add(new OnChangeAjaxBehavior {
          override def onUpdate(target: AjaxRequestTarget) = onAction(target)
        })
      case _ =>
        self.add(new AjaxEventBehavior(eventName) {
          override def onEvent(target: AjaxRequestTarget) = onAction(target)
        })
    }
    self
  }
}
