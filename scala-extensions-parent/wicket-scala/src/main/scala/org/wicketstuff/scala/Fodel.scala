package org.wicketstuff.scala

import javax.swing._
import java.applet._
import org.apache.wicket.markup.html.basic.Label
import org.apache.wicket.model.AbstractReadOnlyModel
import org.apache.wicket.model._
import org.apache.wicket.model.IModel

/**
 * A functional version of a wicket IModel. Uses anonymous functions to find backing objects, instead of reflection.
 *
 * @param getter the function which retrieves the backing object value
 * @param setter the function which assigns the backing object value with the passed in value T
 * @see http://technically.us/code/x/the-escape-hatch
 * @author Nathan Hamblen
 * @author Antony Stubbs
 * @author Jan Kriesten
 */
@serializable
@SerialVersionUID(1L)
class Fodel[T](getter: ⇒ T, setter:(T) ⇒ Unit) extends IModel[T] {

  def this(getter: ⇒ T) = this(getter, null)

  /**
   * Constructs a fodel with only a setter, which also returns the backing object, so can be used as a setter
   */
  //def this(setter:(T) => T) = this(null, setter)

  /**
   * Executes the embedded getter function #getter, to return the backing object.
   */
  override def getObject:T = getter

  /**
   * Executes the embedded getter function #setter, to assign the backing object.
   *
   * @throws UnsupportedOperationException if the Fodel is read-only ( has no setter functionn).
   */
  override def setObject(value:T):Unit = {
    if(setter==null)
      throw new UnsupportedOperationException( "You cannot set the object on a readonly model.")
  	setter(value)
  }

  def detach = ()

}

/**
 * Basic extension to the Fodel that uses strings.
 *
 * @author Antony Stubbs
 */
class FodelString(getter: ⇒ String, setter:(String) ⇒ Unit) extends Fodel[String](getter, setter) {
  def this(getter: ⇒ String) = this(getter, null)
}