package org.wicketstuff.scala

import org.apache.wicket.model.{IModel, Model}
import org.junit.Assert._
import org.junit.Test

/**
 *
 */
class ReactiveModelTest {

  /**
   * Test concatenation of IModel[T] with implicit Plus[T] in scope
   */
  @Test
  def concatenateStrings() {
    val a = Model.of("a")
    val b = Model.of("b")

    val model: IModel[String] = a + b
    assertEquals("ab", model.getObject)

    b.setObject("bb")
    assertEquals("abb", model.getObject)
  }
}
