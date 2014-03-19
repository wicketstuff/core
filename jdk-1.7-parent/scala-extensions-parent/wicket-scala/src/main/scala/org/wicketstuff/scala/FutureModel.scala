package org.wicketstuff.scala

import java.util.concurrent.Executors
import org.apache.wicket.model.AbstractReadOnlyModel
import org.apache.wicket.request.cycle.RequestCycle
import org.apache.wicket.{ThreadContext, Session, Application}
import scala.concurrent._
import scala.concurrent.duration._
import scala.transient

object FutureModel {
  private val Executor =
   // this schedules tasks on the same threads
   //  e.g. 2 out of 3 tasks will be executed in the same thread, even when there are 8 threads in the pool
   //ExecutionContext.global | ExecutionContext.fromExecutor(new ForkJoinPool())

   // this schedules round-robin
   ExecutionContext.fromExecutorService(Executors.newFixedThreadPool(Runtime.getRuntime.availableProcessors()))
}

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
                    (@transient implicit val ec: ExecutionContext = FutureModel.Executor)
  extends AbstractReadOnlyModel[T] {

  @transient
  private[this] val appName: String = Application.get().getName

  @transient
  private[this] val session =
    if (Session.exists()) Session.get()
    else null

  @transient
  private[this] val cycle = RequestCycle.get()

  @transient
  private[this] val f: Future[T] = future {
    try {
      ThreadContext.setApplication(Application.get(appName))
      ThreadContext.setSession(session)
      ThreadContext.setRequestCycle(cycle)

      body
    }
    finally {
      ThreadContext.detach()
    }
  }

  @volatile
  private[this] var obj: T = _

  def getObject = {
    if (obj != null) obj
    else {
      obj = Await.result(f, duration)
      obj
    }
  }
}
