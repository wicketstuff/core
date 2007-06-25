package org.wicketstuff.animator;

import org.apache.wicket.model.IModel;

/**
 * This class is used to animate a color based style-property between two hex
 * values.
 * 
 * @author Gerolf
 * 
 */
public class ColorStyleSubject extends AbstractStyleSubject {

	private static final long serialVersionUID = 1L;

	/**
	 * the color based style property (eg. color, background-color)
	 */
	private String property;

	/**
	 * Constructs the ColorStyleSubject
	 * 
	 * @param targets
	 *            the targets for this subject. see
	 *            {@link AbstractStyleSubject#target()}
	 * @param property
	 *            the property which should be animated
	 * @param from
	 *            the starting hex value
	 * @param to
	 *            the final hex value
	 */
	public ColorStyleSubject(IModel targets, String property, String from,
			String to) {
		target(targets);
		from(from);
		to(to);
		property(property);
	}

	/**
	 * sets the color based style-property of the subject
	 * 
	 * @param property
	 *            the property of the subject
	 * @return this {@codeColorStyleSubject}
	 */
	public ColorStyleSubject property(String property) {
		this.property = property;
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.wicketstuff.animator.AbstractStyleSubject#writeArguments(java.lang.StringBuilder)
	 */
	@Override
	protected void writeArguments(StringBuilder sb) {
		sb.append(", ");
		sb.append("'");
		sb.append(property);
		sb.append("'");
		super.writeArguments(sb);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.wicketstuff.animator.AbstractStyleSubject#getStyleType()
	 */
	@Override
	protected String getStyleType() {
		return "ColorStyleSubject";
	}

}
