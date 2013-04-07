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
package com.googlecode.wicket.jquery.core.renderer;

import org.apache.wicket.core.util.lang.PropertyResolver;

/**
 * Provides the default {@link ITextRenderer}
 *
 * @param <T> the model object type
 * @author Sebastien Briquet - sebfz1
 */
public class TextRenderer<T> implements ITextRenderer<T>
{
	private static final long serialVersionUID = 1L;

	private final String expression;

	/**
	 * Constructor
	 */
	public TextRenderer()
	{
		this.expression = null;
	}

	/**
	 * Constructor
	 * @param expression the property expression that will be resolved for the bean supplied to {@link #getText(Object)}
	 */
	public TextRenderer(String expression)
	{
		this.expression = expression;
	}

	/**
	 * Gets the expression supplied to the constructor
	 * @return null if the default constructor has been used
	 */
	public String getExpression()
	{
		return this.expression;
	}

	@Override
	public String getText(T object)
	{
		if (this.expression == null && object != null)
		{
			return object.toString();
		}

		return this.getText(object, this.expression);
	}

	@Override
	public String getText(T object, String expression)
	{
		if (expression != null)
		{
			Object value = PropertyResolver.getValue(expression, object); //if the object is null, null is returned

			if (value != null)
			{
				return value.toString();
			}
		}

		return "";
	}
}