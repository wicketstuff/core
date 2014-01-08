package org.wicketstuff.scala

import org.junit.{Test, Assert}
import org.apache.wicket.model.Model

/**
 *
 */
class ReactiveModelTest extends Assert {

  @Test
  def aa() {
    val a = new Model("a")
    val b = new Model("b")

    val model: ReactiveModel[String] = a + b
    println("res: " + model.getObject)
  }
}
