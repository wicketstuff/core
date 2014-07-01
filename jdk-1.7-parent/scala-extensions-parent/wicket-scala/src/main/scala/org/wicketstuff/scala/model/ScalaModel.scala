package org.wicketstuff.scala.model

import org.apache.wicket.model.{AbstractReadOnlyModel, LoadableDetachableModel}

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

  def futureM[T](body: => T, duration: Duration = 10.seconds): FutureModel[T] = {
    val fModel = new FutureModel[T](body, duration)
    fModel
  }

  def aroM[T](f: => T): AbstractReadOnlyModel[T] = {
    val arom = new AbstractReadOnlyModel[T] {
      override def getObject = f
    }
    arom
  }
}
