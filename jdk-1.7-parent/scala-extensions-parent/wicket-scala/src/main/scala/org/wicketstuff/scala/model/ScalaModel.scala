package org.wicketstuff.scala.model

import java.io.{Serializable => JSerializable}
import org.apache.wicket.model.{Model, IModel, AbstractReadOnlyModel, CompoundPropertyModel, LoadableDetachableModel, PropertyModel}

import scala.concurrent.duration._

/**
 *
 */
trait ScalaModel {

  def ldM[T](loadF: ⇒ T): LoadableDetachableModel[T] = {
    val ldm = new LoadableDetachableModel[T] {
      override def load(): T = loadF
    }
    ldm
  }

  def futureM[T](body: => T, duration: Duration = 1.second): FutureModel[T] = {
    val fModel = new FutureModel[T](body, duration)
    fModel
  }

  def aroM[T](f: => T): AbstractReadOnlyModel[T] = {
    val arom = new AbstractReadOnlyModel[T] {
      override def getObject = f
    }
    arom
  }

  def propertyM[T <: JSerializable](obj: JSerializable, expression: String): PropertyModel[T] = {
    new PropertyModel[T](obj, expression)
  }

  def compoundM[T <: JSerializable](obj: T): CompoundPropertyModel[T] = {
    new CompoundPropertyModel[T](obj)
  }

  def compoundM[T <: JSerializable](model: IModel[T]): CompoundPropertyModel[T] = {
    new CompoundPropertyModel[T](model)
  }

  def basicM[T <: JSerializable](obj: T): Model[T] = {
    Model.of(obj)
  }
}
