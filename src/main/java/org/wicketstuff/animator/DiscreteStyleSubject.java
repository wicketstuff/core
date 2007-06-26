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
 * This class animates discrete styles, i.e. ones that do not scale but have
 * discrete values that can't be interpolated
 * 
 * @author Gerolf Seitz
 * 
 */
public class DiscreteStyleSubject extends AbstractStyleSubject {

	private static final long serialVersionUID = 1L;

	/**
	 * the threshold of the animation describes, when the animation should
	 * switch to the final value of the animation. a threshold of 0.5 means,
	 * that for the first half of the animation, the from value is used, whereas
	 * in the second half, the to value is used.<p/> defaults to 0.5
	 */
	private String threshold;

	/**
	 * the property which should be animated
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
	public DiscreteStyleSubject(IModel targets, String property, int from,
			int to) {
		this(targets, property, from, to, 0.5);
	}

	/**
	 * Constructs the subject
	 * 
	 * @param targets
	 *            the targets for this subject. see
	 *            {@link AbstractStyleSubject#target()}
	 * @param property
	 *            the property which should be animated.
	 * @param from
	 *            the starting value of the animation.
	 * @param to
	 *            the final value of the animation.
	 * @param threshold
	 *            the threshold, when the animation should switch from the
	 *            {@code from} value to the {@code to} value. <br>
	 *            0 <= threshold <= 1
	 */
	public DiscreteStyleSubject(IModel targets, String property, int from,
			int to, double threshold) {
		target(targets);
		from(String.valueOf(from));
		to(String.valueOf(to));
		threshold(String.valueOf(threshold));
		property(property);
	}

	/**
	 * sets the threshold for this subject
	 * 
	 * @param threshold
	 *            the threshold, defining when the animation should switch from
	 *            the {@code from} value to the {@code to} value.
	 * @return this {@link DiscreteStyleSubject}
	 */
	public DiscreteStyleSubject threshold(String threshold) {
		this.threshold = threshold;
		return this;
	}

	/**
	 * sets the property which should be animated
	 * 
	 * @param property
	 *            the property which should be animated.
	 * @return this {@link DiscreteStyleSubject}
	 */
	public DiscreteStyleSubject property(String property) {
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
		sb.append("', ");
		super.writeArguments(sb);
		sb.append(", ");
		sb.append(threshold);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.wicketstuff.animator.AbstractStyleSubject#getStyleType()
	 */
	@Override
	protected String getStyleType() {
		return "DiscreteStyleSubject";
	}

}
