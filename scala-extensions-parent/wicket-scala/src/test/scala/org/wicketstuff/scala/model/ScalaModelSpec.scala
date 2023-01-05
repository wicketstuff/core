package org.wicketstuff.scala.model

import org.apache.wicket.model.{CompoundPropertyModel, IModel, PropertyModel}
import org.wicketstuff.scala.WicketSpec

import scala.concurrent.TimeoutException
import scala.concurrent.duration._
import scala.util.{Failure, Try}

/**
 * Tests for ScalaModel
 */
class ScalaModelSpec
  extends WicketSpec
  with ScalaModel {

  test("Loadable detachable model") {
    val model = ldM({1 + 2})
    model.getObject mustBe 3
  }

  test("Future model with simple result") {
    val model = futureM[Integer]({1 + 2})
    model.getObject mustBe 3
  }

  test("Future model with timeout") {
    val model: FutureModel[String] = futureM({
	  Thread.sleep(1500)
      "result"
    }, 500.millis)
    val result = Try(model.getObject)
    result match {
      case Failure(x) if x.isInstanceOf[TimeoutException] => true mustBe true
      case wrong: Any => fail(s"Wrong result: $wrong")
    }
  }

  test("Model - null") {
    val value: String = null
    val model = basicM(value)
    model mustNot be (null)
    model.getObject mustBe value
  }

  test("Model - String") {
    val value: String = "value"
    val model = basicM(value)
    model mustNot be (null)
    model.getObject mustBe value
  }

  test("PropertyModel") {
    val john = Person("John", 42)
    val model: PropertyModel[Array[Char]] = propertyM[Array[Char]](john, "name")
    model mustNot be (null)
    model.getObject must equal(john.name)
  }

  test("CompoundPropertyModel - object") {
    val john = Person("John", 42)
    val model: CompoundPropertyModel[Person] = compoundM(john)
    model.getObject must equal(john)
    val nameModel: IModel[String] = model.bind[String]("name")
    nameModel.getObject must equal(john.name)
  }

  test("CompoundPropertyModel - model") {
    val john = Person("John", 42)
    val model: CompoundPropertyModel[Person] = compoundM(basicM(john))
    model.getObject must equal(john)
    val nameModel: IModel[String] = model.bind[String]("name")
    nameModel.getObject must equal(john.name)
  }

  case class Person(name: String, age: Int)
}
