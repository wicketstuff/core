package org.wicketstuff.scala.markup.html.list

import org.apache.wicket.markup.html.list.{ListItem, PropertyListView}
import org.wicketstuff.scala.model.Fodel
import org.wicketstuff.scala.traits.ScalaMarkupContainerT

/**
 * <p>Note, you may need to have "()" as the last statement in your block for the #populateItemFunc
 * parameter, as MarkupContainer#add returns MarkupContainer and due to the overloaded constructor, there must
 * be no ambiguity in the constructor call (translation - if it doesn't work, try putting () at the end of your block ;)).
 *
 * <p>Regarding only having a single constructor - see http://groups.google.com/group/maven-and-scala/msg/e541cbef70e2cbe9?")
 */
class ScalaPropertyListView[T](id:String,
                             model:Fodel[_ <: java.util.List[T]],
                             populateItemFunc:(ListItem[T]) ⇒ Unit)
  extends PropertyListView[T](id, model)
  with ScalaMarkupContainerT {

  override val self: ScalaPropertyListView[T] = this

    //class SPropertyListView[T](id:String, list: ⇒ java.util.List[_ <: T], populateItemFunc:(ListItem[T]) ⇒ Unit) extends PropertyListView[T](id, new Fodel[java.util.List[_ <: T]](list)) with ScalaWicket {
    //class SPropertyListView[T](id:String, list: ⇒ java.util.List[_ <: T], populateItemFunc:(ListItem[T]) ⇒ Unit) extends PropertyListView[T](id, model) with ScalaWicket {

    // disabled extra constructor, in order to avoid ambiguous constructor calls (type coercion to () ⇒ Unit), so you can do :
    // add(new SPropertyListView[String](new java.lang.String("presentations"), list, _.add(new SLabel("name", "asdp name"))))
    // def this(id:String, model:Fodel[_ <: java.util.List[_ <: T]], populateItemFunc:(ListItem[T]) ⇒ Unit) extends PropertyListView[T](id, model) with ScalaWicket {

    override def populateItem(li:ListItem[T]) = populateItemFunc(li)

  }
