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
package com.googlecode.wicket.jquery.ui.renderer;

import org.apache.wicket.util.lang.PropertyResolver;

import com.googlecode.wicket.jquery.ui.renderer.IChoiceRenderer;

/**
 * 
 * @author Sebastien Briquet - sebastien@7thweb.net
 * @deprecated TODO to be removed
 * @param <T>
 */
public abstract class AbstractChoiceRenderer<T> implements IChoiceRenderer<T>
{
	private static final long serialVersionUID = 1L;

	private final String textExpression;
	private final String valueExpression;

	public AbstractChoiceRenderer()
	{
		this.textExpression = null;
		this.valueExpression = null;
	}
	
	public AbstractChoiceRenderer(String textExpression)
	{
		this.textExpression = textExpression;
		this.valueExpression = null;
	}

	public AbstractChoiceRenderer(String textExpression, String valueExpression)
	{
		this.textExpression = textExpression;
		this.valueExpression = valueExpression;
	}

	public String getText(T object)
	{
		return this.getText(object, this.textExpression);
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

		if (object != null)
		{
			return object.toString();
		}

		return "";
	}
	
	public final String getTextExpression()
	{
		return this.textExpression;
	}

	/**
	 * @param object 
	 * @return the index as String if the object is null or the property expression has not been found
	 * 
	 */
	@Override
	public String getValue(T object, int index)
	{
		if (this.valueExpression != null)
		{
			Object value = PropertyResolver.getValue(this.valueExpression, object); //if the object is null, null is returned

			if (value != null)
			{
				return value.toString();
			}
		}

		if (object != null)
		{
			return this.getText(object); //fallback to textual representation
		}

		return Integer.toString(index);
	}

	public final String getValueExpression()
	{
		return this.valueExpression;
	}
}