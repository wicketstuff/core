package org.wicketstuff.scala.model

import org.apache.wicket.model.IModel

object Fodel {
  def apply[T](getter: => T): Fodel[T] = new Fodel(getter, null)

  def apply[T](getter: => T, setter: (T) => Unit): Fodel[T] = new Fodel(getter, setter)
}

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
@SerialVersionUID(1L)
class Fodel[T](getter: ⇒ T,
               setter:(T) ⇒ Unit)
  extends IModel[T] {

  def this(getter: ⇒ T) = this(getter, null)

  /**
   * Executes the embedded getter function #getter, to return the backing object.
   */
  override def getObject:T = getter

  /**
   * Executes the embedded getter function #setter, to assign the backing object.
   *
   * @throws UnsupportedOperationException if the Fodel is read-only ( has no setter functionn).
   */
  override def setObject(value: T) {
    if (setter == null)
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
class FodelString(getter: ⇒ String,
                  setter:(String) ⇒ Unit)
  extends Fodel[String](getter, setter) {

  def this(getter: ⇒ String) = this(getter, null)
}

object FodelString {
  def apply(getter: ⇒ String) = new FodelString(getter, null)
}
