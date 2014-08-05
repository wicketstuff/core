package org.wicketstuff.scala.traits

import org.apache.wicket.MarkupContainer
import org.apache.wicket.feedback.IFeedbackMessageFilter
import org.apache.wicket.markup.html.panel.FeedbackPanel
import org.apache.wicket.model.IModel
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

  def feedback(id: String, filter: IFeedbackMessageFilter = null): FeedbackPanel = {
    val feedback = new FeedbackPanel(id, filter)
    self.add(feedback)
    feedback
  }
}
