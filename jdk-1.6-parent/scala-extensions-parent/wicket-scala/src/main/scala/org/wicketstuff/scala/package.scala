package org.wicketstuff

import org.apache.wicket.model.IModel

import language.implicitConversions

/**
 *
 */
package object scala {

  implicit def modelToReactiveModel[T <: java.io.Serializable](model: IModel[T]): ReactiveModel[T] = {
    new ReactiveModel[T](model.getObject) {
      override def getObject = model.getObject

      override def setObject(obj: T) = model.setObject(obj)

      override def detach() = model.detach()
    }
  }

  implicit def serializableToReactiveModel[T <: java.io.Serializable](obj: T): ReactiveModel[T] = {
    new ReactiveModel[T](obj)
  }
}
