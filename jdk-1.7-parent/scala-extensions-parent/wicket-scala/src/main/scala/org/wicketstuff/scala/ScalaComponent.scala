package org.wicketstuff.scala

import org.apache.wicket.Component
import org.apache.wicket.ajax.form.{AjaxFormSubmitBehavior, OnChangeAjaxBehavior}
import org.apache.wicket.ajax.{AjaxEventBehavior, AjaxRequestTarget}
import org.wicketstuff.scala.model.ScalaModel

/**
 * An extension of Wicket's Component class
 */
trait ScalaComponent
  extends ScalaModel {

  val self: Component = this match {
    case c: Component => c
    case _ => null
  }

  protected def noOp() = () => ()
  private[this] def doNothing(target: AjaxRequestTarget) = (_: AjaxRequestTarget) => ()

  def updateable(): self.type = {
    self.setOutputMarkupId(true)
    self
  }

  def hide(): self.type = {
    self.setVisible(false)
    self
  }

  def show(): self.type = {
    self.setVisible(true)
    self
  }

  def on(eventName: String, onAction: (AjaxRequestTarget) => Unit)(implicit error: (AjaxRequestTarget) => Unit = doNothing): self.type = {
    eventName.toLowerCase match {
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
