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
package com.googlecode.wicket.jquery.ui.form.slider;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.FormComponent;

import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.core.JQueryEvent;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.core.ajax.IJQueryAjaxAware;
import com.googlecode.wicket.jquery.core.ajax.JQueryAjaxBehavior;
import com.googlecode.wicket.jquery.core.ajax.JQueryAjaxPostBehavior;
import com.googlecode.wicket.jquery.ui.form.slider.AbstractSlider.SliderBehavior;

/**
 * Provides a jQuery ajax-slider {@link JQueryBehavior}<br>
 * {@link #onAjax(AjaxRequestTarget, JQueryEvent)} is delegated to AjaxSlider (need to validate AjaxSlider as a {@link FormComponent})
 *
 * @author Sebastien Briquet - sebfz1
 *
 */
public abstract class AjaxSliderBehavior extends SliderBehavior implements IJQueryAjaxAware
{
	private static final long serialVersionUID = 1L;

	private JQueryAjaxBehavior onChangeAjaxBehavior;

	public AjaxSliderBehavior(String selector)
	{
		super(selector);
	}

	public AjaxSliderBehavior(String selector, Options options)
	{
		super(selector, options);
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
		super.onConfigure(component);

		this.setOption("change", this.onChangeAjaxBehavior.getCallbackFunction());
	}

	// Factories //

	/**
	 * Gets a new {@link JQueryAjaxPostBehavior} that will be wired to the 'change' event
	 *
	 * @param source the {@link IJQueryAjaxAware}
	 * @return a new {@code OnChangeAjaxBehavior} by default
	 */
	protected abstract JQueryAjaxPostBehavior newOnChangeAjaxBehavior(IJQueryAjaxAware source);
}
