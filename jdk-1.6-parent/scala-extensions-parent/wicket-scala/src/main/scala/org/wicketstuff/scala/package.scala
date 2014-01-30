package org.wicketstuff

import org.apache.wicket.model.{AbstractReadOnlyModel, IModel}

import language.implicitConversions
import org.apache.wicket.Component
import org.apache.wicket.ajax.{AjaxEventBehavior, AjaxRequestTarget}

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

  implicit class ComponentX(component: Component) {

    /**
     * Sets read-only model to a component by using function
     * that calculates the model object
     *
     * @param m
     * @tparam T
     * @return
     */
    def roModel[T](m: () => T): IModel[T] = {
      val roModel = new AbstractReadOnlyModel[T] {
        override def getObject = m()
      }
      component.setDefaultModel(roModel)
      roModel
    }

    /**
     * Sugar for adding AjaxEventBehavior
     *
     * @param eventName
     * @param m
     * @return
     */
    def on(eventName: String, m: (AjaxRequestTarget) => Unit): AjaxEventBehavior = {
      val behavior = new AjaxEventBehavior(eventName) {
        override def onEvent(target: AjaxRequestTarget) = m(target)
      }
      component.add(behavior)
      behavior
    }
  }
}
