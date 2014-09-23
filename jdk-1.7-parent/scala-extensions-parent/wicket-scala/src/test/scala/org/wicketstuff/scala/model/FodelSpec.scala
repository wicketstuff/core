package org.wicketstuff.scala.model

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{MustMatchers, WordSpec}

/**
 * @author Antony Stubbs
 */
@RunWith(classOf[JUnitRunner])
class FodelSpec
  extends WordSpec
  with MustMatchers {

  val tony = "Tony"
  val karyn = "Karyn"

  "Read only Fodel" should {
    "have it's type parameter inferred" in {
      val bound = tony
      val f = new Fodel(bound)
      f.getObject mustEqual (tony)
      def takesAString(x:String) = x
      takesAString(f.getObject) mustEqual tony
    }
    "should return the object" in {
      val bound = tony
      val f = new FodelString(bound)
      f.getObject mustEqual (tony)
    }
    "fodelstring can be constructed without using 'new'" in {
      val bound = tony
      val f = FodelString(bound)
      f.getObject mustEqual (tony)
    }
    "fodel can be constructed without using 'new'" in {
      val bound = tony
      val f = Fodel(bound)
      f.getObject mustEqual tony
    }
    "readonly fodel should bind correctly" in {
      var bound = tony
      val f = new FodelString(bound)
      f.getObject mustEqual (tony)
      // change the backing string
      bound = karyn
      f.getObject mustEqual (karyn)
    }
    "readonly fodel shouldn't allow setting, by throwing an exception" in {
      val bound = tony
      val f = new FodelString(bound)
      f.getObject mustEqual (tony)
      intercept[UnsupportedOperationException] {f.setObject("cows")}
      bound mustEqual (tony)
    }
  }
  "Read/Write Fodel" should {
    "assign the backing property (FodelString)" in {
      var bound = tony
      val f = new FodelString(bound, bound = _)
      f.getObject mustEqual (tony)
      f.setObject(karyn)
      f.getObject mustEqual (karyn)
    }
    "assign the backing property (Fodel)" in {
      var bound = tony
      val f = Fodel(bound, bound = _:String)
      f.getObject mustEqual (tony)
      f.setObject(karyn)
      f.getObject mustEqual (karyn)
    }
  }
  "Setter only Fodel" should {
    "assign the backing property" in {
      var bound = tony
      val f = new FodelString(null, bound = _)
      f.setObject(karyn)
      bound mustEqual (karyn)
    }
  }

}
