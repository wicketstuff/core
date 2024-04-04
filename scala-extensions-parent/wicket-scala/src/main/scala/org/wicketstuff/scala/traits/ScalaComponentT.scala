package org.wicketstuff.scala.traits

import org.apache.wicket.Component
import org.apache.wicket.ajax.form.{AjaxFormValidatingBehavior, AjaxFormSubmitBehavior, OnChangeAjaxBehavior}
import org.apache.wicket.ajax.{AjaxEventBehavior, AjaxRequestTarget}
import org.wicketstuff.scala.model.ScalaModel

/**
 * An extension of Wicket's Component class
 */
trait ScalaComponentT
  extends ScalaModel {

  val self: Component = this match {
    case c: Component => c
    case _ => null
  }

  type OnError = (AjaxRequestTarget) => Unit

  protected def noOp() = () => ()

  protected def ajaxNoOp(target: AjaxRequestTarget) = (_: AjaxRequestTarget) => ()

  /**
   * An alias for Component#replaceWith()
   *
   * @param replacement The replacement
   * @return The replacement
   */
  def >>>(replacement: Component): replacement.type = {
    self.replaceWith(replacement)
    replacement
  }

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

  def on(eventName: String)(onAction: (AjaxRequestTarget) => Unit)(implicit error: OnError = ajaxNoOp): self.type = {
    eventName.toLowerCase match {
      case "submit" =>
        self.add(new AjaxFormSubmitBehavior("submit") {
          override def onSubmit(target: AjaxRequestTarget) = onAction(target)
          override def onError(target: AjaxRequestTarget) = error(target)
        })
      case "validate" =>
        self.add(new AjaxFormValidatingBehavior("submit") {
          override def onSubmit(target: AjaxRequestTarget) = onAction(target)
          override def onError(target: AjaxRequestTarget) = error(target)
        })
      case "change" =>
        self.add(new OnChangeAjaxBehavior {
          override def onUpdate(target: AjaxRequestTarget) = onAction(target)
          override def onError(target: AjaxRequestTarget, x: RuntimeException) = error(target)
        })
      case _ =>
        self.add(new AjaxEventBehavior(eventName) {
          override def onEvent(target: AjaxRequestTarget) = onAction(target)
        })
    }
    self
  }

  def css(): self.type = ???
}
