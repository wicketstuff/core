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

import org.apache.wicket.Component;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.model.IModel;
import org.threeten.bp.LocalTime;

import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.core.JQueryEvent;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.core.ajax.IJQueryAjaxAware;
import com.googlecode.wicket.jquery.core.ajax.JQueryAjaxBehavior;
import com.googlecode.wicket.jquery.core.ajax.JQueryAjaxPostBehavior;
import com.googlecode.wicket.jquery.core.event.IValueChangedListener;
import com.googlecode.wicket.kendo.ui.KendoUIBehavior;
import com.googlecode.wicket.kendo.ui.ajax.OnChangeAjaxBehavior;

/**
 * Provides a Kendo UI time-picker based on a {@link TimePicker}<br/>
 * This ajax version will post the {@link Component}, using a {@link JQueryAjaxPostBehavior}, when the 'change' javascript method is called.
 *
 * @author Sebastien Briquet - sebfz1
 */
public class AjaxTimePicker extends TimePicker implements IJQueryAjaxAware, IValueChangedListener
{
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 */
	public AjaxTimePicker(String id)
	{
		super(id);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param options the {@link Options}
	 */
	public AjaxTimePicker(String id, Options options)
	{
		super(id, options);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param pattern a {@code SimpleDateFormat} pattern
	 */
	public AjaxTimePicker(String id, String pattern)
	{
		super(id, pattern);
	}

	/**
	 * Main constructor
	 *
	 * @param id the markup id
	 * @param pattern a {@code SimpleDateFormat} pattern
	 * @param options the {@link Options}
	 */
	public AjaxTimePicker(String id, String pattern, Options options)
	{
		super(id, pattern, options);
	}

	/**
	 * Constructor, which use {@link Locale} and Kendo UI Globalization
	 *
	 * @param id the markup id
	 * @param locale the {@link Locale}
	 */
	public AjaxTimePicker(String id, Locale locale)
	{
		super(id, locale);
	}

	/**
	 * Constructor, which use {@link Locale} and Kendo UI Globalization
	 *
	 * @param id the markup id
	 * @param locale the {@link Locale}
	 * @param options the {@link Options}
	 */
	public AjaxTimePicker(String id, Locale locale, Options options)
	{
		super(id, locale, options);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param model the {@link IModel}
	 */
	public AjaxTimePicker(String id, IModel<LocalTime> model)
	{
		super(id, model);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param model the {@link IModel}
	 * @param options {@link Options}
	 */
	public AjaxTimePicker(String id, IModel<LocalTime> model, Options options)
	{
		super(id, model, options);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param model the {@link IModel}
	 * @param pattern a {@code SimpleDateFormat} pattern
	 */
	public AjaxTimePicker(String id, IModel<LocalTime> model, String pattern)
	{
		super(id, model, pattern);
	}

	/**
	 * Main constructor
	 *
	 * @param id the markup id
	 * @param model the {@link IModel}
	 * @param pattern a {@code SimpleDateFormat} pattern
	 * @param options {@link Options}
	 */
	public AjaxTimePicker(String id, IModel<LocalTime> model, String pattern, Options options)
	{
		super(id, model, pattern, options);
	}

	/**
	 * Constructor, which use {@link Locale} and Kendo UI Globalization
	 *
	 * @param id the markup id
	 * @param model the {@link IModel}
	 * @param locale the {@link Locale}
	 */
	public AjaxTimePicker(String id, IModel<LocalTime> model, Locale locale)
	{
		super(id, model, locale);
	}

	/**
	 * Constructor, which use {@link Locale} and Kendo UI Globalization
	 *
	 * @param id the markup id
	 * @param model the {@link IModel}
	 * @param locale the {@link Locale}
	 * @param options the {@link Options}
	 */
	public AjaxTimePicker(String id, IModel<LocalTime> model, Locale locale, Options options)
	{
		super(id, model, locale, options);
	}

	// Events //
	/**
	 * {@inheritDoc} <br/>
	 * <i>Not intended to be overridden</i>
	 */
	@Override
	public void onAjax(AjaxRequestTarget target, JQueryEvent event)
	{
		this.processInput();
		this.onValueChanged(target);
	}

	@Override
	public void onValueChanged(AjaxRequestTarget target)
	{
		// noop
	}

	// IJQueryWidget //

	@Override
	public JQueryBehavior newWidgetBehavior(String selector)
	{
		return new TimePickerBehavior(selector, this.options) {

			private static final long serialVersionUID = 1L;

			@Override
			public void onAjax(AjaxRequestTarget target, JQueryEvent event)
			{
				AjaxTimePicker.this.onAjax(target, event);
			}
		};
	}

	/**
	 * Provides a jQuery timepicker behavior
	 */
	protected abstract static class TimePickerBehavior extends KendoUIBehavior implements IJQueryAjaxAware
	{
		private static final long serialVersionUID = 1L;

		private JQueryAjaxBehavior onChangeAjaxBehavior = null;

		/**
		 * Constructor
		 *
		 * @param selector the html selector (ie: "#myId")
		 */
		public TimePickerBehavior(String selector)
		{
			this(selector, new Options());
		}

		/**
		 * Constructor
		 *
		 * @param selector the html selector (ie: "#myId")
		 * @param options the {@link Options}
		 */
		public TimePickerBehavior(String selector, Options options)
		{
			super(selector, TimePicker.METHOD, options);
		}

		// Methods //

		@Override
		public void bind(Component component)
		{
			super.bind(component);

			if (component instanceof FormComponent<?>)
			{
				this.onChangeAjaxBehavior = this.newOnChangeAjaxBehavior(this, (FormComponent<?>) component);
				component.add(this.onChangeAjaxBehavior);
			}
			else
			{
				throw new WicketRuntimeException(new IllegalArgumentException("'component' should be an intance of FormComponent"));
			}
		}

		// Events //

		@Override
		public void onConfigure(Component component)
		{
			super.onConfigure(component);

			if (this.onChangeAjaxBehavior != null)
			{
				this.setOption("change", this.onChangeAjaxBehavior.getCallbackFunction());
			}
		}

		// Factories //

		/**
		 * Gets a new {@link JQueryAjaxPostBehavior} that will be wired to the 'change' event
		 *
		 * @param source the {@link IJQueryAjaxAware}
		 * @param component the bound {@link Component}
		 * @return a new {@link OnChangeAjaxBehavior} by default
		 */
		protected JQueryAjaxPostBehavior newOnChangeAjaxBehavior(IJQueryAjaxAware source, FormComponent<?> component)
		{
			return new OnChangeAjaxBehavior(source, component);
		}
	}
}
