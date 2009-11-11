package org.wicketstuff.scala

/**
 * <p>The "language level" Wicket extensions.</p>
 * 
 * <p>Note that much better conversion support is coming with Scala 2.8 
 * so as soon as that's out, we will switch to simply using the 
 * library for list conversion.</p>
 *
 * <p>If the conversions here are not sufficient for you, check out Scala-JavaUtils library:
 * http://github.com/jorgeortiz85/scala-javautils which has quite a large selection. Use 
 * this until Scala 2.8 comes out.</p>
 * 
 * @author Antony Stubbs
 */
trait ScalaWicket {

  /**
   * Safe dereference operator. E.g. ?(a.b.c.null.dd)
   */
  def ?[A](block: ⇒ A) = {
    try { block } catch {
      // checks to see if the 3rd to last method called in the stack, is the ?() function, 
      // which means the null pointer exception was actually due to a null object, 
      // otherwise the ?() function would be further down the stack.
      case e: NullPointerException if e.getStackTrace()(2).getMethodName == "$qmark" ⇒ {null}
      // for any other NullPointerException, or otherwise, re-throw the exception.
      case e => throw e
    }
  }

  /**
   * Converts a by name parameter into a Fodel.
   */
  implicit def func2Fodel1[T](gtr: ⇒ T):Fodel[T] = new Fodel[T](gtr, null)

  /**
   * Converts an anonymous fuction of zero arguments into a Fodel.
   */
  implicit def func2Fodel2[T](gtr: () ⇒ T):Fodel[T] = new Fodel[T](gtr(), null)
  
  /**
   * Converts an anonymous fuction, typed with a List of something, into a Fodel. Useful for ListViews etc.
   */
  implicit def func2Fodel5[T](listFunc: () ⇒ List[T]):Fodel[java.util.List[T]] = new Fodel[java.util.List[T]](listFunc(), null)
  
  /**
   * Converts an by name List parameter, into a java.util.List Fodel. Useful for ListViews etc.
   */
  implicit def func2Fodel4[T](list: ⇒ List[T]):Fodel[java.util.List[T]] = new Fodel[java.util.List[T]](list, null)
  
  /**
   * Automatically converts between Scala Sequences and Java's ArrayList. Used for 
   * example, when constructing a {@link org.apache.wicket.markup.html.list.ListView}
   * and passing in a Scala List, this function will implicitly convert it to an ArrayList.
   * 
   * @see http://stubbisms.wordpress.com/2009/02/18/fighting-scala-scala-to-java-list-conversion/
   */
  implicit def listToJavaList[T](l: Seq[T]):java.util.List[T] = 
    l.foldLeft( new java.util.ArrayList[T]( l.size ) ) {
      (al, e) ⇒ al.add(e) 
      al
    }
  
  /**
   * Converts a Fodel, typed with a List of something, into a Fodel typed with a java.util.List 
   * of something. Useful for ListViews etc.
   */
  implicit def fodelListToFodelJavaList[T](f: Fodel[List[T]]):Fodel[java.util.List[T]] = {
    new Fodel[java.util.List[T]](
      {listToJavaList(f.getObject)},
      {f.setObject(_)})
  }
    
}