package org.wicketstuff

import org.apache.wicket.markup.Markup
import org.apache.wicket.markup.head.{JavaScriptHeaderItem, JavaScriptReferenceHeaderItem, CssHeaderItem, CssReferenceHeaderItem}
import org.apache.wicket.request.resource.{JavaScriptResourceReference, CssResourceReference}
import org.apache.wicket.{Component, MarkupContainer}
import org.wicketstuff.scala.model.Fodel

import _root_.java.util.{List => JList, ArrayList => JArrayList}
import org.wicketstuff.scala.traits.{ScalaMarkupContainerT, ScalaComponentT}

import _root_.scala.language.implicitConversions

/**
 *
 */
package object scala {

  implicit class ScalaComponentOps(val component: Component)
    extends ScalaComponentT {

    override val self: component.type = component
  }

  implicit class ScalaMarkupContainerOps(val container: MarkupContainer)
    extends ScalaMarkupContainerT {

    override val self: container.type = container
  }

  implicit def stringToMarkup(a: String): Markup = Markup.of(a)

  /**
   * Safe dereference operator. E.g. ?(a.b.c.null.dd)
   */
  def ?[A](block: ⇒ A) = {
    try { block } catch {
      // checks to see if the 3rd to last method called in the stack, is the ?() function,
      // which means the null pointer exception was actually due to a null object,
      // otherwise the ?() function would be further down the stack.
      case e: NullPointerException if e.getStackTrace()(2).getMethodName == "$qmark" ⇒ null
      // for any other NullPointerException, or otherwise, re-throw the exception.
      case e: Exception => throw e
    }
  }

  /**
   * Converts a by name parameter into a Fodel.
   */
  implicit def func2Fodel1[T](gtr: ⇒ T):Fodel[T] = new Fodel[T](gtr, null)

  /**
   * Converts an anonymous function of zero arguments into a Fodel.
   */
  implicit def func2Fodel2[T](gtr: () ⇒ T):Fodel[T] = new Fodel[T](gtr(), null)

  /**
   * Converts an anonymous function, typed with a List of something, into a Fodel. Useful for ListViews etc.
   */
  implicit def func2Fodel5[T](listFunc: () ⇒ List[T]):Fodel[JList[T]] = new Fodel[JList[T]](listFunc(), null)

  /**
   * Converts an by name List parameter, into a java.util.List Fodel. Useful for ListViews etc.
   */
  implicit def func2Fodel4[T](list: ⇒ List[T]):Fodel[JList[T]] = new Fodel[JList[T]](list, null)

  /**
   * Automatically converts between Scala Sequences and Java's ArrayList. Used for
   * example, when constructing a org.apache.wicket.markup.html.list.ListView
   * and passing in a Scala List, this function will implicitly convert it to an ArrayList.
   *
   * @see http://stubbisms.wordpress.com/2009/02/18/fighting-scala-scala-to-java-list-conversion/
   */
  implicit def seqToJavaList[T](sequence: Seq[T]): JList[T] =
    sequence.foldLeft( new JArrayList[T]( sequence.size ) ) {
      (arrayList, element) ⇒ arrayList.add(element)
      arrayList
    }

  /**
   * Converts a Fodel, typed with a Seq of something, into a Fodel typed with a java.util.List
   * of something. Useful for ListViews etc.
   */
  implicit def fodelListToFodelJavaList[T](f: Fodel[_ <: Seq[T]]): Fodel[JList[T]] = {
    new Fodel[JList[T]](
      {seqToJavaList(f.getObject)},
      {f.setObject(_)}
    )
  }

  implicit def cssRefToHeaderItem(reference: CssResourceReference): CssReferenceHeaderItem = CssHeaderItem.forReference(reference)

  implicit def jsRefToHeaderItem(reference: JavaScriptResourceReference): JavaScriptReferenceHeaderItem =
    JavaScriptHeaderItem.forReference(reference)

}
