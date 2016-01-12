package org.wicketstuff.scala.model

import org.apache.wicket.model.{Model, IModel, AbstractReadOnlyModel, CompoundPropertyModel, LoadableDetachableModel, PropertyModel}

import scala.concurrent.duration._

/**
 *
 */
trait ScalaModel {

  type Serializable = java.io.Serializable

  def ldM[T](loadF: ⇒ T): LoadableDetachableModel[T] = {
    val ldm = new LoadableDetachableModel[T] {
      override def load(): T = loadF
    }
    ldm
  }

  def futureM[T <: Serializable](body: => T, duration: Duration = 1.second): FutureModel[T] = {
    val fModel = new FutureModel[T](body, duration)
    fModel
  }

  def aroM[T <: Serializable](f: => T): AbstractReadOnlyModel[T] = {
    val arom = new AbstractReadOnlyModel[T] {
      override def getObject = f
    }
    arom
  }

  def propertyM[T <: Serializable](obj: Serializable, expression: String): PropertyModel[T] = {
    new PropertyModel[T](obj, expression)
  }

  def compoundM[T <: Serializable](obj: T): CompoundPropertyModel[T] = {
    new CompoundPropertyModel[T](obj)
  }

  def compoundM[T <: Serializable](model: IModel[T]): CompoundPropertyModel[T] = {
    new CompoundPropertyModel[T](model)
  }

  def basicM[T <: Serializable](obj: T): Model[T] = {
    Model.of(obj)
  }
}
