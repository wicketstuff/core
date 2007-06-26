/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wicketstuff.animator;

import org.apache.wicket.model.IModel;

/**
 * animates a pixel-based style property between two integer values.
 * 
 * @author Gerolf Seitz
 * 
 */
public class NumericalStyleSubject extends AbstractStyleSubject {

	private static final long serialVersionUID = 1L;

	/**
	 * the unit of the style property
	 */
	private String unit;

	/**
	 * the style property
	 */
	private String property;

	/**
	 * Constructs the subject
	 * 
	 * @param targets
	 *            the targets for this subject. see
	 *            {@link AbstractStyleSubject#target()}
	 * @param property
	 *            the property which should be animated
	 * @param from
	 *            the starting value of the animation
	 * @param to
	 *            the final value of the animation
	 */
	public NumericalStyleSubject(IModel targets, String property, int from,
			int to) {
		this(targets, property, from, to, "px");
	}

	/**
	 * Constructs the subject
	 * 
	 * @param targets
	 *            the targets for this subject. see
	 *            {@link AbstractStyleSubject#target()}
	 * @param property
	 *            the property which should be animated
	 * @param from
	 *            the starting value of the animation
	 * @param to
	 *            the final value of the animation
	 * @param unit
	 *            the unit of the property
	 */
	public NumericalStyleSubject(IModel targets, String property, int from,
			int to, String unit) {
		target(targets);
		from(Integer.toString(from));
		to(Integer.toString(to));
		unit(unit);
		property(property);
	}

	/**
	 * sets the unit of the style-property
	 * 
	 * @param unit
	 *            the unit of the style-property
	 * @return this {@link NumericalStyleSubject}
	 */
	public NumericalStyleSubject unit(String unit) {
		this.unit = unit;
		return this;
	}

	/**
	 * sets the property which should be animated
	 * 
	 * @param property
	 *            the property which should be animated
	 * @return this {@link NumericalStyleSubject}
	 */
	public NumericalStyleSubject property(String property) {
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
		sb.append(", '");
		sb.append(unit);
		sb.append("'");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.wicketstuff.animator.AbstractStyleSubject#getStyleType()
	 */
	@Override
	protected String getStyleType() {
		return "NumericalStyleSubject";
	}

}
