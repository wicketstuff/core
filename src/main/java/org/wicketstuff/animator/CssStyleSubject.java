package org.wicketstuff.animator;

import org.apache.wicket.model.IModel;

/**
 * This class is used to animate between two styles defined using css.
 * 
 * @author Gerolf
 * 
 */
public class CssStyleSubject extends AbstractStyleSubject {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a subject which animates between the fromStyleOrClass and the
	 * toStyleOrClass.
	 * 
	 * @param targets
	 *            the targets for the subject. see
	 *            {@link AbstractStyleSubject#target()}
	 * @param fromStyleOrClass
	 *            the style or css class which is used as the starting value of
	 *            the animation.
	 * @param toStyleOrClass
	 *            the style or css class which is used as the final value of the
	 *            animation.
	 */
	public CssStyleSubject(IModel targets, String fromStyleOrClass,
			String toStyleOrClass) {
		target(targets);
		from(fromStyleOrClass);
		to(toStyleOrClass);
	}

	/**
	 * Constructs a subject which animates between the current style of the
	 * targets and the style specified in toStyleOrClass.
	 * 
	 * @param targets
	 *            the targets for the subject. see
	 *            {@link AbstractStyleSubject#target()}
	 * @param toStyleOrClass
	 *            the style or css class which is used as the final value of the
	 *            animation.
	 */
	public CssStyleSubject(IModel targets, String toStyleOrClass) {
		target(targets);
		from(null);
		to(toStyleOrClass);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.wicketstuff.animator.AbstractStyleSubject#writeArguments(java.lang.StringBuilder)
	 */
	@Override
	protected void writeArguments(StringBuilder sb) {
		if (from != null) {
			sb.append(", ");
			sb.append("'");
			sb.append(from);
			sb.append("'");
		}
		sb.append(", ");
		sb.append("'");
		sb.append(to);
		sb.append("'");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.wicketstuff.animator.AbstractStyleSubject#getStyleType()
	 */
	@Override
	protected String getStyleType() {
		return "CSSStyleSubject";
	}
}
