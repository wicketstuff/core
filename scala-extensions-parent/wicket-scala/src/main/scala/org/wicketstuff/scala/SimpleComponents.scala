package org.wicketstuff.scala

/**
 * A collection of simple componenets, most of which simply allow you to use Fodel's 
 * directly, or pass in anonymous functions for their onSubmit methods etc.
 * 
 * @author Antony Stubbs
 */

import org.apache.wicket.markup.html.basic.Label
import org.apache.wicket.markup.html.form.{Form, TextField}
import org.apache.wicket.markup.html.link.Link
import org.apache.wicket.markup.html.list.{ListView, ListItem, PropertyListView}
import org.apache.wicket.model.{IModel, Model}

class SForm[T](id:String, model:IModel[T] , onSubmitFunc: ⇒ Unit) extends Form[T](id) {

  override def onSubmit = onSubmitFunc

}

class SLabel(id:String, gtr: ⇒ String) extends Label[String](id, new Fodel(gtr, null)) {
  
  def print = println(gtr)
  
}

class SLink(id:String, onClickFunc: ⇒ Unit) extends Link(id) {

  override def onClick = onClickFunc

}

class SListView[T](id:String, list:java.util.List[T], populateItemFunc:(ListItem[T]) ⇒ Unit) extends ListView[T](id, list) {

  override def populateItem(li:ListItem[T]) = populateItemFunc(li)

}

/**
 * <p>Note, you may need to have "()" as the last statement in your block for the #populateItemFunc 
 * parameter, as MarkupContainer#add returns MarkupContainer and due to the overloaded constructor, there must
 * be no ambiguity in the constructor call (translation - if it doesn't work, try putting () at the end of your block ;)).
 * 
 * <p>Regarding only having a single contructor - see http://groups.google.com/group/maven-and-scala/msg/e541cbef70e2cbe9?")
 */
class SPropertyListView[T](id:String, model:Fodel[_ <: java.util.List[_ <: T]], populateItemFunc:(ListItem[T]) ⇒ Unit) extends PropertyListView[T](id, model) with ScalaWicket {
//class SPropertyListView[T](id:String, list: ⇒ java.util.List[_ <: T], populateItemFunc:(ListItem[T]) ⇒ Unit) extends PropertyListView[T](id, new Fodel[java.util.List[_ <: T]](list)) with ScalaWicket {
  //class SPropertyListView[T](id:String, list: ⇒ java.util.List[_ <: T], populateItemFunc:(ListItem[T]) ⇒ Unit) extends PropertyListView[T](id, model) with ScalaWicket {
  
  // disabled extra constructor, in order to avoid ambiguos constructor calls (type coercion to () ⇒ Unit), so you can do :
  // add(new SPropertyListView[String](new java.lang.String("presentations"), list, _.add(new SLabel("name", "asdp name"))))
  // def this(id:String, model:Fodel[_ <: java.util.List[_ <: T]], populateItemFunc:(ListItem[T]) ⇒ Unit) extends PropertyListView[T](id, model) with ScalaWicket {
  
  override def populateItem(li:ListItem[T]) = populateItemFunc(li)

}

class STextField[T](id:String, fodel:Fodel[T]) extends TextField[T](id, fodel) {
  
  def this(id:String, getter: ⇒ T, setter:(T) ⇒ Unit) = this(id, new Fodel[T](getter, setter ) )
  
  def print = println(fodel.getObject)
 
}