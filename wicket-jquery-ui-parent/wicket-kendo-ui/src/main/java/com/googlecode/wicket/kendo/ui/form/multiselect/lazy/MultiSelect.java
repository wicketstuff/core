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

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.lang.Generics;

import com.googlecode.wicket.jquery.core.IJQueryWidget;
import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.core.behavior.ChoiceModelBehavior;
import com.googlecode.wicket.jquery.core.event.SelectionChangedAdapter;
import com.googlecode.wicket.jquery.core.renderer.IChoiceRenderer;
import com.googlecode.wicket.jquery.core.template.IJQueryTemplate;
import com.googlecode.wicket.jquery.core.utils.RequestCycleUtils;
import com.googlecode.wicket.kendo.ui.KendoDataSource;
import com.googlecode.wicket.kendo.ui.KendoUIBehavior;
import com.googlecode.wicket.kendo.ui.renderer.ChoiceRenderer;
import com.googlecode.wicket.kendo.ui.template.KendoTemplateBehavior;

/**
 * Provides a Kendo UI MultiSelect widget.<br>
 * This lazy version loads the list of choices asynchronously
 *
 * @author Sebastien Briquet - sebfz1
 *
 * @param <T> the model object type
 */
public abstract class MultiSelect<T> extends FormComponent<Collection<T>> implements IJQueryWidget // NOSONAR
{
	private static final long serialVersionUID = 1L;

	/** cache of current choices, needed to retrieve the user selected object */
	private List<T> choices = null;
	private ChoiceModelBehavior<T> choiceModelBehavior;

	/** the data-source renderer */
	private IChoiceRenderer<? super T> renderer;

	/** the template */
	private final IJQueryTemplate template;
	private KendoTemplateBehavior templateBehavior = null;

	/** inner list width. 0 means that it will not be handled */
	private int width = 0;

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 */
	protected MultiSelect(String id)
	{
		this(id, new ChoiceRenderer<T>());
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param model the {@link IModel}
	 */
	protected MultiSelect(String id, IModel<? extends Collection<T>> model)
	{
		this(id, model, new ChoiceRenderer<T>());
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param renderer the {@link IChoiceRenderer}
	 */
	protected MultiSelect(String id, IChoiceRenderer<? super T> renderer)
	{
		super(id);

		this.renderer = renderer;
		this.template = this.newTemplate();
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param model the {@link IModel}
	 * @param renderer the {@link ChoiceRenderer}
	 */
	@SuppressWarnings("unchecked")
	protected MultiSelect(String id, IModel<? extends Collection<T>> model, IChoiceRenderer<? super T> renderer)
	{
		super(id, (IModel<Collection<T>>) model);

		this.renderer = renderer;
		this.template = this.newTemplate();
	}

	// Properties //

	/**
	 * Gets the current/cached list of choices
	 * 
	 * @return the list of choices
	 */
	public final List<T> getChoices()
	{
		if (this.choices != null)
		{
			return this.choices;
		}

		return Collections.emptyList();
	}

	/**
	 * Gets the {@link ChoiceModelBehavior} callback url
	 * 
	 * @return the {@code ChoiceModelBehavior} callback url
	 */
	protected CharSequence getCallbackUrl()
	{
		return this.choiceModelBehavior.getCallbackUrl();
	}

	/**
	 * Gets the template script token/id
	 * 
	 * @return the template script token/id
	 */
	public String getTemplateToken()
	{
		if (this.templateBehavior != null)
		{
			return this.templateBehavior.getToken();
		}

		return null;
	}

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
	 * Call {@link #getChoices()} and cache the result<br>
	 * Internal use only
	 *
	 * @param input the user input
	 * @return the list of choices
	 */
	private List<T> internalGetChoices(String input)
	{
		this.choices = this.getChoices(input);

		return this.choices;
	}

	/**
	 * Get the list of choice according to the user-input
	 * 
	 * @param input the user-input. Empty if {@code serverFiltering} is set to {@code false}
	 * @return list of choice
	 */
	protected abstract List<T> getChoices(String input);

	@Override
	public void convertInput()
	{
		final List<T> list = Generics.newArrayList();
		final String[] values = this.getInputAsArray();

		if (values != null)
		{
			for (String value : values)
			{
				for (T choice : this.choices)
				{
					if (this.renderer.getValue(choice).equals(value))
					{
						list.add(choice);
					}
				}
			}
		}

		this.setConvertedInput(list);
	}

	@Override
	public void updateModel()
	{
		FormComponent.updateCollectionModel(this);
	}

	/**
	 * Gets the Kendo UI widget
	 *
	 * @return the jQuery object
	 */
	public String widget()
	{
		return KendoUIBehavior.widget(this, MultiSelectBehavior.METHOD);
	}

	/**
	 * Refreshes the widget by reading from the datasource
	 *
	 * @param target the {@link AjaxRequestTarget}
	 */
	public void refresh(AjaxRequestTarget target)
	{
		target.appendJavaScript(String.format("var $w = %s; if ($w) { $w.dataSource.read(); }", this.widget()));
	}

	// Properties //

	@Override
	protected String getModelValue()
	{
		List<String> values = Generics.newArrayList();

		for (T value : this.getModelObject())
		{
			values.add(String.valueOf(this.renderer.render(value)));
		}

		return values.toString();
	}

	// Events //

	@Override
	protected void onInitialize()
	{
		super.onInitialize();

		this.choiceModelBehavior = this.newChoiceModelBehavior();
		this.add(this.choiceModelBehavior);

		this.add(JQueryWidget.newWidgetBehavior(this));

		if (this.template != null)
		{
			this.templateBehavior = new KendoTemplateBehavior(this.template);
			this.add(this.templateBehavior);
		}
	}
	
	@Override
	protected void onComponentTag(ComponentTag tag)
	{
		this.checkComponentTag(tag, "select"); // must be attached to a 'select' tag

		super.onComponentTag(tag);
	}

	@Override
	public void onConfigure(JQueryBehavior behavior)
	{
		behavior.setOption("value", this.getModelValue());
		behavior.setOption("dataTextField", Options.asString(this.renderer.getTextField()));
		behavior.setOption("dataValueField", Options.asString(this.renderer.getValueField()));

		// set template (if any) //
		if (this.templateBehavior != null)
		{
			behavior.setOption("template", String.format("jQuery('#%s').html()", this.getTemplateToken()));
		}

		// set list-width //
		if (this.getListWidth() > 0)
		{
			behavior.setOption("open", String.format("function(e) { e.sender.list.width(%d); }", this.getListWidth()));
		}
	}

	/**
	 * Configure the {@link KendoDataSource} with additional options
	 * 
	 * @param dataSource the {@link KendoDataSource}
	 */
	protected void onConfigure(KendoDataSource dataSource)
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
		return new MultiSelectBehavior(selector, new SelectionChangedAdapter()) { // NOSONAR

			private static final long serialVersionUID = 1L;

			// Properties //

			@Override
			protected CharSequence getDataSourceUrl()
			{
				return MultiSelect.this.getCallbackUrl();
			}

			// Events //

			@Override
			protected void onConfigure(KendoDataSource dataSource)
			{
				MultiSelect.this.onConfigure(dataSource);
			}
		};
	}

	// Factories //

	/**
	 * Gets a new {@link IJQueryTemplate} to customize the rendering<br>
	 * The properties used in the template text (ie: ${data.name}) should be of the prefixed by "data." and should be identified in the list returned by {@link IJQueryTemplate#getTextProperties()} (without "data.")
	 *
	 * @return null by default
	 */
	protected IJQueryTemplate newTemplate()
	{
		return null;
	}

	/**
	 * Gets a new {@link ChoiceModelBehavior}
	 *
	 * @return a new {@code ChoiceModelBehavior}
	 */
	protected ChoiceModelBehavior<T> newChoiceModelBehavior()
	{
		return new ChoiceModelBehavior<T>(this.renderer, this.template) {

			private static final long serialVersionUID = 1L;

			@Override
			public List<T> getChoices()
			{
				final String input = RequestCycleUtils.getQueryParameterValue(FILTER_VALUE).toString("");

				return MultiSelect.this.internalGetChoices(input);
			}
		};
	}
}
