package org.wicketstuff.scala

import org.apache.wicket.model.{IModel, Model}

/**
 *
 */
class ReactiveModel[T <: java.io.Serializable](obj: T)
  extends Model[T](obj) {

  def this() = this(null.asInstanceOf[T])

  def +(right: IModel[T])(implicit ev: Plus[T]): ReactiveModel[T] = {
    val left = this

    new ReactiveModel[T](getObject) {
      private var hasCustomObject = false

      override def getObject = {
        if (hasCustomObject) left.getObject
        else ev.plus(left.getObject, right.getObject)
      }

      override def setObject(obj: T) = {
        left.setObject(obj)
        hasCustomObject = true
      }

      override def detach() = {
        left.detach()
        if (!hasCustomObject) right.detach()
      }
    }
  }
}
