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
package com.googlecode.wicket.jquery.ui.kendo.combobox;

import org.apache.wicket.core.util.lang.PropertyResolver;

import com.googlecode.wicket.jquery.core.renderer.TextRenderer;

/**
 * Provides the default ComboBox renderer.
 *
 * @param <T> the model object type
 * @author Sebastien Briquet - sebfz1
 */
public class ComboBoxRenderer<T> extends TextRenderer<T>
{
	private static final long serialVersionUID = 1L;
	private static final String TEXT_FIELD = "cb_text";
	private static final String VALUE_FIELD = "cb_value";

	private String valueExpression;

	/**
	 * Constructor
	 */
	public ComboBoxRenderer()
	{
		super();

		this.valueExpression = null;
	}

	/**
	 * Constructor
	 * @param textExpression the property expression that will be resolved for the bean supplied to {@link #getText(Object)}
	 */
	public ComboBoxRenderer(String textExpression)
	{
		super(textExpression);

		this.valueExpression = null;
	}

	/**
	 * Constructor
	 * @param textExpression the property expression that will be resolved for the bean supplied to {@link #getText(Object)}
	 * @param valueExpression the property expression that will be resolved for the bean supplied to {@link #getValue(Object)}
	 */
	public ComboBoxRenderer(String textExpression, String valueExpression)
	{
		super(textExpression);

		this.valueExpression = valueExpression;
	}

	/**
	 * Gets the name of the field that acts as the 'dataTextField' in the JSON response.
	 * @return the name of the text field
	 */
	public String getTextField()
	{
		String testExpression = super.getExpression();

		if (testExpression != null)
		{
			return testExpression;
		}

		return TEXT_FIELD;
	}

	/**
	 * Gets the value that should be renderer for the supplied object
	 * @param object the T object
	 * @return the value
	 */
	public String getValue(T object)
	{
		if (this.valueExpression != null)
		{
			Object value = PropertyResolver.getValue(this.valueExpression, object); //if the object is null, null is returned

			if (value != null)
			{
				return value.toString();
			}
		}

		return this.getText(object);
	}

	/**
	 * Gets the name of the field that acts as the 'dataValueField' in the JSON response.
	 * @return the name of the value field
	 */
	public String getValueField()
	{
		if (this.valueExpression != null)
		{
			return this.valueExpression;
		}

		return VALUE_FIELD;
	}
}