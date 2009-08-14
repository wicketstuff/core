package org.wicketstuff.scala

import org.apache.wicket.markup.html.WebPage
import org.apache.wicket.markup.html.basic.Label
import org.apache.wicket.model.PropertyModel
import org.apache.wicket.util.tester.WicketTester
import org.apache.wicket.markup.html.list._

import org.specs._
import org.specs.runner._

/**
 * Specifications for the various Scala extended components.
 * 
 * @author Antony Stubbs
 */
class ComponentSpecs extends Specification with JUnit with ScalaTest with ScalaWicket {

  val tony = "Tony"
  val karyn = "Karyn"
  
  class ComponentContainerPage extends WebPage

  var clickCount = 0
  /**
   * Checks that the passed in function updates the clickCount by 
   * 1, every time it is called.
   */
  def clickCountUpdatingFunction(fx: => Unit){
    clickCount = 0
    clickCount += 1     
    clickCount mustEq 1
    fx
    clickCount mustEq 2
    clickCount += 1
    clickCount mustEq 3
    fx
    clickCount mustEq 4
  }
  
  "SLink component" should { doBefore{ new WicketTester() }
    "work with a closure for onclick" in {
      //detailedDiffs()
	  var slk = new SLink("clicker", clickCount += 1)
      clickCountUpdatingFunction(slk.onClick)
    }
  }
  "SLabel component" should { doBefore{ new WicketTester() }
    "bind dynamically to a backing object using a closure" in {
      var n = tony
      // a normal label doesn't update without a model
	  val l = new Label("name", n)
	  l.getDefaultModelObject mustEqual(tony)
      n = n + karyn 
      l.getDefaultModelObject aka "static label" must be equalTo(tony)
	  
      // a scala label should bind dynamically
      n = tony
   	  val sl = new SLabel("name", n)
      sl.getDefaultModelObject mustEq tony
      n = n + karyn
      sl.getDefaultModelObject aka "scala label" must be equalTo(tony+karyn)
    }
  }
  "Label component" should { doBefore{ new WicketTester() }
    "work with explicit fodel" in {
      var n = tony
      val l = new Label("name", new Fodel(n))
      l.getDefaultModelObject mustEq tony
      n = n + karyn
      l.getDefaultModelObject aka "label with explicit fodel" must be equalTo(tony+karyn)
  	}
  }
  "STextField component" should { doBefore{ new WicketTester() }
    "work with writable fodel" in {
      var n = tony
      val stf = new STextField("name", new Fodel[String](n, n = _))
   	  stf.getDefaultModelObject mustEq tony
      n = n + karyn
      stf.getDefaultModelObject aka "scala label" must be equalTo(tony+karyn)
      stf.setModelObject(karyn)
      n aka "baking model object after textfield update" mustEq karyn
    }
    "can be used with an Int" in {
      val x = 1
      new STextField("name", x) must notBeNull
    }
  }
  "SForm component" should { doBefore{ new WicketTester() }
    "take a closure for it's onSubmit method" in {
      val sf = new SForm("form", null, clickCount += 1) // null model
      def fx = sf.onSubmit
      clickCountUpdatingFunction(fx)
    }
  }
  "SListView component" should { doBefore{ new WicketTester() }
    "take a closure for the #populateItem method" in {
      def fx(item:ListItem[String]):Unit = {
        item mustBe null
        clickCount += 1
      }
      val slv = new SListView[String]("votes", null, fx)
      def curry = slv.populateItem(null)
      clickCountUpdatingFunction(curry)
    }
  }
  "SPropertyListView component" should { doBefore{ new WicketTester() }
    "take a partially applied function for the #populateItem method" in {
      def fx(item:ListItem[String]):Unit = {
        item mustBe null
        clickCount += 1
      }
      val splv = new SPropertyListView[String]("votes", List[String](), fx _)
      def curry = splv.populateItem(null)
      clickCountUpdatingFunction(curry)
    }
    "take a function literal for the #populateItem method" in {
      val splv = new SPropertyListView[String]("votes", List[String](), (item:ListItem[String]) => {
        item mustBe null
        clickCount += 1
      })
      def curry = splv.populateItem(null)
      clickCountUpdatingFunction(curry)
    }
    "be able to be constructed with a static Scala type List" in {
      new SPropertyListView[String]("votes", List[String](), null) must notBeNull
    }
    "be able to be constructed with a Fodel for the backing list" in {
      val theBackingList = List(1,2,3)
      val fodel = new Fodel[java.util.List[Int]](theBackingList)
      new SPropertyListView[Int]("votes", fodel, null) must notBeNull
    }
    "use the fodel converter" in {
      new WicketTester() // -- for some reason the doBefore doesn't work hhere
      val theBackingList = List(1,2,3)
      val fodel = new Fodel[List[Int]](theBackingList)
      val correctFodel = fodelListToFodelJavaList(fodel)
      new SPropertyListView[Int]("votes", correctFodel, null) must notBeNull
    }
    "use the fodel converter implicitly" in {
      val theBackingList = List(1,2,3)
      val fodel = new Fodel[List[Int]](theBackingList)
      new SPropertyListView[Int]("votes", fodel, null) must notBeNull
    }
    "be able to be constructed with a Fodel for the backing list with the Fodel types with a Scala List" in {
      val theBackingList = List(1,2,3)
      val fodel = new Fodel[List[Int]](theBackingList)
      val fodel2 = new Fodel(listToJavaList(theBackingList))
      
      new SPropertyListView[Int]("votes", fodel2, null)
      new SPropertyListView[Int]("votes", fodel, null) must notBeNull
    }
    "be able to be constructed with a Fodel for the backing list with the Fodel type parameter inferred" in {
      val theBackingList = List(1,2,3)
      val fodel = new Fodel(theBackingList)
      new SPropertyListView[Int]("votes", fodel, null) must notBeNull
    }
    "be able to be constructed with a pass by name parameter for the backing list, typed as a Scala list" in {
      val theBackingList = List(1,2,3)
      new SPropertyListView[Int]("votes", theBackingList, null) must notBeNull
    }
    "be able to be constructed with a Function literal for the backing list, implicitly, typed as a Scala list" in {
      val theBackingList = List(1,2,3)
      new SPropertyListView[Int]("votes", () => theBackingList, null) // -- un comment this line for the test
    }
    "be able to be constructed with a Function literal for the backing list, typed as a Java list" in {
      val theBackingList = new java.util.ArrayList[Int]()
      theBackingList.add(1)
      theBackingList.add(2)
      theBackingList.add(3)
      new SPropertyListView[Int]("votes", () => theBackingList, null) must notBeNull
    }
    "passing the list in as a by name should work properly" in {
      val theBackingList = new java.util.ArrayList[Int]()
      theBackingList.add(1)
      theBackingList.add(2)
      theBackingList.add(3)
      val lv = new SPropertyListView[Int]("votes", theBackingList, null)
      theBackingList.add(4)
      lv.getModelObject.size mustBe 4
    }
    "passing the list in as a Scala List also works" in {
      var theBackingList = List(1,2,3)
      val lv = new SPropertyListView[Int]("votes", theBackingList, null)
      theBackingList = theBackingList ::: List(4)
      lv.getModelObject.size mustBe 4
    }
    "be able to be constructed with the Type parameter inferred" in {
      skip("Does not compile. The compiler appears to not be able to infer the generic parameter of SPropertyListView")
      // new SPropertyListView("votes", List[Int](), null) must notBeNull // -- uncomment for test
    }
    "be constructed using an infered function type (that is, the return value is infered to be ())" in {
	  val theBackingList = new java.util.ArrayList[Int]()
	  new SPropertyListView[Int]("votes", theBackingList, (li:ListItem[Int]) => { 5 }) // should not have to put '()' at the end of the function block
	  new SPropertyListView[Int]("votes", theBackingList, _.add(new Label("","")))
    }
    "can still construct with a fodel using pass by name" in {
      val theBackingList = List(1,2,3)
      val fodel = new Fodel[java.util.List[Int]](theBackingList)
      new SPropertyListView[Int]("votes", fodel.getObject, null) must notBeNull
    }
    "different ways to construct using function literals with infered return type" in {
	  val list = new java.util.ArrayList[String]()
	  list.add("1")
	
	  new SPropertyListView[String](new java.lang.String("presentations"), list, (li:ListItem[String]) => {li.add(new SLabel("name", "asdp name"))})
	  new SPropertyListView[String](new java.lang.String("presentations"), list, li => li.add(new SLabel("name", "asdp name")))
	  new SPropertyListView[String](new java.lang.String("presentations"), list, _.add(new SLabel("name", "asdp name")))

      def bar(li:ListItem[String]):Unit = { li add(new SLabel("name", "asdp name")) }
	  new SPropertyListView[String](new java.lang.String("presentations"), list, bar _)
   
	  // shortcut
      var plv = new SPropertyListView[String](new java.lang.String("presentations"), list, _:(ListItem[String] ⇒ Unit))
      
      plv((li:ListItem[String]) => {li.add(new SLabel("name", "asdp name"))})
	  plv(li => li.add(new SLabel("name", "asdp name")))
	  plv(_.add(new SLabel("name", "asdp name")))

    }
  }

}