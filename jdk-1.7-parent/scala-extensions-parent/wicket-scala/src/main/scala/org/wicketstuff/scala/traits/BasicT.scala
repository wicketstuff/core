package org.wicketstuff.scala.traits

import org.apache.wicket.MarkupContainer
import org.apache.wicket.feedback.IFeedbackMessageFilter
import org.apache.wicket.markup.html.panel.FeedbackPanel
import org.apache.wicket.model.{IModel, Model}
import org.wicketstuff.scala.markup.html.ScalaWebMarkupContainer
import org.wicketstuff.scala.markup.html.basic.ScalaLabel

/**
 *
 */
trait BasicT
  extends ScalaComponentT {

  override val self: MarkupContainer = this match {
    case c: MarkupContainer => c
    case _ => null
  }

  def label[T <: Serializable](id: String, model: IModel[T] = new Model[T]()): ScalaLabel[T] = {
    val label = new ScalaLabel(id, model)
    self.add(label)
    label
  }

  type Init[T] = (ScalaWebMarkupContainer[T]) => Unit
  private[this] def emptyInit[T]: Init[T] = (_: ScalaWebMarkupContainer[T]) => ()

  def div[T <: Serializable](id: String, model: IModel[T] = new Model[T]())(implicit init: Init[T] = emptyInit): ScalaWebMarkupContainer[T] = {
    val container = new ScalaWebMarkupContainer[T](id, model)
    self.add(container)
    init(container)
    container
  }

  def span[T <: Serializable](id: String, model: IModel[T] = new Model[T]())(implicit init: Init[T] = emptyInit): ScalaWebMarkupContainer[T] = {
    div[T](id, model)(init)
  }

  def feedback(id: String, filter: IFeedbackMessageFilter = null): FeedbackPanel = {
    val feedback = new FeedbackPanel(id, filter)
    self.add(feedback)
    feedback
  }
}
