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
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.validator.RangeValidator;

import com.googlecode.wicket.jquery.ui.JQueryAbstractBehavior;
import com.googlecode.wicket.jquery.ui.JQueryBehavior;

/**
 * Provides a jQuery slider based on a {@link FormComponentPanel}
 *
 * @author Sebastien Briquet - sebfz1
 */
public class Slider extends AbstractSlider<Integer>
{
	public enum Range {
		MIN("'min'"),
		MAX("'max'");

		private String range;

		private Range(String range)
		{
			this.range = range;
		}

		@Override
		public String toString()
		{
			return this.range;
		}
	}

	private static final long serialVersionUID = 1L;

	private IValidator<Integer> rangeValidator = null;
	protected AbstractTextComponent<Integer> input = null; // will be accessed in AjaxSlider

	/**
	 * Constructor
	 * @param id the markup id
	 */
	public Slider(String id)
	{
		super(id);
		this.init();
	}

	/**
	 * Constructor
	 * @param id the markup id
	 * @param model the {@link IModel}
	 */
	public Slider(String id, IModel<Integer> model)
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
	public Slider(String id, IModel<Integer> model, Label label)
	{
		super(id, model, label);
		this.init();
	}

	/**
	 * Constructor
	 * @param id the markup id
	 * @param model the {@link IModel}
	 * @param input the {@link TextField} that will host the slider value when being updated.
	 */
	public Slider(String id, IModel<Integer> model, TextField<Integer> input)
	{
		super(id, model);

		this.input = input;
		this.input.setRequired(true); //prevent null model object in case of no value is supplied.
		this.input.setOutputMarkupId(true);
		this.input.add(this.newInputBehavior());

		this.init();
	}

	@Override
	protected Fragment newInputFragment(String id)
	{
		Fragment fragment;

		// input TextField has not been specified in ctor //
		if (this.input == null)
		{
			this.input = new HiddenField<Integer>("input", this.getModel(), Integer.class);

			fragment = new Fragment(id, "input-fragment", this);
			fragment.add(this.input.setOutputMarkupPlaceholderTag(true));
		}
		else
		{
			fragment = super.newInputFragment(id); //return empty fragment
		}

		return fragment;
	}

	@Override
 	protected void convertInput()
 	{
 		this.setConvertedInput(this.input.getConvertedInput());
 	}

	@Override
	@SuppressWarnings("unchecked")
	public <W extends AbstractSlider<Integer>> W setRangeValidator(RangeValidator<Integer> validator)
	{
		this.rangeValidator = validator;

		return (W) this;
	}

	/**
	 * Get the label pattern to be used to display the value. Should be overridden with care!<br/>
	 * <b>Note: </b> the pattern is a javascript string, where the value is represented by "ui.value".<br/>
	 *
	 * @return default to ui.value
	 */
	protected String getLabelPattern()
	{
		return "ui.value";
	}

	// Events //
	@Override
	protected void onInitialize()
	{
		super.onInitialize();

		if (this.rangeValidator != null)
		{
			this.input.add(this.rangeValidator); //let throw a NPE if no input is defined.
		}
	}

	@Override
	protected void onConfigure(JQueryBehavior behavior)
	{
		super.onConfigure(behavior);

		StringBuilder statements = new StringBuilder();

		statements.append("jQuery('#").append(this.input.getMarkupId()).append("').val(ui.value); ");

		if (super.labelId != null)
		{
			statements.append("jQuery('#").append(super.labelId).append("').text(").append(this.getLabelPattern()).append("); ");
		}

		behavior.setOption("slide", String.format("function(event, ui) { %s }", statements));
		behavior.setOption("value", this.getModelObject());
	}


	// Options //

	/**
	 * Sets the {@link Range}
	 * @param range
	 * @return {@link Slider} (this)
	 */
	public Slider setRange(Range range)
	{
		super.options.set("range", range);
		return this;
	}

	// Factory //
	/**
	 * Gets a new behavior that will handle the change event triggered on provided input.<br/>
	 * The behavior is added to the input that has been provided in the constructor (means it is visible and the user can interact with)
	 * @return a {@link JQueryAbstractBehavior}
	 */
	private JQueryAbstractBehavior newInputBehavior()
	{
		return new JQueryAbstractBehavior("slider-input") {

			private static final long serialVersionUID = 1L;

			@Override
			protected String $() {

				StringBuilder statements = new StringBuilder();

				statements.append("jQuery('#").append(Slider.this.input.getMarkupId()).append("').on('change', function() { ");
				statements.append("jQuery('#").append(Slider.this.label.getMarkupId()).append("').slider('value', jQuery(this).val()); "); //change the slider value (+slide)
				statements.append("} );");

				return String.format("jQuery(function() { %s });", statements);
			}
		};
	}
}
