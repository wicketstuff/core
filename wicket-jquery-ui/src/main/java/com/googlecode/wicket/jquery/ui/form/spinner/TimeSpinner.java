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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.wicket.extensions.markup.html.form.DateTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.lang.Args;

import com.googlecode.wicket.jquery.core.IJQueryCultureWidget;
import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.core.utils.DateUtils;
import com.googlecode.wicket.jquery.core.utils.LocaleUtils;
import com.googlecode.wicket.jquery.ui.JQueryUIBehavior;

/**
 * Provides an time jQuery spinner based on a {@link TextField}
 *
 * @author Sebastien Briquet - sebfz1
 * @since 6.22.0
 * @since 7.1.2
 */
public class TimeSpinner extends DateTextField implements IJQueryCultureWidget
{
	private static final long serialVersionUID = 1L;

	protected final Options options;

	/**
	 * Constructor
	 * 
	 * @param id the markup id
	 */
	public TimeSpinner(String id)
	{
		this(id, DateUtils.TIME_PATTERN, new Options());
	}

	/**
	 * Constructor
	 * 
	 * @param id the markup id
	 * @param options the {@link Options}
	 */
	public TimeSpinner(String id, Options options)
	{
		this(id, DateUtils.TIME_PATTERN, options);
	}

	/**
	 * Constructor
	 * 
	 * @param id the markup id
	 * @param pattern a {@code SimpleDateFormat} pattern
	 */
	public TimeSpinner(String id, String pattern)
	{
		this(id, pattern, new Options());
	}

	/**
	 * Main constructor
	 * 
	 * @param id the markup id
	 * @param pattern a {@code SimpleDateFormat} pattern
	 * @param options the {@link Options}
	 */
	public TimeSpinner(String id, String pattern, Options options)
	{
		super(id, pattern);

		this.options = Args.notNull(options, "options");
	}

	/**
	 * Constructor, which use {@link Locale} and Query UI Globalization
	 * 
	 * @param id the markup id
	 * @param locale the {@link Locale}
	 */
	public TimeSpinner(String id, Locale locale)
	{
		this(id, locale, new Options());
	}

	/**
	 * Constructor, which use {@link Locale} and Query UI Globalization
	 * 
	 * @param id the markup id
	 * @param locale the {@link Locale}
	 * @param options the {@link Options}
	 */
	public TimeSpinner(String id, Locale locale, Options options)
	{
		this(id, LocaleUtils.getLocaleTimePattern(locale, DateUtils.TIME_PATTERN), options.set("culture", Options.asString(LocaleUtils.getLangageCode(locale))));
	}

	/**
	 * Constructor
	 * 
	 * @param id the markup id
	 * @param model the {@link IModel}
	 */
	public TimeSpinner(String id, IModel<Date> model)
	{
		this(id, model, DateUtils.TIME_PATTERN, new Options());
	}

	/**
	 * Constructor
	 * 
	 * @param id the markup id
	 * @param model the {@link IModel}
	 * @param options the {@link Options}
	 */
	public TimeSpinner(String id, IModel<Date> model, Options options)
	{
		this(id, model, DateUtils.TIME_PATTERN, options);
	}

	/**
	 * Constructor
	 * 
	 * @param id the markup id
	 * @param model the {@link IModel}
	 * @param pattern a {@code SimpleDateFormat} pattern
	 */
	public TimeSpinner(String id, IModel<Date> model, String pattern)
	{
		this(id, model, pattern, new Options());
	}

	/**
	 * Main constructor
	 * 
	 * @param id the markup id
	 * @param model the {@link IModel}
	 * @param pattern a {@code SimpleDateFormat} pattern.
	 * @param options the {@link Options}
	 */
	public TimeSpinner(String id, IModel<Date> model, String pattern, Options options)
	{
		super(id, model, pattern);

		this.options = Args.notNull(options, "options");
	}

	/**
	 * Constructor, which use {@link Locale} and Query UI Globalization
	 * 
	 * @param id the markup id
	 * @param model the {@link IModel}
	 * @param locale the {@link Locale}
	 */
	public TimeSpinner(String id, IModel<Date> model, Locale locale)
	{
		this(id, model, locale, new Options());
	}

	/**
	 * Constructor, which use {@link Locale} and jQuery UI Globalization
	 * 
	 * @param id the markup id
	 * @param model the {@link IModel}
	 * @param locale the {@link Locale}
	 * @param options the {@link Options}
	 */
	public TimeSpinner(String id, IModel<Date> model, Locale locale, Options options)
	{
		this(id, model, LocaleUtils.getLocaleTimePattern(locale, DateUtils.TIME_PATTERN), options.set("culture", Options.asString(LocaleUtils.getLangageCode(locale))));
	}

	// Events //

	@Override
	protected void onInitialize()
	{
		super.onInitialize();

		this.add(JQueryWidget.newWidgetBehavior(this)); // cannot be in ctor as the markupId may be set manually afterward
	}

	@Override
	protected void onConfigure()
	{
		super.onConfigure();

		this.setDisabled(!this.isEnabledInHierarchy());
	}

	@Override
	public void onConfigure(JQueryBehavior behavior)
	{
		// noop
	}

	@Override
	public void onBeforeRender(JQueryBehavior behavior)
	{
		// noop
	}

	// Properties //

	/**
	 * Marked as final. It is - probably - not consistent to have a pattern different from the display
	 */
	@Override
	public final String getTextFormat()
	{
		return super.getTextFormat();
	}

	/**
	 * Gets a string representation given the time pattern in use.
	 *
	 * @return the model object as string
	 */
	public String getModelObjectAsString()
	{
		Date date = this.getModelObject();

		if (date != null)
		{
			return new SimpleDateFormat(this.getTextFormat()).format(date);
		}

		return "";
	}

	// Options //

	/**
	 * Sets the culture to use for parsing and formatting the value.<br/>
	 * <b>More:</b> https://github.com/jquery/globalize
	 *
	 * @param culture the culture to be used
	 * @return this, for chaining
	 *
	 */
	@Override
	public TimeSpinner setCulture(final String culture)
	{
		this.options.set("culture", Options.asString(culture));

		return this;
	}

	@Override
	public String getCulture()
	{
		return this.options.get("culture");
	}

	/**
	 * Disables the spinner, if set to true.
	 *
	 * @param disabled whether the spinner is (visually) disabled
	 * @return this, for chaining
	 */
	private TimeSpinner setDisabled(final boolean disabled)
	{
		this.options.set("disabled", disabled);

		return this;
	}

	// IJQueryWidget //

	@Override
	public JQueryUIBehavior newWidgetBehavior(String selector)
	{
		return new TimeSpinnerBehavior(selector, new SpinnerAdapter(), this.options);
	}
}
