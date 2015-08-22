package org.wicketstuff.scala

import _root_.java.util.{ArrayList => JArrayList, List => JList}

import org.apache.wicket.markup.html.WebPage
import org.apache.wicket.markup.html.basic.Label
import org.apache.wicket.markup.html.list.ListItem
import org.apache.wicket.util.tester.WicketTester
import org.junit.runner.RunWith
import org.scalatest.{MustMatchers, WordSpec}
import org.scalatest.junit.JUnitRunner
import org.wicketstuff.scala.markup.html.basic.ScalaLabel
import org.wicketstuff.scala.markup.html.form.{ScalaForm, ScalaTextField}
import org.wicketstuff.scala.markup.html.link.ScalaLink
import org.wicketstuff.scala.markup.html.list.{ScalaListView, ScalaPropertyListView}
import org.wicketstuff.scala.model.Fodel

/**
 * Specifications for the various Scala extended components.
 * 
 * @author Antony Stubbs
 */
@RunWith(classOf[JUnitRunner])
class ComponentSpec
  extends WordSpec
  with MustMatchers {

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
    clickCount mustBe 1
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
      val slk = new ScalaLink("clicker", f = clickCount += 1)
      clickCountUpdatingFunction(slk.onClick())
      tester.destroy()
    }
  }
  "SLabel component" should {
    "bind dynamically to a backing object using a closure" in {
      val tester = new WicketTester()
      var n = tony
      // a normal label doesn't update without a model
      val l = new Label("name", n)
      l.getDefaultModelObject mustEqual tony
      n = n + karyn
      l.getDefaultModelObject mustEqual tony

      // a scala label should bind dynamically
      n = tony
      val sl = new ScalaLabel("name", n)
      sl.getDefaultModelObject mustEqual tony
      n = n + karyn
      sl.getDefaultModelObject mustEqual (tony + karyn)
      tester.destroy()
    }
  }
  "Label component" should {
    "work with explicit fodel" in {
      val tester = new WicketTester()
      var n = tony
      val l = new Label("name", new Fodel(n))
      l.getDefaultModelObject mustEqual tony
      n = n + karyn
      l.getDefaultModelObject mustEqual (tony + karyn)
      tester.destroy()
    }
  }
  "STextField component" should {
    "work with writable fodel" in {
      val tester = new WicketTester()
      var n = tony
      val stf = new ScalaTextField[String]("name", new Fodel[String](n, n = _))
      stf.getDefaultModelObject mustEqual tony
      n = n + karyn
      stf.getDefaultModelObject mustEqual (tony+karyn)
      stf.setModelObject(karyn)
      n mustEqual karyn
      tester.destroy()
    }
    "can be used with an Int" in {
      val tester = new WicketTester()
      val x = 1
      val field = new ScalaTextField("name", x)
      tester.destroy()
      field mustNot be (null)
    }
  }
  "SForm component" should {
    "take a closure for it's onSubmit method" in {
      val tester = new WicketTester()
      val sf = new ScalaForm[Unit]("form", actions = Map("submit" -> { that => clickCount += 1})) // null model
      def fx() = sf.onSubmit()
      clickCountUpdatingFunction(fx())
      tester.destroy()
    }
    "pass model to a super-class" in {
      val tester = new WicketTester()
      val sf = new ScalaForm[String]("form", new Fodel[String]("test"))
      sf.getModelObject mustEqual "test"
      tester.destroy()
    }
  }
  "SListView component" should {
    "take a closure for the #populateItem method" in {
      val tester = new WicketTester()
      def fx(item:ListItem[String]) {
        item mustBe null
        clickCount += 1
      }
      val slv = new ScalaListView[String]("votes", fx)
      def curry() = slv.populateItem(null)
      clickCountUpdatingFunction(curry())
      tester.destroy()
    }
  }
  "SPropertyListView component" should {
    "take a function literal for the #populateItem method" in {
      val tester = new WicketTester()
      val splv = new ScalaPropertyListView[String]("votes", List[String](), (item:ListItem[String]) => {
        item mustBe null
        clickCount += 1
      })
      def curry() = splv.populateItem(null)
      clickCountUpdatingFunction(curry())
      tester.destroy()
    }
    "take a partially applied function for the #populateItem method" in {
      val tester = new WicketTester()
      def fx(item:ListItem[String]) {
        item mustBe null
        clickCount += 1
      }
      val splv = new ScalaPropertyListView[String]("votes", List[String](), fx)
      def curry() = splv.populateItem(null)
      clickCountUpdatingFunction(curry())
      tester.destroy()
    }
    "be able to be constructed with a static Scala type List" in {
      val tester = new WicketTester()
      val view = new ScalaPropertyListView[String]("votes", List[String](), null)
      tester.destroy()
      view mustNot be (null)
    }
    "be able to be constructed with a Fodel for the backing list" in {
      val tester = new WicketTester()
      val theBackingList = List(1,2,3)
      val fodel = new Fodel[JList[Int]](theBackingList)
      val view = new ScalaPropertyListView[Int]("votes", fodel, null)
      tester.destroy()
      view mustNot be (null)
    }
    "use the fodel converter" in {
      val tester = new WicketTester()
      val theBackingList = List(1,2,3)
      val fodel = new Fodel[List[Int]](theBackingList)
      val correctFodel = fodelListToFodelJavaList(fodel)
      val view = new ScalaPropertyListView[Int]("votes", correctFodel, null)
      tester.destroy()
      view mustNot be (null)
    }
    "use the fodel converter implicitly" in {
      val tester = new WicketTester()
      val theBackingList = List(1,2,3)
      val fodel = new Fodel[List[Int]](theBackingList)
      val view = new ScalaPropertyListView[Int]("votes", fodel, null)
      tester.destroy()
      view mustNot be (null)
    }
    "be able to be constructed with a Fodel for the backing list with the Fodel types with a Scala List" in {
      val tester = new WicketTester()
      val theBackingList = List(1,2,3)
      val fodel = new Fodel[List[Int]](theBackingList)
      val fodel2 = new Fodel(seqToJavaList(theBackingList))

      new ScalaPropertyListView[Int]("votes", fodel2, null)
      val view = new ScalaPropertyListView[Int]("votes", fodel, null)
      tester.destroy()
      view mustNot be (null)
    }
    "be able to be constructed with a Fodel for the backing list with the Fodel type parameter inferred" in {
      val tester = new WicketTester()
      val theBackingList = List(1,2,3)
      val fodel = new Fodel(theBackingList)
      val view = new ScalaPropertyListView[Int]("votes", fodel, null)
      tester.destroy()
      view mustNot be (null)
    }
    "be able to be constructed with a pass by name parameter for the backing list, typed as a Scala list" in {
      val tester = new WicketTester()
      val theBackingList = List(1,2,3)
      val view = new ScalaPropertyListView[Int]("votes", theBackingList, null)
      tester.destroy()
      view mustNot be (null)
    }
    "be able to be constructed with a Function literal for the backing list, typed as a Java list" in {
      val tester = new WicketTester()
      val theBackingList = new JArrayList[Int]()
      theBackingList.add(1)
      theBackingList.add(2)
      theBackingList.add(3)
      val view = new ScalaPropertyListView[Int]("votes", () => theBackingList, null)
      tester.destroy()
      view mustNot be (null)
    }
    "passing the list in as a by name should work properly" in {
      val tester = new WicketTester()
      val theBackingList = new JArrayList[Int]()
      theBackingList.add(1)
      theBackingList.add(2)
      theBackingList.add(3)
      val lv = new ScalaPropertyListView[Int]("votes", theBackingList, null)
      theBackingList.add(4)
      val size = lv.getModelObject.size
      tester.destroy()
      size mustEqual 4
    }
    "passing the list in as a Scala List also works" in {
      val tester = new WicketTester()
      var theBackingList = List(1,2,3)
      val lv = new ScalaPropertyListView[Int]("votes", theBackingList, null)
      theBackingList = theBackingList ::: List(4)
      val size = lv.getModelObject.size
      tester.destroy()
      size mustEqual 4
    }
    "can still construct with a fodel using pass by name" in {
      val tester = new WicketTester()
      val theBackingList = List(1,2,3)
      val fodel = new Fodel[JList[Int]](theBackingList)
      val view = new ScalaPropertyListView[Int]("votes", fodel.getObject, null)
      tester.destroy()
      view mustNot be (null)
    }
  }
}
