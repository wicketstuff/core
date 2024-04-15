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
package com.googlecode.wicket.kendo.ui.form.datetime;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.util.lang.Args;

import com.googlecode.wicket.jquery.core.JQueryEvent;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.core.ajax.IJQueryAjaxAware;
import com.googlecode.wicket.jquery.core.ajax.JQueryAjaxBehavior;
import com.googlecode.wicket.jquery.core.ajax.JQueryAjaxPostBehavior;
import com.googlecode.wicket.jquery.core.event.IValueChangedListener;
import com.googlecode.wicket.kendo.ui.KendoUIBehavior;
import com.googlecode.wicket.kendo.ui.ajax.OnChangeAjaxBehavior;
import com.googlecode.wicket.kendo.ui.ajax.OnChangeAjaxBehavior.ChangeEvent;

/**
 * Provides a {@value #METHOD} behavior
 * 
 * @author Sebastien Briquet - sebfz1
 */
public class TimePickerBehavior extends KendoUIBehavior implements IJQueryAjaxAware
{
	private static final long serialVersionUID = 1L;

	public static final String METHOD = "kendoTimePicker";

	private final IValueChangedListener listener;
	private JQueryAjaxBehavior onChangeAjaxBehavior = null;

	/**
	 * Constructor
	 *
	 * @param selector the html selector (ie: "#myId")
	 * @param listener the {@code IValueChangedListener}
	 */
	public TimePickerBehavior(String selector, IValueChangedListener listener)
	{
		this(selector, new Options(), listener);
	}

	/**
	 * Constructor
	 *
	 * @param selector the html selector (ie: "#myId")
	 * @param options the {@link Options}
	 * @param listener the {@code IValueChangedListener}
	 */
	public TimePickerBehavior(String selector, Options options, IValueChangedListener listener)
	{
		super(selector, METHOD, options);

		this.listener = Args.notNull(listener, "listener");
	}

	// Methods //

	@Override
	public void bind(Component component)
	{
		super.bind(component);

		this.onChangeAjaxBehavior = this.newOnChangeAjaxBehavior(this);
		component.add(this.onChangeAjaxBehavior);
	}

	// Events //

	@Override
	public void onConfigure(Component component)
	{
		if (this.onChangeAjaxBehavior != null)
		{
			this.setOption("change", this.onChangeAjaxBehavior.getCallbackFunction());
		}

		super.onConfigure(component);
	}

	@Override
	public void onAjax(AjaxRequestTarget target, JQueryEvent event)
	{
		if (event instanceof ChangeEvent)
		{
			this.listener.onValueChanged(target);
		}
	}

	// Factories //

	/**
	 * Gets a new {@link JQueryAjaxPostBehavior} that will be wired to the 'change' event
	 *
	 * @param source the {@link IJQueryAjaxAware}
	 * @return a new {@code OnChangeAjaxBehavior} by default
	 */
	protected JQueryAjaxPostBehavior newOnChangeAjaxBehavior(IJQueryAjaxAware source)
	{
		return new OnChangeAjaxBehavior(source);
	}
}