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
package com.googlecode.wicket.kendo.ui.form.autocomplete;

import java.util.Locale;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.convert.IConverter;

import com.googlecode.wicket.jquery.core.renderer.ITextRenderer;
import com.googlecode.wicket.kendo.ui.renderer.ChoiceRenderer;

/**
 * Provides a Kendo UI auto-complete widget.<br/>
 *
 * @author Sebastien Briquet - sebfz1
 *
 * @param <T> the model object type
 */
public abstract class AutoCompleteTextField<T> extends AbstractAutoCompleteTextField<T, T>
{
	private static final long serialVersionUID = 1L;

	private final IConverter<T> converter;

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 */
	public AutoCompleteTextField(String id)
	{
		super(id);

		this.converter = this.newConverter();
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param renderer the {@link ChoiceRenderer}
	 */
	public AutoCompleteTextField(String id, ITextRenderer<? super T> renderer)
	{
		super(id, renderer);

		this.converter = this.newConverter();
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param model the {@link IModel}
	 */
	public AutoCompleteTextField(String id, IModel<T> model)
	{
		super(id, model);

		this.converter = this.newConverter();
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param model the {@link IModel}
	 * @param renderer the {@link ChoiceRenderer}
	 */
	public AutoCompleteTextField(String id, IModel<T> model, ITextRenderer<? super T> renderer)
	{
		super(id, model, renderer);

		this.converter = this.newConverter();
	}

	// Properties //

	@Override
	protected final String getModelValue()
	{
		return this.renderer.getText(this.getModelObject()); // renderer cannot be null.
	}

	@Override
	@SuppressWarnings("unchecked")
	public <C> IConverter<C> getConverter(Class<C> type)
	{
		if (!String.class.isAssignableFrom(this.getType())) /* TODO: manage String (property)model object in a better way */
		{
			if (type != null && type.isAssignableFrom(this.getType()))
			{
				return (IConverter<C>) this.converter;
			}
		}

		return super.getConverter(type);
	}

	// Events //

	@Override
	protected final void onSelected(AjaxRequestTarget target, T choice)
	{
		this.setModelObject(choice);
		this.onSelected(target);
	}

	/**
	 * Triggered when the user selects an item from results that matched its input
	 *
	 * @param target the {@link AjaxRequestTarget}
	 */
	protected void onSelected(AjaxRequestTarget target)
	{
	}

	// Factories //

	/**
	 * Gets a new {@link IConverter}.<br/>
	 * Used when the form component is posted and the bean type has been supplied to the constructor.
	 *
	 * @return the {@link IConverter}
	 */
	private final IConverter<T> newConverter()
	{
		return new IConverter<T>() {

			private static final long serialVersionUID = 1L;

			@Override
			public T convertToObject(String value, Locale locale)
			{
				if (value != null && value.equals(AutoCompleteTextField.this.getModelValue()))
				{
					return AutoCompleteTextField.this.getModelObject();
				}

				return null; // if the TextField value (string) does not corresponds to the current object model (ie: user specific value), returns null.
			}

			@Override
			public String convertToString(T value, Locale locale)
			{
				return AutoCompleteTextField.this.renderer.getText(value);
			}
		};
	}
}
