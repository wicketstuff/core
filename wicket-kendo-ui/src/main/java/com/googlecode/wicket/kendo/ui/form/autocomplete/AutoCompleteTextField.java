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

import java.util.List;
import java.util.Locale;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.convert.IConverter;

import com.googlecode.wicket.jquery.core.renderer.ITextRenderer;

/**
 * Provides a Kendo UI auto-complete widget.<br>
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
	protected AutoCompleteTextField(String id)
	{
		super(id);

		this.converter = this.newConverter();
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param renderer the {@link ITextRenderer}
	 */
	protected AutoCompleteTextField(String id, ITextRenderer<? super T> renderer)
	{
		super(id, renderer);

		this.converter = this.newConverter();
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param type type for field validation
	 */
	protected AutoCompleteTextField(String id, Class<T> type)
	{
		super(id, type);

		this.converter = this.newConverter();
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param renderer the {@link ITextRenderer}
	 * @param type type for field validation
	 */
	protected AutoCompleteTextField(String id, ITextRenderer<? super T> renderer, Class<T> type)
	{
		super(id, renderer, type);

		this.converter = this.newConverter();
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param model the {@link IModel}
	 */
	protected AutoCompleteTextField(String id, IModel<T> model)
	{
		super(id, model);

		this.converter = this.newConverter();
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param model the {@link IModel}
	 * @param renderer the {@link ITextRenderer}
	 */
	protected AutoCompleteTextField(String id, IModel<T> model, ITextRenderer<? super T> renderer)
	{
		super(id, model, renderer);

		this.converter = this.newConverter();
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param model the {@link IModel}
	 * @param type the type of the bean. This parameter should be supplied for the internal converter ({@link #getConverter(Class)}) to be used.
	 */
	protected AutoCompleteTextField(String id, IModel<T> model, Class<T> type)
	{
		super(id, model, type);

		this.converter = this.newConverter();
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param model the {@link IModel}
	 * @param renderer the {@link ITextRenderer}
	 * @param type the type of the bean. This parameter should be supplied for the internal converter ({@link #getConverter(Class)}) to be used.
	 */
	protected AutoCompleteTextField(String id, IModel<T> model, ITextRenderer<? super T> renderer, Class<T> type)
	{
		super(id, model, renderer, type);

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
		// TODO add/verify sample with property (string) model
		if (type != null && type.isAssignableFrom(this.getType()))
		{
			return (IConverter<C>) this.converter;
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
	 * Gets a new {@link IConverter}.<br>
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
				if (value != null)
				{
					if (value.equals(getModelValue()))
					{
						// the value already corresponds to the model object, no conversion to be done
						return getModelObject();
					}
					else
					{
						// the value has been entered manually; try getting the object...
						List<T> choices = getChoices(value);

						if (!choices.isEmpty())
						{
							for (T choice : choices)
							{
								if (renderer.getText(choice).equals(value))
								{
									return choice;
								}
							}
						}
					}
				}

				// the string value does not corresponds to the current object model (ie: user specific value)
				// and has not been found in the latest list of choice
				return null;
			}

			@Override
			public String convertToString(T value, Locale locale)
			{
				return renderer.getText(value);
			}
		};
	}
}
