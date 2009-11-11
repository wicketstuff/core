package org.wicketstuff.scala

import org.apache.wicket.markup.html.basic.Label
import org.apache.wicket.model.PropertyModel

import org.specs._
import org.specs.runner._

/**
 * @author Antony Stubbs
 */

class FodelSpecs extends Specification with JUnit with ScalaTest {

  val tony = "Tony"
  val karyn = "Karyn"

  "Read only Fodel" should {
    "have it's type parameter inferred" in {
      var bound = tony
	  var f = new Fodel(bound);
      f.getObject must be equalTo(tony)
      def takesAString(x:String) = x
      takesAString(f.getObject) must be equalTo tony
    }
    "should return the object" in {
      var bound = tony
	  var f = new FodelString(bound);
      f.getObject must be equalTo(tony)
    }
    "can be constructed without using 'new'" in {
      skip("can't yet")
      // var bound = tony
	  // var f = FodelString(bound);
      // f.getObject must be equalTo(tony)
    }
    "readonly fodel should bind correctly" in {
      var bound = tony
	  var f = new FodelString(bound);
      f.getObject must be equalTo(tony)
      // change the backing string
      bound = karyn
      f.getObject must be equalTo(karyn)
    }
    "readonly fodel shouldn't allow setting, by throwing an exception" in {
      var bound = tony
	  var f = new FodelString(bound);
      f.getObject must be equalTo(tony)
      f.setObject("cows") must throwAn[UnsupportedOperationException]
      bound aka "backing object which must not have changed" must be equalTo(tony)
    }
  }
  "Read/Write Fodel" should {
    "assign the backing property" in {
	  var bound = tony
      var f = new FodelString(bound, bound = _)
	  f.getObject must be equalTo (tony)
	  f.setObject(karyn)
	  f.getObject must be equalTo (karyn)
	}
    "work with apply() / unapply()" in {
      skip("can't yet")
	  var bound = tony
      var f = new FodelString(bound, bound = _)
	  //f() must be equalTo (tony)
	  f.getObject must be equalTo(tony)
	  //f(karyn)
	  f.getObject must be equalTo (karyn)
    }
  }
  "Setter only Fodel" should {
    "return the backing property by using the setter function" in {
    }
    "assign the backing property" in {
	  var bound = tony
      var f = new FodelString(null, bound = _)
	  f.setObject(karyn)
	  bound must be equalTo (karyn)
    }
    "assign the backing property and return it" in {
      skip("can't yet")
      var bound = tony
      var f = new FodelString(null, bound = _)
	  f.getObject must be equalTo (tony)
	  f.setObject(karyn)
	  f.getObject must be equalTo (karyn)
    }
    
  }

}