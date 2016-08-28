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
package com.googlecode.wicket.kendo.ui.form.datetime.local;

import java.util.Locale;

import org.apache.wicket.markup.html.form.AbstractTextComponent.ITextFormatProvider;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.convert.IConverter;

import com.googlecode.wicket.jquery.core.IJQueryWidget;
import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.core.utils.LocaleUtils;
import com.googlecode.wicket.kendo.ui.KendoUIBehavior;
import com.googlecode.wicket.kendo.ui.utils.KendoDateTimeUtils;

/**
 * Base class for {@link DatePicker} and {@link TimePicker}
 *
 * @author Sebastien Briquet - sebfz1
 *
 * @param <T> the model object type
 */
public abstract class LocalTextField<T> extends TextField<T> implements ITextFormatProvider, IJQueryWidget
{
	private static final long serialVersionUID = 1L;

	private final Locale locale;

	/** the date pattern of the TextField */
	private final String pattern;

	/** the converter for the TextField */
	private final IConverter<T> converter;

	protected final Options options;

	/**
	 * Main constructor
	 *
	 * @param id the markup id
	 * @param model the {@link IModel}
	 * @param pattern a {@code SimpleDateFormat} pattern
	 * @param options the {@link Options}
	 * @param type the class type
	 * @param converter the {@link IConverter}
	 */
	public LocalTextField(String id, IModel<T> model, Locale locale, String pattern, Options options, Class<T> type, IConverter<T> converter)
	{
		super(id, model, type);

		this.locale = locale;
		this.pattern = pattern;
		this.options = options;
		this.converter = converter;
	}

	// Properties //

	@Override
	public Locale getLocale()
	{
		if (this.locale != null)
		{
			return this.locale;
		}

		return super.getLocale();
	}

	/**
	 * Returns the date pattern.
	 *
	 * Marked as final. It is - probably - not consistent to have a pattern different from the display
	 *
	 * @see org.apache.wicket.markup.html.form.AbstractTextComponent.ITextFormatProvider#getTextFormat()
	 */
	@Override
	public final String getTextFormat()
	{
		return this.pattern;
	}

	/**
	 * Returns the default converter if created without pattern; otherwise it returns a pattern-specific converter.
	 *
	 * @param type The type for which the convertor should work
	 * @return A pattern-specific converter
	 * @see org.apache.wicket.markup.html.form.TextField
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <C> IConverter<C> getConverter(final Class<C> type)
	{
		if (this.getType().isAssignableFrom(type))
		{
			return (IConverter<C>) this.converter;
		}
		else
		{
			return super.getConverter(type);
		}
	}

	/**
	 * Gets a string representation given the time pattern in use.
	 *
	 * @return the model object as string
	 */
	public String getModelObjectAsString()
	{
		T date = this.getModelObject();

		if (date != null)
		{
			return this.converter.convertToString(date, this.getLocale());
		}

		return "";
	}

	/**
	 * Gets the widget's method
	 * 
	 * @return the widget's method
	 */
	protected abstract String getMethod();

	// Events //

	@Override
	protected void onInitialize()
	{
		super.onInitialize();

		this.add(JQueryWidget.newWidgetBehavior(this)); // cannot be in ctor as the markupId may be set manually afterward
	}

	@Override
	public void onConfigure(JQueryBehavior behavior)
	{
		if (behavior.getOption("culture") == null)
		{
			behavior.setOption("culture", Options.asString(LocaleUtils.getLangageCode(this.getLocale())));
		}

		if (behavior.getOption("format") == null)
		{
			behavior.setOption("format", Options.asString(KendoDateTimeUtils.toPattern(this.getTextFormat())));
		}
	}

	@Override
	public void onBeforeRender(JQueryBehavior behavior)
	{
		// noop
	}

	// IJQueryWidget //

	@Override
	public JQueryBehavior newWidgetBehavior(String selector)
	{
		return new KendoUIBehavior(selector, this.getMethod(), this.options);
	}
}
