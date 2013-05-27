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
package com.googlecode.wicket.jquery.ui.kendo.datetime;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.wicket.Component;
import org.apache.wicket.extensions.markup.html.form.DateTextField;
import org.apache.wicket.model.IModel;
import com.googlecode.wicket.jquery.core.IJQueryWidget;
import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.core.utils.LocaleUtils;
import com.googlecode.wicket.jquery.ui.kendo.KendoAbstractBehavior;
import com.googlecode.wicket.jquery.ui.kendo.utils.KendoDateTimeUtils;

/**
 * Provides a Kendo UI date-picker based on a {@link DateTextField}<br/>
 * The code is quite identical to the jQuery DatePicker
 *
 * @author Sebastien Briquet - sebfz1
 */
public class DatePicker extends DateTextField implements IJQueryWidget
{
	private static final long serialVersionUID = 1L;
	private static final String METHOD = "kendoDatePicker";

	protected static final String DEFAULT_PATTERN = "MM/dd/yyyy"; //default java date pattern

	private Options options;

	/**
	 * Constructor
	 * @param id the markup id
	 */
	public DatePicker(String id)
	{
		this(id, DEFAULT_PATTERN, new Options());
	}

	/**
	 * Constructor
	 * @param id the markup id
	 * @param options the {@link Options}
	 */
	public DatePicker(String id, Options options)
	{
		this(id, DEFAULT_PATTERN, options);
	}

	/**
	 * Constructor
	 * @param id the markup id
	 * @param pattern a <code>SimpleDateFormat</code> pattern
	 */
	public DatePicker(String id, String pattern)
	{
		this(id, pattern, new Options());
	}

	/**
	 * Main constructor
	 * @param id the markup id
	 * @param pattern a <code>SimpleDateFormat</code> pattern
	 * @param options the {@link Options}
	 */
	public DatePicker(String id, String pattern, Options options)
	{
		super(id, pattern);

		this.options = options;
	}

	/**
	 * Constructor, which use {@link Locale} and Kengo UI Globalization
	 * @param id the markup id
	 * @param locale the {@link Locale}
	 */
	public DatePicker(String id, Locale locale)
	{
		this(id, locale, new Options());
	}

	/**
	 * Constructor, which use {@link Locale} and Kengo UI Globalization
	 * @param id the markup id
	 * @param locale the {@link Locale}
	 * @param options the {@link Options}
	 */
	public DatePicker(String id, Locale locale, Options options)
	{
		this(id, LocaleUtils.getLocaleDatePattern(locale, DEFAULT_PATTERN), options.set("culture", Options.asString(LocaleUtils.getLangageCode(locale))));
	}


	/**
	 * Constructor
	 * @param id the markup id
	 * @param model the {@link IModel}
	 */
	public DatePicker(String id, IModel<Date> model)
	{
		this(id, model, DEFAULT_PATTERN, new Options());
	}

	/**
	 * Constructor
	 * @param id the markup id
	 * @param model the {@link IModel}
	 * @param options {@link Options}
	 */
	public DatePicker(String id, IModel<Date> model, Options options)
	{
		this(id, model, DEFAULT_PATTERN, options);
	}

	/**
	 * Constructor
	 * @param id the markup id
	 * @param model the {@link IModel}
	 * @param pattern a <code>SimpleDateFormat</code> pattern
	 */
	public DatePicker(String id, IModel<Date> model, String pattern)
	{
		this(id, model, pattern, new Options());
	}

	/**
	 * Main constructor
	 * @param id the markup id
	 * @param model the {@link IModel}
	 * @param pattern a <code>SimpleDateFormat</code> pattern
	 * @param options {@link Options}
	 */
	public DatePicker(String id, IModel<Date> model, String pattern, Options options)
	{
		super(id, model, pattern);

		this.options = options;
	}

	/**
	 * Constructor, which use {@link Locale} and Kengo UI Globalization
	 * @param id the markup id
	 * @param model the {@link IModel}
	 * @param locale the {@link Locale}
	 */
	public DatePicker(String id, IModel<Date> model, Locale locale)
	{
		this(id, model, locale, new Options());
	}

	/**
	 * Constructor, which use {@link Locale} and Kengo UI Globalization
	 * @param id the markup id
	 * @param model the {@link IModel}
	 * @param locale the {@link Locale}
	 * @param options the {@link Options}
	 */
	public DatePicker(String id, IModel<Date> model, Locale locale, Options options)
	{
		this(id, model, LocaleUtils.getLocaleDatePattern(locale, DEFAULT_PATTERN), options.set("culture", Options.asString(LocaleUtils.getLangageCode(locale))));
	}

	// Getters //
	/**
	 * Marked as final.
	 * It is - probably - not consistent to have a pattern different from the display
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

	// Events //
	@Override
	protected void onInitialize()
	{
		super.onInitialize();

		this.add(JQueryWidget.newWidgetBehavior(this)); //cannot be in ctor as the markupId may be set manually afterward
	}


	/**
	 * Called immediately after the onConfigure method in a behavior. Since this is before the rendering
	 * cycle has begun, the behavior can modify the configuration of the component (i.e. {@link Options})
	 *
	 * @param behavior the {@link JQueryBehavior}
	 */
	protected void onConfigure(JQueryBehavior behavior)
	{
		if (this.options.get("format") == null)
		{
			this.options.set("format", Options.asString(KendoDateTimeUtils.toPattern(this.getTextFormat())));
		}
	}

	// IJQueryWidget //
	@Override
	public JQueryBehavior newWidgetBehavior(String selector)
	{
		return new KendoAbstractBehavior(selector, METHOD, this.options) {

			private static final long serialVersionUID = 1L;

			@Override
			public void onConfigure(Component component)
			{
				DatePicker.this.onConfigure(this);
			}
		};
	}
}
