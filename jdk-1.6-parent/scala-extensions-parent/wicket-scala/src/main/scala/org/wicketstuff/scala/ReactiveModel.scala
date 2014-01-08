package org.wicketstuff.scala

import org.apache.wicket.model.{IModel, Model}

/**
 *
 */
class ReactiveModel[T <: Serializable]
  extends Model[T] {

  def +(model: IModel[T]): ReactiveModel[T] = {
    new ReactiveModel[T] {
      private var hasCustomObject = false

      override def getObject = {
        if (hasCustomObject) super.getObject
        else (super.getObject.toString + model.getObject.toString).asInstanceOf[T]
      }

      override def setObject(obj: T) = {
        super.setObject(obj)
        hasCustomObject = true
      }

      override def detach() = {
        super.detach()
        if (!hasCustomObject) model.detach()
      }
    }
  }
}
