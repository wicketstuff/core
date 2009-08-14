package org.wicketstuff.scala

import org.apache.wicket.markup.html.basic.Label
import org.apache.wicket.model.PropertyModel

import org.specs._
import org.specs.runner._

/**
 * @author Antony Stubbs
 */
class ScalaWicketSpecs extends Specification with JUnit with ScalaTest with ScalaWicket {

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
      result mustEq "Auckland"
    }
    "return null when working with a null element at some point in the tree" in {
      val company = Company(null)
      val result = ?( company.employee.address.city )
      result must beNull
    }
    "re-throw the NPE when working with a method which actually throws a NullPointerException" in {
      val company = Company(Employee(Address("Auckland")))
      ?( company.employee.lookupAddressFromDb.city ) aka "the null-safe lookup method" must throwA[NullPointerException]
    }
    "also works for nested functions called ?" in {
      val company = Company(Employee(Address("Auckland")))
      ?( company.employee.?.city ) aka "the null-safe lookup method" must throwA[NullPointerException]
    }   
  }

  import org.apache.wicket.util.tester.WicketTester
  
  "Fodel converters" should { doBefore{ new WicketTester() }
    "convert anonymous functions to Fodels" in {
      var clickCount = 0
      val l = new Label("name", () => { clickCount })
      clickCount += 1
      l.getDefaultModelObject must be(1)
    }
    "convert anonymous functions to Fodels with shorter syntax" in {
      var clickCount = 0
      val l = new Label("name", () => clickCount)
      clickCount += 1
      l.getDefaultModelObject must be(1)
    }
  }
  
  "Implicit list conversion" should {
    "convert to an ArrayList implicitly" in {
      def takesAnJUList(list:java.util.List[Int]) = list
      val result = takesAnJUList(List(1,2,3))
      result.size mustBe 3
      result.isInstanceOf[java.util.ArrayList[Int]] mustBe true
    }
    "allow us to create a ListView out of a Scala List" in {
      import org.apache.wicket.markup.html.list._
      val wt = new WicketTester
      val lv = new ListView[Int]("myListView", List(1,2,3)) {
        override def populateItem(li:ListItem[Int]) = println("o Hi!")
      }
      lv.getModelObject.size mustBe 3
    }
  }

}