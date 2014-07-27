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
package com.googlecode.wicket.kendo.ui.form.autocomplete;

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;

import com.googlecode.wicket.jquery.core.IJQueryWidget;
import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.core.utils.RequestCycleUtils;
import com.googlecode.wicket.kendo.ui.behavior.ChoiceModelBehavior;
import com.googlecode.wicket.kendo.ui.renderer.ChoiceRenderer;

/**
 * Provides a Kendo UI auto-complete widget.<br/>
 *
 * @author Sebastien Briquet - sebfz1
 *
 * @param <T> the model object type
 */
public abstract class AutoCompleteTextField<T> extends TextField<T> /* TextField<Collection<T>> */ implements IJQueryWidget, IAutoCompleteListener
{
	private static final long serialVersionUID = 1L;

	/** Default separator */
	private static final String SEPARATOR = ", ";

	private ChoiceModelBehavior<T> choiceModelBehavior;

	/** cache of current choices, needed to retrieve the user selected object */
	private List<T> choices;

	/** the datasource renderer */
	private final ChoiceRenderer<? super T> renderer;

	/** inner list width. 0 means that it will not be handled */
	private int width = 0;

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 */
	public AutoCompleteTextField(String id)
	{
		this(id, new ChoiceRenderer<T>());
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param renderer the {@link ChoiceRenderer}
	 */
	public AutoCompleteTextField(String id, ChoiceRenderer<? super T> renderer)
	{
		super(id);

		this.renderer = renderer;
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param model the {@link IModel}
	 */
	//public AutoCompleteTextField(String id, IModel<? extends Collection<T>> model)
	public AutoCompleteTextField(String id, IModel<T> model)
	{
		this(id, model, new ChoiceRenderer<T>());
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param model the {@link IModel}
	 * @param renderer the {@link ChoiceRenderer}
	 */
	public AutoCompleteTextField(String id, IModel<T> model, ChoiceRenderer<? super T> renderer)
	{
		super(id, model);

		this.renderer = renderer;
	}

	// Properties //

	/**
	 * Gets the (inner) list width.
	 *
	 * @return the list width
	 */
	public int getListWidth()
	{
		return this.width;
	}

	/**
	 * Sets the (inner) list width.
	 *
	 * @param width the list width
	 * @return this, for chaining
	 */
	public AutoCompleteTextField<T> setListWidth(int width)
	{
		this.width = width;

		return this;
	}

	// Methods //

	/**
	 * Call {@link #getChoices()} and cache the result<br/>
	 * Internal use only
	 *
	 * @return the list of choices
	 */
	private List<T> internalGetChoices(String input)
	{
		// for IModel<? extends Collection<T>>
//		this.choices.addAll(this.getChoices(input));
		this.choices = this.getChoices(input);

		return this.choices;
	}

	protected abstract List<T> getChoices(String input);

//	@Override
//	protected String getModelValue()
//	{
//		Collection<T> values = this.getModelObject();
//
//		if (values != null)
//		{
//			StringBuilder builder = new StringBuilder();
//			Iterator<T> iterator = values.iterator();
//
//			for (int count = 0; iterator.hasNext(); count++)
//			{
//				if (count > 0)
//				{
//					builder.append(SEPARATOR);
//				}
//
//				builder.append(this.renderer.getText(iterator.next()));
//			}
//
//			return builder.toString();
//		}
//
//		return "";
//	}

	@Override
	protected String getModelValue()
	{
		return this.renderer.getText(this.getModelObject()); // renderer cannot be null.
	}

// for IModel<? extends Collection<T>>
//	@Override
//	protected T convertValue(String[] values)
//	{
//		List<T> list = new ArrayList<T>(values.length);
//
//		if (this.choices != null)
//		{
//			for (T object : this.choices)
//			{
//				for (String value : values)
//				{
//					if (value.equals(this.renderer.getText(object)))
//					{
//						list.add(object);
//						break;
//					}
//				}
//			}
//		}
//
//		return list;
//	}

// for IModel<? extends Collection<T>>
//	@Override
//	public void updateModel()
//	{
//		FormComponent.updateCollectionModel(this);
//	}

	// Events //

	@Override
	protected void onInitialize()
	{
		super.onInitialize();

		this.add(this.choiceModelBehavior = this.newChoiceModelBehavior());
		this.add(JQueryWidget.newWidgetBehavior(this));
	}

	@Override
	public void onConfigure(JQueryBehavior behavior)
	{
//		behavior.setOption("value", this.getModelValue());
//		behavior.setOption("separator", Options.asString(SEPARATOR));
		behavior.setOption("dataTextField", Options.asString(this.renderer.getTextField()));
		behavior.setOption("dataValueField", Options.asString(this.renderer.getValueField()));

		if (this.getListWidth() > 0)
		{
			behavior.setOption("open", String.format("function(e) { e.sender.list.width(%d); }", this.getListWidth()));
		}
	}

	@Override
	public void onBeforeRender(JQueryBehavior behavior)
	{
		// noop
	}

	@Override
	protected void onComponentTag(final ComponentTag tag)
	{
		super.onComponentTag(tag);

		tag.put("autocomplete", "off"); // disable browser's autocomplete
	}

	@Override
	public final void onSelect(AjaxRequestTarget target, int index)
	{
		if (0 < index && index < this.choices.size())
		{
			T choice = this.choices.get(index);

			this.setModelObject(choice);
			this.onSelected(target, choice);
		}
	}

	/**
	 * Triggered when the user selects an item from results that matched its input
	 *
	 * @param target the {@link AjaxRequestTarget}
	 * @param choice TODO javadoc or remove
	 */
	protected void onSelected(AjaxRequestTarget target, T choice)
	{
	}

	// IJQueryWidget //

	@Override
	public JQueryBehavior newWidgetBehavior(String selector)
	{
		return new AutoCompleteBehavior(selector) {

			private static final long serialVersionUID = 1L;

			@Override
			protected CharSequence getChoiceCallbackUrl()
			{
				return choiceModelBehavior.getCallbackUrl();
			}

			@Override
			public void onSelect(AjaxRequestTarget target, int index)
			{
				AutoCompleteTextField.this.onSelect(target, index);
			}
		};
	}

	// Factories //

	/**
	 * Gets a new {@link ChoiceModelBehavior}
	 *
	 * @return a new {@link ChoiceModelBehavior}
	 */
	protected ChoiceModelBehavior<T> newChoiceModelBehavior()
	{
		return new ChoiceModelBehavior<T>(this.renderer) {

			private static final long serialVersionUID = 1L;

			@Override
			public List<T> getChoices()
			{
				final String input = RequestCycleUtils.getQueryParameterValue("filter[filters][0][value]").toString();

				return AutoCompleteTextField.this.internalGetChoices(input);
			}
		};
	}
}
