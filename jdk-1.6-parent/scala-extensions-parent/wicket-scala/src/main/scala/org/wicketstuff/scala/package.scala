package org.wicketstuff

import org.apache.wicket.model.IModel

import language.implicitConversions

/**
 *
 */
package object scala {

  implicit def modelToReactiveModel[T <: Serializable](model: IModel[T]): ReactiveModel[T] = {
    new ReactiveModel[T] {
      override def getObject = model.getObject

      override def setObject(obj: T) = model.setObject(obj)

      override def detach() = model.detach()
    }
  }
}
