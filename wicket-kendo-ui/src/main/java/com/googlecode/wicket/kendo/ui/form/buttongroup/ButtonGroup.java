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
package com.googlecode.wicket.kendo.ui.form.buttongroup;

import java.io.Serializable;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.CallbackParameter;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.FormComponentPanel;
import org.apache.wicket.markup.html.form.HiddenField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.googlecode.wicket.jquery.core.IJQueryWidget;
import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.core.ajax.IJQueryAjaxAware;
import com.googlecode.wicket.jquery.core.ajax.JQueryAjaxBehavior;
import com.googlecode.wicket.kendo.ui.form.buttongroup.ButtonGroupBehavior.OnSelectAjaxBehavior;

/**
 * Provides a Kendo UI Mobile ButtonGroup {@link FormComponentPanel}
 *
 * @author Sebastien Briquet - sebfz1
 * @since 6.21.0
 * @since 7.1.0
 */
public class ButtonGroup<T extends Serializable> extends FormComponentPanel<T> implements IJQueryWidget, IButtonGroupListener
{
	private static final long serialVersionUID = 1L;

	private IModel<? extends List<T>> choices;

	/** the input that will hold the value */
	private FormComponent<Integer> input;

	protected final Options options;

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param choices the list of choices
	 */
	public ButtonGroup(String id, List<T> choices)
	{
		this(id, Model.ofList(choices));
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param choices the list of choices
	 */
	public ButtonGroup(String id, IModel<? extends List<T>> choices)
	{
		super(id);

		this.choices = wrap(choices);
		this.options = new Options();
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param model the {@link IModel}
	 * @param choices the list of choices
	 */
	public ButtonGroup(String id, IModel<T> model, List<T> choices)
	{
		this(id, model, Model.ofList(choices));
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param model the {@link IModel}
	 * @param choices the list of choices
	 */
	public ButtonGroup(String id, IModel<T> model, IModel<? extends List<T>> choices)
	{
		super(id, model);

		this.choices = wrap(choices);
		this.options = new Options();
	}

	// Events //

	@Override
	protected void onInitialize()
	{
		super.onInitialize();

		this.input = new HiddenField<Integer>("value", this.newIndexModel(), Integer.class);
		this.add(this.input.setOutputMarkupId(true));

		final WebMarkupContainer group = new WebMarkupContainer("group");
		this.add(group);

		group.add(new ListView<T>("button", this.choices) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<T> item)
			{
				item.add(ButtonGroup.this.newLabel("label", item.getModel()));

			}
		});

		this.add(JQueryWidget.newWidgetBehavior(this, group));
	}

	@Override
	public void onConfigure(JQueryBehavior behavior)
	{
		behavior.setOption("index", this.input.getModelObject());
	}

	@Override
	public void onBeforeRender(JQueryBehavior behavior)
	{
		// noop
	}

	@Override
	public final void onSelect(AjaxRequestTarget target, int index)
	{
		List<? extends T> choices = this.choices.getObject();

		if (choices != null)
		{
			this.setModelObject(choices.get(index));
			this.onSelect(target, this.getModelObject());
		}
	}

	/**
	 * Triggered when a new value is selected
	 * 
	 * @param target the {@link AjaxRequestTarget}
	 * @param value the new selected value
	 * @see #isSelectEventEnabled()
	 */
	protected void onSelect(AjaxRequestTarget target, T value)
	{
		// noop
	}

	// Properties //

	/**
	 * Indicates whether the 'select' event is enabled.<br />
	 * If true, the {@link #onSelect(AjaxRequestTarget, int)} event will be triggered.<br />
	 * <b>Note:</b> this property is not part of the {@link IButtonGroupListener} as it is done usually, because it does not condition the {@link OnSelectAjaxBehavior} binding
	 *
	 * @return false by default
	 */
	protected boolean isSelectEventEnabled()
	{
		return false;
	}

	// Methods //

	@Override
	public void convertInput()
	{
		int index = this.input.getConvertedInput();
		List<? extends T> choices = this.choices.getObject();

		if (choices != null && 0 <= index && index < choices.size())
		{
			this.setConvertedInput(choices.get(index));
		}
		else
		{
			this.setConvertedInput(null);
		}
	}

	@Override
	protected void detachModel()
	{
		super.detachModel();

		if (this.choices != null)
		{
			this.choices.detach();
		}
	}

	// Factories //

	/**
	 * Gets a new {@link IModel} for the current choice index
	 * 
	 * @return a new {@code IModel}
	 */
	private IModel<Integer> newIndexModel()
	{
		return new IModel<Integer>() {

			private static final long serialVersionUID = 1L;

			@Override
			public Integer getObject()
			{
				List<? extends T> choices = ButtonGroup.this.choices.getObject();

				if (choices != null)
				{
					return choices.indexOf(ButtonGroup.this.getModelObject()); // uses equals
				}

				return ButtonGroupBehavior.NONE;
			}

			@Override
			public void setObject(Integer index)
			{
				List<? extends T> choices = ButtonGroup.this.choices.getObject();

				if (choices != null && 0 <= index && index < choices.size())
				{
					ButtonGroup.this.setModelObject(choices.get(index));
				}
			}

			@Override
			public void detach()
			{
				// noop
			}
		};
	}

	/**
	 * Gets a new {@link Component} that acts as the button's label
	 * 
	 * @param id the label markup id
	 * @param model the label {@link IModel}
	 * @return a new {@code Component}
	 */
	protected Component newLabel(String id, IModel<T> model)
	{
		return new Label(id, model);
	}

	// IJQueryWidget //

	@Override
	public ButtonGroupBehavior newWidgetBehavior(String selector)
	{
		return new ButtonGroupBehavior(selector, this.options, this) {

			private static final long serialVersionUID = 1L;

			@Override
			protected JQueryAjaxBehavior newOnSelectAjaxBehavior(IJQueryAjaxAware source)
			{
				return new OnSelectAjaxBehavior(source) {

					private static final long serialVersionUID = 1L;

					@Override
					public CharSequence getCallbackFunctionBody(CallbackParameter... parameters)
					{
						String statement = String.format("jQuery('#%s').val(e.index);", input.getMarkupId());

						if (ButtonGroup.this.isSelectEventEnabled())
						{
							statement += super.getCallbackFunctionBody(parameters);
						}

						return statement;
					}
				};
			}
		};
	}
}
