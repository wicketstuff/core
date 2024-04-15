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

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.FormComponentPanel;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.model.IModel;
import org.apache.wicket.validation.validator.RangeValidator;

import com.googlecode.wicket.jquery.core.IJQueryWidget;
import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.ui.JQueryUIBehavior;

/**
 * Base class for implementing jQuery slider(s)
 *
 * @author Sebastien Briquet - sebfz1
 *
 * @param <T> the model object type
 */
public abstract class AbstractSlider<T> extends FormComponentPanel<T> implements IJQueryWidget
{
	private static final long serialVersionUID = 1L;

	protected Options options;

	/** the div on which the slider behavior will be applied to */
	protected Label label;
	protected String labelId = null;

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 */
	public AbstractSlider(String id)
	{
		super(id);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param model the {@link IModel}
	 */
	public AbstractSlider(String id, IModel<T> model)
	{
		super(id, model);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param model the {@link IModel}
	 * @param label {@link Label} on which the current slide value will be displayed
	 */
	public AbstractSlider(String id, IModel<T> model, Label label)
	{
		super(id, model);

		label.setDefaultModel(model);
		label.setOutputMarkupId(true);
		this.setLabelId(label.getMarkupId());
	}

	/**
	 * Initialization, should be called *manually* by overridden classes, in their constructors.<br>
	 * <b>Warning:</b> Options should not be set before this method has been called.
	 */
	void initialize()
	{
		this.options = new Options();

		this.label = new Label("slider", "");
		this.add(this.label);
	}

	// Methods //

	@Override
	public abstract void convertInput();

	/**
	 * Adds a {@link RangeValidator} so slider value(s) are valid only if comprised between minimum and maximum.<br>
	 * <br>
	 * <b>Warning:</b> it does not define the minimum and maximum values the slider can slide on, but only valid ones.<br>
	 * To define the minimum and maximum values the slider can slide on, use {@link #setMin(Integer)} and {@link #setMax(Integer)}<br>
	 * <br>
	 * <b>Dev note:</b> this method is marked as abstract to make sure the developer that will add the validator to the right input(s) in {@link #onInitialize()}
	 *
	 * @param validator the {@link RangeValidator}
	 * @return this
	 */
	public abstract AbstractSlider<T> setRangeValidator(RangeValidator<Integer> validator);

	// Properties //

	/**
	 * Sets the label's markupId on which the current slide value will be displayed.<br>
	 * <b>Warning:</b> It cannot be set/changed once the Component has been initialized.
	 *
	 * @param markupId the label's markupId
	 * @return this
	 */
	public final AbstractSlider<T> setLabelId(String markupId)
	{
		this.labelId = markupId;
		return this;
	}

	// Events //
	@Override
	protected void onInitialize()
	{
		super.onInitialize();

		this.add(this.newInputFragment("model")); // cannot be in ctor as the model may not have been initialized before.
		this.add(JQueryWidget.newWidgetBehavior(this, this.label)); // cannot be in ctor as the markupId may be set manually afterward
	}

	@Override
	public void onConfigure(JQueryBehavior behavior)
	{
	}

	@Override
	public void onBeforeRender(JQueryBehavior behavior)
	{
	}

	// IJQueryWidget //

	@Override
	public JQueryBehavior newWidgetBehavior(String selector)
	{
		return new SliderBehavior(selector, this.options);
	}

	// Factory //

	/**
	 * Gets a new {@link Fragment} containing the input<br>
	 * Overridden methods should provide a {@link Fragment} containing input(s) when no input(s) has been specified in implementation constructors.
	 *
	 * @param id the markup id
	 * @return the empty-fragment by default
	 */
	protected Fragment newInputFragment(String id)
	{
		return new Fragment(id, "empty-fragment", this);
	}

	// Options //

	/**
	 * Sets the min value
	 *
	 * @param min the min value
	 * @return this, for chaining
	 */
	public AbstractSlider<T> setMin(Integer min)
	{
		this.options.set("min", min);

		return this;
	}

	/**
	 * Sets the max value
	 *
	 * @param max the max value
	 * @return this, for chaining
	 */
	public AbstractSlider<T> setMax(Integer max)
	{
		this.options.set("max", max);

		return this;
	}

	/**
	 * Sets the step value
	 *
	 * @param step the step value
	 * @return this, for chaining
	 */
	public AbstractSlider<T> setStep(Integer step)
	{
		this.options.set("step", step);

		return this;
	}

	/**
	 * Sets the {@link Orientation}
	 *
	 * @param orientation the {@code Orientation}
	 * @return this, for chaining
	 */
	public AbstractSlider<T> setOrientation(Orientation orientation)
	{
		this.options.set("orientation", orientation);

		return this;
	}

	/**
	 * Provides a jQuery slider {@link JQueryBehavior}
	 */
	public static class SliderBehavior extends JQueryUIBehavior
	{
		private static final long serialVersionUID = 1L;
		public static final String METHOD = "slider";

		public SliderBehavior(String selector)
		{
			super(selector, METHOD);
		}

		public SliderBehavior(String selector, Options options)
		{
			super(selector, METHOD, options);
		}
	}
}
