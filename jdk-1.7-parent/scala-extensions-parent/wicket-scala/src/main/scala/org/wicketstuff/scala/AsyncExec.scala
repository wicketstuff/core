package org.wicketstuff.scala

import java.util.concurrent.Executors

import org.apache.wicket.request.cycle.RequestCycle
import org.apache.wicket.{Application, Session, ThreadContext}

import scala.concurrent.{ExecutionContext, Future}

object AsyncExec {
  val Executor =
  // this schedules tasks on the same threads
  //  e.g. 2 out of 3 tasks will be executed in the same thread, even when there are 8 threads in the pool
  //ExecutionContext.global | ExecutionContext.fromExecutor(new ForkJoinPool())

  // this schedules round-robin
    ExecutionContext.fromExecutorService(Executors.newFixedThreadPool(Runtime.getRuntime.availableProcessors()))
}

/**
 * A trait that allows to execute a task asynchronously while leveraging the
 * current Application, Session and RequestCycle thread locals
 */
trait AsyncExec[T] {

  @transient
  implicit protected val ec: ExecutionContext = AsyncExec.Executor

  private[this] val appName: String = Application.get().getName

  @transient
  private[this] val session =
    if (Session.exists()) Session.get()
    else null

  @transient
  private[this] val cycle = RequestCycle.get()

  @transient
  protected def future: Future[T] = Future {
    try {
      ThreadContext.setApplication(Application.get(appName))
      ThreadContext.setSession(session)
      ThreadContext.setRequestCycle(cycle)

      task
    }
    finally {
      ThreadContext.detach()
    }
  }

  /**
   * @return The task to execute asynchronously
   */
  protected def task: T
}
