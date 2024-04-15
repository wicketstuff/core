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
package com.googlecode.wicket.kendo.ui.renderer;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.core.util.lang.PropertyResolver;
import com.github.openjson.JSONObject;

import com.googlecode.wicket.jquery.core.renderer.IChoiceRenderer;
import com.googlecode.wicket.jquery.core.renderer.TextRenderer;

/**
 * Default implementation of {@link IChoiceRenderer}.
 *
 * @author Sebastien Briquet - sebfz1
 *
 * @param <T> the model object type
 */
public class ChoiceRenderer<T> extends TextRenderer<T> implements IChoiceRenderer<T>
{
	private static final long serialVersionUID = 1L;
	private static final String VALUE_FIELD = "value";

	private String valueExpression;

	/**
	 * Constructor
	 */
	public ChoiceRenderer()
	{
		super();

		this.valueExpression = null;
	}

	/**
	 * Constructor
	 *
	 * @param textExpression the property expression that will be resolved for the bean supplied to {@link #getText(Object)}
	 */
	public ChoiceRenderer(String textExpression)
	{
		super(textExpression);

		this.valueExpression = null;
	}

	/**
	 * Constructor
	 *
	 * @param textExpression the property expression that will be resolved for the bean supplied to {@link #getText(Object)}
	 * @param valueExpression the property expression that will be resolved for the bean supplied to {@link #getValue(Object)}
	 */
	public ChoiceRenderer(String textExpression, String valueExpression)
	{
		super(textExpression);

		this.valueExpression = valueExpression;
	}
	
	// Properties //

	@Override
	public String getValueField()
	{
		if (this.valueExpression != null)
		{
			return this.valueExpression;
		}

		return VALUE_FIELD;
	}

	@Override
	public String getValue(T object)
	{
		if (this.valueExpression != null)
		{
			Object value = PropertyResolver.getValue(this.valueExpression, object); // if the object is null, null is returned

			if (value != null)
			{
				return value.toString();
			}
		}

		return this.getText(object);
	}
	
	@Override
	public List<String> getFields()
	{
		return Arrays.asList(this.getTextField(), this.getValueField());
	}

	// Methods //

	@Override
	public JSONObject render(T object)
	{
		final JSONObject o = new JSONObject();
		
		o.put(this.getTextField(), this.getText(object));
		o.put(this.getValueField(), this.getValue(object));

		return o;
	}
}
