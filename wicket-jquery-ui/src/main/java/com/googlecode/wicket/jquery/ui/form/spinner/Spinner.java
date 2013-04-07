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

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;

import com.googlecode.wicket.jquery.core.IJQueryCultureWidget;
import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.core.Options;

/**
 * Provides a jQuery spinner based on a {@link TextField}
 *
 * @param <T> the type of the model object
 * @author Sebastien Briquet - sebfz1
 * @since 1.5.0
 */
public class Spinner<T extends Number> extends TextField<T> implements IJQueryCultureWidget
{
	private static final long serialVersionUID = 1L;

	private Options options;

	/**
	 * Constructor
	 * @param id the markup id
	 */
	public Spinner(final String id)
	{
		this(id, new Options());
	}

	/**
	 * Constructor
	 * @param id the markup id
	 * @param options {@link Options}
	 */
	public Spinner(String id, Options options)
	{
		this(id, options, null);
	}

	/**
	 * Constructor
	 * @param id the markup id
	 * @param type type for field validation
	 */
	public Spinner(final String id, final Class<T> type)
	{
		this(id, new Options(), type);
	}

	/**
	 * Constructor
	 * @param id the markup id
	 * @param options {@link Options}
	 * @param type Type for field validation
	 */
	public Spinner(final String id, Options options, final Class<T> type)
	{
		super(id, type);

		this.options = options;
	}

	/**
	 * Constructor
	 * @param id the markup id
	 * @param model the {@link IModel}
	 */
	public Spinner(final String id, final IModel<T> model)
	{
		this(id, model, new Options(), null);
	}

	/**
	 * Constructor
	 * @param id the markup id
	 * @param model the {@link IModel}
	 * @param options {@link Options}
	 */
	public Spinner(String id, final IModel<T> model, Options options)
	{
		this(id, model, options, null);
	}

	/**
	 * Constructor
	 * @param id the markup id
	 * @param model the {@link IModel}
	 * @param type type for field validation
	 */
	public Spinner(final String id, final IModel<T> model, final Class<T> type)
	{
		this(id, model, new Options(), type);
	}

	/**
	 * Constructor
	 * @param id the markup id
	 * @param model the {@link IModel}
	 * @param options {@link Options}
	 * @param type Type for field validation
	 */
	public Spinner(final String id, final IModel<T> model, Options options, final Class<T> type)
	{
		super(id, model, type);

		this.options = options;
	}

	// Events //
	@Override
	protected void onInitialize()
	{
		super.onInitialize();

		this.add(JQueryWidget.newWidgetBehavior(this)); //cannot be in ctor as the markupId may be set manually afterward
	}

	@Override
	protected void onConfigure()
	{
		super.onConfigure();

		this.setDisabled(!this.isEnabled());
	}

	/**
	 * Called immediately after the onConfigure method in a behavior. Since this is before the rendering
	 * cycle has begun, the behavior can modify the configuration of the component (i.e. {@link Options})
	 *
	 * @param behavior the {@link JQueryBehavior}
	 */
	protected void onConfigure(JQueryBehavior behavior)
	{
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
	public Spinner<T> setCulture(final String culture)
	{
		this.options.set("culture", Options.asString(culture));

		return this;
	}


	@Override
	public String getCulture()
	{
		return (String) this.options.get("culture");
	}

	/**
	 * Disables the spinner, if set to true.
	 *
	 * @param disabled whether the spinner is (visually) disabled
	 * @return this, for chaining
	 */
	private Spinner<T> setDisabled(final boolean disabled)
	{
		this.options.set("disabled", disabled);

		return this;
	}

	/**
	 * Sets the min.
	 *
	 * @param min the min
	 * @return this, for chaining
	 */
	public Spinner<T> setMin(final Number min)
	{
		this.options.set("min", min);

		return this;
	}

	/**
	 * Sets the min.<br/>
	 * If Globalize is included, the min option can be passed as a string which will be parsed based on the numberFormat and culture options; otherwise it will fall back to the native parseFloat() method.<br/>
	 * <b>More:</b> https://github.com/jquery/globalize
	 *
	 * @param min the min
	 * @return this, for chaining
	 */
	public Spinner<T> setMin(final String min)
	{
		this.options.set("min", Options.asString(min));

		return this;
	}

	/**
	 * Sets the max.
	 *
	 * @param max the max
	 * @return this, for chaining
	 */
	public Spinner<T> setMax(final Number max)
	{
		this.options.set("max", max);

		return this;
	}

	/**
	 * Sets the max.<br/>
	 * If Globalize is included, the max option canbe passed as a string which will be parsed based on the numberFormat and culture options; otherwise it will fall back to the native parseFloat() method.<br/>
	 * <b>More:</b> https://github.com/jquery/globalize
	 *
	 * @param max the max
	 * @return this, for chaining
	 */
	public Spinner<T> setMax(final String max)
	{
		this.options.set("max", Options.asString(max));

		return this;
	}

	//Not activated for now, because of currency issue in Wicket (space before the currency symbol, WICKET-4988) and an issue in Java (space as thousand separator, in fr_FR for instance).
//	/**
//	 * Format of numbers passed to Globalize, if available. Most common are "n" for a decimal number and "C" for a currency value. Also see the culture option.<br/>
//	 * <b>More: </b> https://github.com/jquery/globalize
//	 * <b>More: </b> http://api.jqueryui.com/spinner/#option-culture
//	 *
//	 * @param format the number format
//	 * @return this, for chaining
//	 */
//	public Spinner<T> setNumberFormat(final String format)
//	{
//		this.options.set("numberFormat", Options.asString(format));
//
//		return this;
//	}

	/**
	 * Sets the number of steps to take when paging via the pageUp/pageDown methods.
	 *
	 * @param steps the number of steps. Default is 10
	 * @return this, for chaining
	 */
	public Spinner<T> setPage(final Number steps)
	{
		this.options.set("page", steps);

		return this;
	}

	/**
	 * Sets the size of the step to take when spinning via buttons or via the stepUp()/stepDown() methods. The element's step attribute is used if it exists and the option is not explicitly set.
	 *
	 * @param size the size of the step. Default is 1
	 * @return this, for chaining
	 */
	public Spinner<T> setStep(final Number size)
	{
		this.options.set("step", size);

		return this;
	}


	// IJQueryWidget //
	@Override
	public SpinnerBehavior newWidgetBehavior(String selector)
	{
		return new SpinnerBehavior(selector, this.options) {

			private static final long serialVersionUID = 1L;

			@Override
			public void onConfigure(Component component)
			{
				Spinner.this.onConfigure(this);
			}
		};
	}


	/**
	 * TODO: javadoc
	 */
	public static class SpinnerBehavior extends JQueryBehavior
	{
		private static final long serialVersionUID = 1L;
		private static final String METHOD = "spinner";

		public SpinnerBehavior(String selector)
		{
			super(selector, METHOD);
		}

		public SpinnerBehavior(String selector, Options options)
		{
			super(selector, METHOD, options);
		}
	}
}
