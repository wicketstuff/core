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
package com.googlecode.wicket.jquery.ui.form.spinner;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.convert.ConversionException;
import org.apache.wicket.util.convert.IConverter;

import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.ui.JQueryUIBehavior;

/**
 * Provides an ajax jQuery spinner based on a {@link TextField}<br>
 * The {@code type} ctor arg is mandatory due to the {@link #convertValue(String)} method
 *
 * @param <T> the type of the model object
 * @author Sebastien Briquet - sebfz1
 * @since 1.5.0
 */
public class AjaxSpinner<T extends Number> extends Spinner<T> implements ISpinnerListener
{
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param type type for field validation
	 */
	public AjaxSpinner(final String id, final Class<T> type)
	{
		super(id, type);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param options the {@link Options}
	 * @param type Type for field validation
	 */
	public AjaxSpinner(final String id, Options options, final Class<T> type)
	{
		super(id, options, type);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param model the {@link IModel}
	 * @param type type for field validation
	 */
	public AjaxSpinner(final String id, final IModel<T> model, final Class<T> type)
	{
		super(id, model, type);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param model the {@link IModel}
	 * @param options the {@link Options}
	 * @param type Type for field validation
	 */
	public AjaxSpinner(final String id, final IModel<T> model, Options options, final Class<T> type)
	{
		super(id, model, options, type);
	}

	// Properties //

	@Override
	public boolean isOnSpinEventEnabled()
	{
		return true;
	}

	@Override
	public boolean isOnStopEventEnabled()
	{
		return false;
	}

	// Methods //

	/**
	 * Converts the supplied value using the type converter
	 * 
	 * @param value
	 */
	public T convertValue(String value)
	{
		final IConverter<T> converter = this.getConverter(this.getType());

		try
		{
			return converter.convertToObject(value, this.getLocale());
		}
		catch (ConversionException e)
		{
			this.error(this.newValidationError(e));
		}

		return null;
	}

	// Events //

	/**
	 * {@inheritDoc}<br>
	 * <b>Warning:</b> {@code onSpin} converts the input but does not process it (no validation, no model update)
	 */
	@Override
	public final void onSpin(AjaxRequestTarget target, String value)
	{
		this.onSpin(target, this.convertValue(value));
	}

	public void onSpin(AjaxRequestTarget target, T value)
	{
		// noop
	}

	@Override
	public void onStop(AjaxRequestTarget target)
	{
		// noop
	}

	// IJQueryWidget //

	@Override
	public JQueryUIBehavior newWidgetBehavior(String selector)
	{
		return new SpinnerBehavior(selector, this, this.options);
	}
}
