package org.wicketstuff.scala

import _root_.java.util.{ArrayList => JArrayList, List => JList}

import org.apache.wicket.markup.html.basic.Label
import org.apache.wicket.model.{IModel, Model}
import org.apache.wicket.util.tester.WicketTester
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{MustMatchers, WordSpec}

/**
 * @author Antony Stubbs
 */
@RunWith(classOf[JUnitRunner])
class ScalaPackageSpec
  extends WordSpec
  with MustMatchers {

  "NullSafe operater" should {
    
  case class Company(employee:Employee)
  case class Employee(address:Address){
    def lookupAddressFromDb:Address = throw new NullPointerException("db error")
    def ?():Address = throw new NullPointerException("db error")
  }
  case class Address(city:String)
 
    "return the leaf value when working with non-null tree" in {
      val company = Company(Employee(Address("Auckland")))
      val result = ?( company.employee.address.city )
      result mustEqual "Auckland"
    }
    "return null when working with a null element at some point in the tree" in {
      val company = Company(null)
      val result = ?( company.employee.address.city )
      result.asInstanceOf[AnyRef] mustBe (null)
    }
    "re-throw the NPE when working with a method which actually throws a NullPointerException" in {
      val company = Company(Employee(Address("Auckland")))
      intercept[NullPointerException] {
        ?( company.employee.lookupAddressFromDb.city )   
      }
    }
    "also works for nested functions called ?" in {
      val company = Company(Employee(Address("Auckland")))
      intercept[NullPointerException]{?( company.employee.?().city)} 
    }   
  }


  "Fodel converters" should {
    "convert anonymous functions to Fodels" in {
      val tester = new WicketTester
      var clickCount = 0
      val getter: IModel[_] = () => { clickCount }
      val l = new Label("name", getter)
      clickCount += 1
      val modelObject = l.getDefaultModelObject
      tester.destroy()
      modelObject mustEqual 1
    }
    "convert anonymous functions to Fodels with shorter syntax" in {
      val tester = new WicketTester
      var clickCount = 0
      val getter: IModel[_] = () => clickCount
      val l = new Label("name", getter)
      clickCount += 1
      val modelObject = l.getDefaultModelObject
      tester.destroy()
      modelObject mustEqual 1
    }
  }
  
  "Implicit list conversion" should {
    "convert to an ArrayList implicitly" in {
      def takesAnJUList(list:JList[Int]) = list
      val result = takesAnJUList(List(1,2,3))
      result.size mustEqual 3
      result.isInstanceOf[JArrayList[_]] mustBe true
    }
    "allow us to create a ListView out of a Scala List" in {
      import org.apache.wicket.markup.html.list._
      val tester = new WicketTester
      val lv = new ListView[Int]("myListView", Model.ofList(List(1,2,3))) {
        override def populateItem(li:ListItem[Int]) {
          println("o Hi!")
        }
      }
      val size = lv.getModelObject.size
      tester.destroy()
      size mustEqual 3
    }
  }

}
