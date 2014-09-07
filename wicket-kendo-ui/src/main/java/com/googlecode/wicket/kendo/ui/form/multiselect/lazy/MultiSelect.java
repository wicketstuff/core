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
package com.googlecode.wicket.kendo.ui.form.multiselect.lazy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.model.IModel;

import com.googlecode.wicket.jquery.core.IJQueryWidget;
import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.core.behavior.ChoiceModelBehavior;
import com.googlecode.wicket.jquery.core.data.IChoiceProvider;
import com.googlecode.wicket.jquery.core.utils.RendererUtils;
import com.googlecode.wicket.kendo.ui.renderer.ChoiceRenderer;

/**
 * Provides a Kendo UI MultiSelect widget.<br/>
 * This lazy version loads the list of choices asynchronously
 *
 * @author Sebastien Briquet - sebfz1
 *
 * @param <T> the model object type
 */
public abstract class MultiSelect<T> extends FormComponent<Collection<T>> implements IJQueryWidget, IChoiceProvider<T>
{
	private static final long serialVersionUID = 1L;

	private ChoiceModelBehavior<T> choiceModelBehavior;

	/** cache of current choices, needed to retrieve the user selected object */
	private List<T> choices = null;

	/** the data-source renderer */
	private ChoiceRenderer<? super T> renderer;

	/** inner list width. 0 means that it will not be handled */
	private int width = 0;

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 */
	public MultiSelect(String id)
	{
		super(id);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param renderer the {@link ChoiceRenderer}
	 */
	public MultiSelect(String id, ChoiceRenderer<? super T> renderer)
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
	public MultiSelect(String id, IModel<? extends Collection<T>> model)
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
	@SuppressWarnings("unchecked")
	public MultiSelect(String id, IModel<? extends Collection<T>> model, ChoiceRenderer<? super T> renderer)
	{
		super(id, (IModel<Collection<T>>) model);

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
	public MultiSelect<T> setListWidth(int width)
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
	private List<T> internalGetChoices()
	{
		this.choices = this.getChoices();

		return this.choices;
	}

	@Override
	protected Collection<T> convertValue(String[] values)
	{
		List<T> list = new ArrayList<T>(values.length);

		if (this.choices != null)
		{
			for (T object : this.choices)
			{
				for (String value : values)
				{
					if (value.equals(this.renderer.getText(object)))
					{
						list.add(object);
						break;
					}
				}
			}
		}

		return list;
	}

	@Override
	public void updateModel()
	{
		FormComponent.updateCollectionModel(this);
	}

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
		behavior.setOption("value", this.getModelValue());
		behavior.setOption("dataTextField", Options.asString(this.renderer.getTextField()));
		behavior.setOption("dataValueField", Options.asString(this.renderer.getValueField()));

		if (this.getListWidth() > 0)
		{
			behavior.setOption("open", String.format("function(e) { e.sender.list.width(%d); }", this.getListWidth()));
		}
	}

	@Override
	protected String getModelValue()
	{
		List<String> values = new ArrayList<String>();

		for (T value : this.getModelObject())
		{
			values.add(RendererUtils.toJson(value, this.renderer));
		}

		return values.toString();
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
		return new MultiSelectBehavior(selector) {

			private static final long serialVersionUID = 1L;

			@Override
			protected CharSequence getChoiceCallbackUrl()
			{
				return choiceModelBehavior.getCallbackUrl();
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
				return MultiSelect.this.internalGetChoices();
			}
		};
	}
}
