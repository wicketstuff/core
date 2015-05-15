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
package com.googlecode.wicket.kendo.ui.form;

import org.apache.wicket.model.IModel;
import org.apache.wicket.util.lang.Args;

import com.googlecode.wicket.jquery.core.IJQueryWidget;
import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.kendo.ui.KendoUIBehavior;

/**
 * Provides a Kendo UI NumericTextBox on the built-in {@link org.apache.wicket.markup.html.form.NumberTextField}
 *
 * @param <T> the model object type
 * @author Sebastien Briquet - sebfz1
 *
 */
public class NumberTextField<T extends Number & Comparable<T>> extends org.apache.wicket.markup.html.form.NumberTextField<T> implements IJQueryWidget
{
	private static final long serialVersionUID = 1L;
	public static final String METHOD = "kendoNumericTextBox";

	private final Options options;

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 */
	public NumberTextField(String id)
	{
		this(id, new Options());
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param options the {@link Options}
	 */
	public NumberTextField(String id, Options options)
	{
		super(id);

		this.options = Args.notNull(options, "options");
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param type the type to use when updating the model for this text field
	 */
	public NumberTextField(String id, Class<T> type)
	{
		this(id, type, new Options());
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param type the type to use when updating the model for this text field
	 * @param options the {@link Options}
	 */
	public NumberTextField(String id, Class<T> type, Options options)
	{
		super(id, type);

		this.options = Args.notNull(options, "options");
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param model the {@link IModel}
	 */
	public NumberTextField(String id, IModel<T> model)
	{
		this(id, model, new Options());
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param model the {@link IModel}
	 * @param options the {@link Options}
	 */
	public NumberTextField(String id, IModel<T> model, Options options)
	{
		super(id, model);

		this.options = Args.notNull(options, "options");
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param model the {@link IModel}
	 * @param type the type to use when updating the model for this text field
	 */
	public NumberTextField(String id, IModel<T> model, Class<T> type)
	{
		this(id, model, type, new Options());
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param model the {@link IModel}
	 * @param type the type to use when updating the model for this text field
	 * @param options the {@link Options}
	 */
	public NumberTextField(String id, IModel<T> model, Class<T> type, Options options)
	{
		super(id, model, type);

		this.options = Args.notNull(options, "options");
	}

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
		// noop
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
		return new KendoUIBehavior(selector, NumberTextField.METHOD, this.options);
	}
}
