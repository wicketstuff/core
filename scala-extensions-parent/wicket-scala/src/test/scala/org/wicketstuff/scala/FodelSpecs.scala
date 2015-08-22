package org.wicketstuff.scala


import org.specs2.mutable.SpecificationWithJUnit

/**
 * @author Antony Stubbs
 */

class FodelSpecs
  extends SpecificationWithJUnit {

  val tony = "Tony"
  val karyn = "Karyn"

  "Read only Fodel" should {
    "have it's type parameter inferred" in {
      val bound = tony
      val f = new Fodel(bound)
      f.getObject must be equalTo(tony)
      def takesAString(x:String) = x
      takesAString(f.getObject) must be equalTo tony
    }
    "should return the object" in {
      val bound = tony
      val f = new FodelString(bound)
      f.getObject must be equalTo(tony)
    }
    "fodelstring can be constructed without using 'new'" in {
      val bound = tony
      val f = FodelString(bound)
      f.getObject must be equalTo(tony)
    }
    "fodel can be constructed without using 'new'" in {
      val bound = tony
      val f = Fodel(bound)
      f.getObject must be equalTo(tony)
    }
    "readonly fodel should bind correctly" in {
      var bound = tony
      val f = new FodelString(bound)
      f.getObject must be equalTo(tony)
      // change the backing string
      bound = karyn
      f.getObject must be equalTo(karyn)
    }
    "readonly fodel shouldn't allow setting, by throwing an exception" in {
      val bound = tony
      val f = new FodelString(bound)
      f.getObject must be equalTo(tony)
      f.setObject("cows") must throwAn[UnsupportedOperationException]
      bound aka "backing object which must not have changed" must be equalTo(tony)
    }
  }
  "Read/Write Fodel" should {
    "assign the backing property (FodelString)" in {
      var bound = tony
      val f = new FodelString(bound, bound = _)
      f.getObject must be equalTo (tony)
      f.setObject(karyn)
      f.getObject must be equalTo (karyn)
    }
    "assign the backing property (Fodel)" in {
      var bound = tony
      val f = Fodel(bound, bound = _:String)
      f.getObject must be equalTo (tony)
      f.setObject(karyn)
      f.getObject must be equalTo (karyn)
    }
  }
  "Setter only Fodel" should {
    "assign the backing property" in {
      var bound = tony
      val f = new FodelString(null, bound = _)
      f.setObject(karyn)
      bound must be equalTo (karyn)
    }
  }

}
