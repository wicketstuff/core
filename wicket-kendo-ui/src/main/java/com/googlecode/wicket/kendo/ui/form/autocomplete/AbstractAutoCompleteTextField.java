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

import java.util.Collections;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;

import com.googlecode.wicket.jquery.core.IJQueryWidget;
import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.core.behavior.ChoiceModelBehavior;
import com.googlecode.wicket.jquery.core.renderer.ITextRenderer;
import com.googlecode.wicket.jquery.core.renderer.TextRenderer;
import com.googlecode.wicket.jquery.core.template.IJQueryTemplate;
import com.googlecode.wicket.jquery.core.utils.RequestCycleUtils;
import com.googlecode.wicket.kendo.ui.KendoDataSource;
import com.googlecode.wicket.kendo.ui.renderer.ChoiceRenderer;
import com.googlecode.wicket.kendo.ui.template.KendoTemplateBehavior;

/**
 * Provides the base class for a Kendo UI auto-complete widget.<br>
 * <b>Caution:</b> in this base class, the model object is not set and there is no converter
 *
 * @author Sebastien Briquet - sebfz1
 *
 * @param <T> the model object type
 * @param <C> the model of choices
 */
public abstract class AbstractAutoCompleteTextField<T, C> extends TextField<T> implements IJQueryWidget, IAutoCompleteListener
{
	private static final long serialVersionUID = 1L;

	/** cache of current choices, needed to retrieve the user selected object */
	private List<C> choices = null;
	private ChoiceModelBehavior<C> choiceModelBehavior;

	/** the data-source renderer */
	final ITextRenderer<? super C> renderer;

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
	public AbstractAutoCompleteTextField(String id)
	{
		this(id, new TextRenderer<C>(), null);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param renderer the {@link ChoiceRenderer}
	 */
	public AbstractAutoCompleteTextField(String id, ITextRenderer<? super C> renderer)
	{
		this(id, renderer, null);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param type type for field validation
	 */
	public AbstractAutoCompleteTextField(String id, Class<T> type)
	{
		this(id, new TextRenderer<C>(), type);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param renderer the {@link ChoiceRenderer}
	 * @param type type for field validation
	 */
	public AbstractAutoCompleteTextField(String id, ITextRenderer<? super C> renderer, Class<T> type)
	{
		super(id, type);

		this.renderer = renderer;
		this.template = this.newTemplate();
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param model the {@link IModel}
	 */
	public AbstractAutoCompleteTextField(String id, IModel<T> model)
	{
		this(id, model, new TextRenderer<C>(), null);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param model the {@link IModel}
	 * @param renderer the {@link ChoiceRenderer}
	 */
	public AbstractAutoCompleteTextField(String id, IModel<T> model, ITextRenderer<? super C> renderer)
	{
		this(id, model, renderer, null);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param model the {@link IModel}
	 * @param type type for field validation
	 */
	public AbstractAutoCompleteTextField(String id, IModel<T> model, Class<T> type)
	{
		this(id, model, new TextRenderer<C>(), type);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param model the {@link IModel}
	 * @param renderer the {@link ChoiceRenderer}
	 * @param type type for field validation
	 */
	public AbstractAutoCompleteTextField(String id, IModel<T> model, ITextRenderer<? super C> renderer, Class<T> type)
	{
		super(id, model, type);

		this.renderer = renderer;
		this.template = this.newTemplate();
	}

	// Properties //

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
	public AbstractAutoCompleteTextField<T, C> setListWidth(int width)
	{
		this.width = width;

		return this;
	}

	/**
	 * Gets the {@link ITextRenderer}
	 *
	 * @return the {@link ITextRenderer}
	 */
	public ITextRenderer<? super C> getRenderer()
	{
		return this.renderer;
	}

	// Methods //

	/**
	 * Call {@link #getChoices()} and cache the result<br>
	 * Internal use only
	 *
	 * @return the list of choices
	 */
	private List<C> internalGetChoices(String input)
	{
		this.choices = this.getChoices(input);

		return this.choices;
	}

	public List<C> getChoices()
	{
		if (this.choices != null)
		{
			return this.choices;
		}

		return Collections.emptyList();
	}

	protected abstract List<C> getChoices(String input);

	// Events //

	@Override
	protected void onInitialize()
	{
		super.onInitialize();

		this.choiceModelBehavior = this.newChoiceModelBehavior();
		this.add(this.choiceModelBehavior);

		// choiceModelBehavior should be set at this point
		this.add(JQueryWidget.newWidgetBehavior(this));

		if (this.template != null)
		{
			this.templateBehavior = new KendoTemplateBehavior(this.template);
			this.add(this.templateBehavior);
		}
	}

	@Override
	public void onConfigure(JQueryBehavior behavior)
	{
		// set data source //
		behavior.setOption("dataTextField", Options.asString(this.renderer.getTextField()));

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

	// Events //

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

	@Override
	protected void onComponentTag(final ComponentTag tag)
	{
		super.onComponentTag(tag);

		tag.put("autocomplete", "off"); // disable browser's autocomplete
	}

	@Override
	public final void onSelect(AjaxRequestTarget target, int index)
	{
		if (-1 < index && index < this.choices.size())
		{
			this.onSelected(target, this.choices.get(index));
		}
	}

	/**
	 * Triggered when the user selects an item from results that matched its input
	 *
	 * @param target the {@link AjaxRequestTarget}
	 * @param choice the selected choice
	 */
	protected void onSelected(AjaxRequestTarget target, C choice)
	{
		// noop
	}

	// IJQueryWidget //

	@Override
	public JQueryBehavior newWidgetBehavior(String selector)
	{
		return new AutoCompleteBehavior(selector, this) {

			private static final long serialVersionUID = 1L;

			// Properties //
			
			@Override
			protected CharSequence getDataSourceUrl()
			{
				return AbstractAutoCompleteTextField.this.getCallbackUrl();
			}
			
			// Events //

			@Override
			protected void onConfigure(KendoDataSource dataSource)
			{
				AbstractAutoCompleteTextField.this.onConfigure(dataSource);
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
	protected ChoiceModelBehavior<C> newChoiceModelBehavior()
	{
		return new ChoiceModelBehavior<C>(this.renderer, this.template) {

			private static final long serialVersionUID = 1L;
			private static final String TERM = "filter[filters][0][value]";

			@Override
			public List<C> getChoices()
			{
				final String input = RequestCycleUtils.getQueryParameterValue(TERM).toString();

				return AbstractAutoCompleteTextField.this.internalGetChoices(input);
			}
		};
	}
}
