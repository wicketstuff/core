package org.wicketstuff.scala

import org.apache.wicket.markup.html.WebPage
import org.apache.wicket.markup.html.basic.Label
import org.apache.wicket.markup.html.list.ListItem
import org.apache.wicket.util.tester.WicketTester

import org.specs2.mutable.SpecificationWithJUnit

/**
 * Specifications for the various Scala extended components.
 * 
 * @author Antony Stubbs
 */
class ComponentSpecs
  extends SpecificationWithJUnit
  with ScalaWicket {

  sequential

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
    clickCount mustEqual  1
    fx
    clickCount mustEqual 2
    clickCount += 1
    clickCount mustEqual 3
    fx
    clickCount mustEqual 4
  }

  "SLink component" should {
    "work with a closure for onclick" in {
      val tester = new WicketTester()
      //detailedDiffs()
      val slk = new SLink("clicker", clickCount += 1)
      clickCountUpdatingFunction(slk.onClick)
      tester.destroy()
      ok("passed")
    }
  }
  "SLabel component" should {
    "bind dynamically to a backing object using a closure" in {
      val tester = new WicketTester()
      var n = tony
      // a normal label doesn't update without a model
      val l = new Label("name", n)
      l.getDefaultModelObject mustEqual(tony)
      n = n + karyn 
      l.getDefaultModelObject aka "static label" must be equalTo(tony)

      // a scala label should bind dynamically
      n = tony
      val sl = new SLabel("name", n)
      sl.getDefaultModelObject mustEqual tony
      n = n + karyn
      sl.getDefaultModelObject aka "scala label" must be equalTo(tony+karyn)
      tester.destroy()
      ok("passed")
    }
  }
  "Label component" should {
    "work with explicit fodel" in {
      val tester = new WicketTester()
      var n = tony
      val l = new Label("name", new Fodel(n))
      l.getDefaultModelObject mustEqual tony
      n = n + karyn
      l.getDefaultModelObject aka "label with explicit fodel" must be equalTo(tony+karyn)
      tester.destroy()
      ok("passed")
    }
  }
  "STextField component" should {
    "work with writable fodel" in {
      val tester = new WicketTester()
      var n = tony
      val stf = new STextField("name", new Fodel[String](n, n = _))
      stf.getDefaultModelObject mustEqual tony
      n = n + karyn
      stf.getDefaultModelObject aka "scala label" must be equalTo(tony+karyn)
      stf.setModelObject(karyn)
      n aka "baking model object after textfield update" mustEqual karyn
      tester.destroy()
      ok("passed")
    }
    "can be used with an Int" in {
      val tester = new WicketTester()
      val x = 1
      val field = new STextField("name", x)
      tester.destroy()
      field must not beNull
    }
  }
  "SForm component" should {
    "take a closure for it's onSubmit method" in {
      val tester = new WicketTester()
      val sf = new SForm("form", null, clickCount += 1) // null model
      def fx = sf.onSubmit
      clickCountUpdatingFunction(fx)
      tester.destroy()
      ok("passed")
    }
    "pass model to a super-class" in {
      val tester = new WicketTester()
      val sf = new SForm("form", new Fodel[String]("test"), {})
      sf.getModelObject mustEqual "test"
      tester.destroy()
      ok("passed")
    }
  }
  "SListView component" should {
    "take a closure for the #populateItem method" in {
      val tester = new WicketTester()
      def fx(item:ListItem[String]) {
        item must beNull
        clickCount += 1
      }
      val slv = new SListView[String]("votes", null, fx)
      def curry = slv.populateItem(null)
      clickCountUpdatingFunction(curry)
      tester.destroy()
      ok("passed")
    }
  }
  "SPropertyListView component" should {
    "take a function literal for the #populateItem method" in {
      val tester = new WicketTester()
      val splv = new SPropertyListView[String]("votes", List[String](), (item:ListItem[String]) => {
        item must beNull
        clickCount += 1
      })
      def curry = splv.populateItem(null)
      clickCountUpdatingFunction(curry)
      tester.destroy()
      ok("passed")
    }
    "take a partially applied function for the #populateItem method" in {
      val tester = new WicketTester()
      def fx(item:ListItem[String]) {
        item must beNull
        clickCount += 1
      }
      val splv = new SPropertyListView[String]("votes", List[String](), fx _)
      def curry = splv.populateItem(null)
      clickCountUpdatingFunction(curry)
      tester.destroy()
      ok("passed")
    }
    "be able to be constructed with a static Scala type List" in {
      val tester = new WicketTester()
      val view = new SPropertyListView[String]("votes", List[String](), null)
      tester.destroy()
      view must not beNull
    }
    "be able to be constructed with a Fodel for the backing list" in {
      val tester = new WicketTester()
      val theBackingList = List(1,2,3)
      val fodel = new Fodel[java.util.List[Int]](theBackingList)
      val view = new SPropertyListView[Int]("votes", fodel, null)
      tester.destroy()
      view must not beNull
    }
    "use the fodel converter" in {
      val tester = new WicketTester()
      val theBackingList = List(1,2,3)
      val fodel = new Fodel[List[Int]](theBackingList)
      val correctFodel = fodelListToFodelJavaList(fodel)
      val view = new SPropertyListView[Int]("votes", correctFodel, null)
      tester.destroy()
      view must not beNull
    }
    "use the fodel converter implicitly" in {
      val tester = new WicketTester()
      val theBackingList = List(1,2,3)
      val fodel = new Fodel[List[Int]](theBackingList)
      val view = new SPropertyListView[Int]("votes", fodel, null)
      tester.destroy()
      view must not beNull
    }
    "be able to be constructed with a Fodel for the backing list with the Fodel types with a Scala List" in {
      val tester = new WicketTester()
      val theBackingList = List(1,2,3)
      val fodel = new Fodel[List[Int]](theBackingList)
      val fodel2 = new Fodel(listToJavaList(theBackingList))

      new SPropertyListView[Int]("votes", fodel2, null)
      val view = new SPropertyListView[Int]("votes", fodel, null)
      tester.destroy()
      view must not beNull
    }
    "be able to be constructed with a Fodel for the backing list with the Fodel type parameter inferred" in {
      val tester = new WicketTester()
      val theBackingList = List(1,2,3)
      val fodel = new Fodel(theBackingList)
      val view = new SPropertyListView[Int]("votes", fodel, null)
      tester.destroy()
      view must not beNull
    }
    "be able to be constructed with a pass by name parameter for the backing list, typed as a Scala list" in {
      val tester = new WicketTester()
      val theBackingList = List(1,2,3)
      val view = new SPropertyListView[Int]("votes", theBackingList, null)
      tester.destroy()
      view must not beNull
    }
    "be able to be constructed with a Function literal for the backing list, typed as a Java list" in {
      val tester = new WicketTester()
      val theBackingList = new java.util.ArrayList[Int]()
      theBackingList.add(1)
      theBackingList.add(2)
      theBackingList.add(3)
      val view = new SPropertyListView[Int]("votes", () => theBackingList, null)
      tester.destroy()
      view must not beNull
    }
    "passing the list in as a by name should work properly" in {
      val tester = new WicketTester()
      val theBackingList = new java.util.ArrayList[Int]()
      theBackingList.add(1)
      theBackingList.add(2)
      theBackingList.add(3)
      val lv = new SPropertyListView[Int]("votes", theBackingList, null)
      theBackingList.add(4)
      val size = lv.getModelObject.size
      tester.destroy()
      size mustEqual 4
    }
    "passing the list in as a Scala List also works" in {
      val tester = new WicketTester()
      var theBackingList = List(1,2,3)
      val lv = new SPropertyListView[Int]("votes", theBackingList, null)
      theBackingList = theBackingList ::: List(4)
      val size = lv.getModelObject.size
      tester.destroy()
      size mustEqual 4
    }
    "can still construct with a fodel using pass by name" in {
      val tester = new WicketTester()
      val theBackingList = List(1,2,3)
      val fodel = new Fodel[java.util.List[Int]](theBackingList)
      val view = new SPropertyListView[Int]("votes", fodel.getObject, null)
      tester.destroy()
      view must not beNull
    }
  }

}
