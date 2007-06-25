package org.wicketstuff.animator;

/**
 * This class can be used to let any arbitrary javascript code be executed
 * during the animation. The code is embedded inside a function:
 * {@code function (value) { ... your javascript code here }}<br>
 * The function body can have references to the parameter value.
 * 
 * @author Gerolf
 * 
 */
public abstract class JavascriptSubject implements IAnimatorSubject {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.wicketstuff.animator.IAnimatorSubject#getJavaScript()
	 */
	public String getJavaScript() {
		return "function (value) {" + getFunctionBody() + "};";
	}

	/**
	 * This method should return the body of the animation function. References
	 * to the parameter {@code value} can be made.
	 * 
	 * @return the javascript of the function body
	 */
	protected abstract String getFunctionBody();

}
