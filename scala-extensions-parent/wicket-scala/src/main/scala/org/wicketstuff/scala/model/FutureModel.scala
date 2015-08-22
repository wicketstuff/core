package org.wicketstuff.scala.model

import org.apache.wicket.model.LoadableDetachableModel
import org.wicketstuff.scala.AsyncExec

import scala.concurrent._
import scala.concurrent.duration._


/**
 * A model that starts calculating its object as soon as possible, i.e. when there is
 * an available thread in the pool.
 * It it useful when the model object's value requires longer computation or blocking,
 * or when there are several models which objects have to be computed in parallel.
 * In the best case it should have calculated the result by the time #getObject() is called.
 *
 * Usage:
 * new MyComponent("someId", new FutureModel[MyType]({
 *   val result: MyType = someSlowComputation()
 *   result
 * }))
 *
 * @param body the function that calculates the result
 * @param duration the maximum time to wait for the result
 * @param ec the ExecutionContext to use
 * @tparam T the type of the result
 */
class FutureModel[T](body: => T,
                     duration: Duration = 10.seconds)
                    (@transient implicit override protected val ec: ExecutionContext = AsyncExec.Executor)
  extends LoadableDetachableModel[T]
  with AsyncExec[T] {

  override protected def task: T = body

  override def load: T = {
    Await.result(future, duration)
  }
}
