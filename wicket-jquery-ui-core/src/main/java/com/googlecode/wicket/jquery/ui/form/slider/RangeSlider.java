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
import org.apache.wicket.markup.html.form.AbstractTextComponent;
import org.apache.wicket.markup.html.form.FormComponentPanel;
import org.apache.wicket.markup.html.form.HiddenField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.util.string.Strings;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;
import org.apache.wicket.validation.validator.RangeValidator;

import com.googlecode.wicket.jquery.ui.JQueryAbstractBehavior;
import com.googlecode.wicket.jquery.ui.JQueryBehavior;

/**
 * Provides a jQuery range slider based on a {@link FormComponentPanel}
 *
 * @author Sebastien Briquet - sebfz1
 */
public class RangeSlider extends AbstractSlider<RangeValue>
{
	private static final long serialVersionUID = 1L;

	private IValidator<Integer> rangeValidator = null;
	protected AbstractTextComponent<Integer> lower; // will be accessed in AjaxRangeSlider
	protected AbstractTextComponent<Integer> upper; // will be accessed in AjaxRangeSlider

	/**
	 * Constructor
	 * @param id the markup id
	 */
	public RangeSlider(String id)
	{
		super(id);
		this.init();
	}

	/**
	 * Constructor
	 * @param id the markup id
	 * @param model the {@link IModel}
	 */
	public RangeSlider(String id, IModel<RangeValue> model)
	{
		super(id, model);
		this.init();
	}

	/**
	 * Constructor
	 * @param id the markup id
	 * @param model the {@link IModel}
	 * @param label {@link Label} on which the current slide value will be displayed
	 */
	public RangeSlider(String id, IModel<RangeValue> model, Label label)
	{
		super(id, model, label);
		this.init();
	}

	/**
	 * Constructor
	 * @param id the markup id
	 * @param model the {@link IModel}
	 * @param lower
	 * @param upper
	 */
	public RangeSlider(String id, IModel<RangeValue> model, TextField<Integer> lower, TextField<Integer> upper)
	{
		super(id, model);

		this.lower = lower;
		this.lower.setRequired(true); //prevent null model object in case of no value is supplied.
		this.lower.setOutputMarkupId(true);
		this.lower.add(this.newLowerBehavior());

		this.upper = upper;
		this.upper.setRequired(true); //prevent null model object in case of no value is supplied.
		this.upper.setOutputMarkupId(true);
		this.upper.add(this.newUpperBehavior());

		this.init();
	}

	/**
	 * Initialization
	 */
	@Override
	protected void init()
	{
		super.init();

		this.setRange(true);
	}

	@Override
 	protected void convertInput()
 	{
		Integer lower = this.lower.getConvertedInput();
		Integer upper = this.upper.getConvertedInput();

		if (lower != null && upper != null)
		{
			RangeValue value = new RangeValue(lower, upper);

			if (value.getLower() <= value.getUpper())
			{
				this.setConvertedInput(value);
			}
			else
			{
				ValidationError error = new ValidationError();
				error.addMessageKey("RangeSlider.ConversionError");
				error.setVariable("lower", value.getLower());
				error.setVariable("upper", value.getUpper());

				this.error(error);
			}
		}
 	}

	@Override
	@SuppressWarnings("unchecked")
	public <W extends AbstractSlider<RangeValue>> W setRangeValidator(RangeValidator<Integer> validator)
	{
		this.rangeValidator = validator;

		return (W) this;
	}

	/**
	 * Get the label pattern to be used to display the value. Should be overridden with care!<br/>
	 * <b>Note: </b> the pattern is a javascript string, where lower value is represented by "ui.values[0]", upper value by "ui.values[1]".<br/>
	 *
	 * @return default to '[' + ui.values[0] + ', ' +  ui.values[1] + ']'
	 */
	protected String getLabelPattern()
	{
		return "'[' + ui.values[0] + ', ' +  ui.values[1] + ']'";
	}

	// Events //
	@Override
	protected void onInitialize()
	{
		super.onInitialize();

		if (this.rangeValidator != null)
		{
			this.lower.add(this.rangeValidator); //let throw a NPE if no input is defined.
			this.upper.add(this.rangeValidator); //let throw a NPE if no input is defined.
		}
	}

	@Override
	protected void onConfigure(JQueryBehavior behavior)
	{
		super.onConfigure(behavior);

		StringBuilder statements = new StringBuilder();

		statements.append("jQuery('#").append(this.lower.getMarkupId()).append("').val(ui.values[0]); ");
		statements.append("jQuery('#").append(this.upper.getMarkupId()).append("').val(ui.values[1]); ");

		if (!Strings.isEmpty(super.labelId))
		{
			statements.append("jQuery('#").append(super.labelId).append("').text(").append(this.getLabelPattern()).append("); ");
		}

		behavior.setOption("slide", String.format("function(event, ui) { %s }", statements));
		behavior.setOption("values", this.getModelObject());
	}

	// Options //
	/**
	 * Sets the Range
	 * @param range
	 * @return {@link RangeSlider} (this)
	 */
	private RangeSlider setRange(Boolean range)
	{
		this.options.set("range", range);
		return this;
	}

	// Factories //
	@Override
	protected Fragment newInputFragment(String id)
	{
		Fragment fragment = null;

		// lower & upper TextFields have not been specified in ctor //
		if (this.lower == null || this.upper == null)
		{
			this.lower = new HiddenField<Integer>("lower", new PropertyModel<Integer>(this.getModelObject(), "lower"), Integer.class);
			this.upper = new HiddenField<Integer>("upper", new PropertyModel<Integer>(this.getModelObject(), "upper"), Integer.class);

			fragment = new Fragment(id, "range-fragment", this);
			fragment.add(this.lower.setOutputMarkupPlaceholderTag(true));
			fragment.add(this.upper.setOutputMarkupPlaceholderTag(true));
		}
		else
		{
			fragment = super.newInputFragment(id); //return empty fragment
		}

		return fragment;
	}

	/**
	 * Gets a new behavior that will handle the change event triggered on provided lower-input.<br/>
	 * The behavior is added to the input that has been provided in the constructor (means it is visible and to user can interact with)
	 * @return a {@link JQueryAbstractBehavior}
	 */
	private JQueryAbstractBehavior newLowerBehavior()
	{
		return new JQueryAbstractBehavior("slider-lower") {

			private static final long serialVersionUID = 1L;

			@Override
			protected String $() {

				StringBuilder statements = new StringBuilder();

				statements.append("jQuery('#").append(lower.getMarkupId()).append("').on('change', function() { ");
				statements.append("jQuery('#").append(label.getMarkupId()).append("').slider('values', 0, jQuery(this).val()); "); //change the slider value (+slide)
				statements.append("} );");

				return String.format("jQuery(function() { %s });", statements);
			}
		};
	}

	/**
	 * Gets a new behavior that will handle the change event triggered on provided upper-input.<br/>
	 * The behavior is added to the input that has been provided in the constructor (means it is visible and to user can interact with)
	 * @return a {@link JQueryAbstractBehavior}
	 */
	private JQueryAbstractBehavior newUpperBehavior()
	{
		return new JQueryAbstractBehavior("slider-upper") {

			private static final long serialVersionUID = 1L;

			@Override
			protected String $() {

				StringBuilder statements = new StringBuilder();

				statements.append("jQuery('#").append(upper.getMarkupId()).append("').on('change', function() { ");
				statements.append("jQuery('#").append(label.getMarkupId()).append("').slider('values', 1, jQuery(this).val()); "); //change the slider value (+slide)
				statements.append("} );");

				return String.format("jQuery(function() { %s });", statements);
			}
		};
	}
}
