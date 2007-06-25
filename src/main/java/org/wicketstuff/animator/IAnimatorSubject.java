package org.wicketstuff.animator;

/**
 * The interface for a subject used for an animation.
 * 
 * @author Gerolf
 * 
 */
public interface IAnimatorSubject {

	/**
	 * 
	 * @return the string of the javascript code which is passed as an argument
	 *         to the .addSubject method of the javascript class Animator.
	 */
	public abstract String getJavaScript();

}