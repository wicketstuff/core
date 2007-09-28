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

import java.io.Serializable;
import java.util.Collection;

import org.apache.wicket.model.IModel;

/**
 * Base class for StyleSubjects.
 * 
 * @author Gerolf Seitz
 * 
 */
public abstract class AbstractStyleSubject implements Serializable, IAnimatorSubject
{

	/**
	 * the model representing the targets of this subject. the modelobject has
	 * to be either String for a single component-id or String[] or Collection<String>
	 * for several component-ids.
	 */
	protected IModel target;

	/**
	 * represents the starting value of the animation for this subject. this can
	 * be either the string of a numerical value (in case of a
	 * NumericalStyleSubject) or a style definition or css classname (in case of
	 * CssStyleSubject).
	 * 
	 * @see NumericalStyleSubject
	 * @see CssStyleSubject
	 * @see ColorStyleSubject
	 * @see DiscreteStyleSubject
	 */
	protected String from;

	/**
	 * represents the final value of the animation for this subject. this can be
	 * either the string of a numerical value (in case of a
	 * NumericalStyleSubject) or a style definition or css classname (in case of
	 * CssStyleSubject).
	 * 
	 * @see NumericalStyleSubject
	 * @see CssStyleSubject
	 * @see ColorStyleSubject
	 * @see DiscreteStyleSubject
	 */
	protected String to;

	/**
	 * sets the target for this subject
	 * 
	 * @param target
	 *            the target for this subject
	 * @return this {@code AbstractStyleSubject}
	 */
	public AbstractStyleSubject target(IModel target)
	{
		this.target = target;
		return this;
	}

	/**
	 * sets the from value of the subject. depending on the kind of StyleSubject
	 * this can be a numerical value, a style definition ...
	 * 
	 * @param from
	 *            the from value of the subject
	 * @return this {@code AbstractStyleSubject}
	 */
	public AbstractStyleSubject from(String from)
	{
		this.from = from;
		return this;
	}

	/**
	 * sets the to value of the subject. depending on the kind of StyleSubject
	 * this can be a numerical value, a style definition, ...
	 * 
	 * @param to
	 *            the to value of the subject
	 * @return this {@code AbstractStyleSubject}
	 */
	public AbstractStyleSubject to(String to)
	{
		this.to = to;
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.wicketstuff.animator.IAnimatorSubject#getJavaScript()
	 */
	public String getJavaScript()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("new ");
		sb.append(getStyleType());
		sb.append("(");
		writeTargets(sb);
		writeArguments(sb);
		sb.append(")");
		return sb.toString();
	}

	private void writeTargets(StringBuilder sb)
	{
		sb.append("[");
		Object obj = target.getObject();
		String[] ids;
		if (obj instanceof String)
		{
			ids = new String[] { obj.toString() };
		}
		else if (obj instanceof String[])
		{
			ids = (String[])target.getObject();
		}
		else if (obj instanceof Collection)
		{
			ids = (String[])((Collection)obj).toArray(new String[0]);
		}
		else
		{
			throw new IllegalArgumentException(
					"target must be of type String, String[], Collection<String>");
		}
		for (int i = 0; i < ids.length; i++)
		{
			sb.append("Wicket.$('");
			sb.append(ids[i]);
			sb.append("')");
		}
		sb.append("]");
	}

	/**
	 * writes the javascript code for the arguments to a StringBuilder
	 * 
	 * @param sb
	 *            the StringBuilder to which the arguments should be written
	 */
	protected void writeArguments(StringBuilder sb)
	{
		sb.append(", ");

		sb.append("'");
		sb.append(from);
		sb.append("'");

		sb.append(", ");

		sb.append("'");
		sb.append(to);
		sb.append("'");
	}

	/**
	 * @return the class name of the corresponding Javascript class
	 * @see org.wicketstuff.animator.animator.js
	 */
	protected abstract String getStyleType();
}
