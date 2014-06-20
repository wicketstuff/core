package org.wicketstuff.scala

import org.apache.wicket.Component
import org.apache.wicket.ajax.{AjaxRequestTarget, AjaxEventBehavior}
import org.apache.wicket.ajax.form.{OnChangeAjaxBehavior, AjaxFormSubmitBehavior}

object ScalaComponent {
  implicit class ScalaComponentOps(component: Component) extends AnyVal {
    
  }
}

/**
 * An extension of Wicket's Component class
 */
trait ScalaComponent {
  self: Component =>

  private[this] def doNothing(target: AjaxRequestTarget) = (_: AjaxRequestTarget) => ()

  def updateable(): this.type = {
    self.setOutputMarkupId(true)
    this
  }

  def hide() = setVisibilityAllowed(false)
  def show() = setVisibilityAllowed(true)

  def on(eventName: String, onAction: (AjaxRequestTarget) => Unit)(implicit error: (AjaxRequestTarget) => Unit = doNothing): this.type = {
    eventName match {
      case "submit" =>
        add(new AjaxFormSubmitBehavior(eventName) {
          override def onSubmit(target: AjaxRequestTarget) = onAction(target)
          override def onError(target: AjaxRequestTarget) = error(target)
        })
        this
      case "change" =>
        add(new OnChangeAjaxBehavior {
          override def onUpdate(target: AjaxRequestTarget) = onAction(target)
        })
        this
      case _ =>
        add(new AjaxEventBehavior(eventName) {
          override def onEvent(target: AjaxRequestTarget) = onAction(target)
        })
        this
    }
  }
}
