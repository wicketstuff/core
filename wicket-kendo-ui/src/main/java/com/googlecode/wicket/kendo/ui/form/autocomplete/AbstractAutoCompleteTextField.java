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
import com.googlecode.wicket.jquery.core.behavior.ChoiceModelBehavior;
import com.googlecode.wicket.jquery.core.renderer.ITextRenderer;
import com.googlecode.wicket.jquery.core.renderer.TextRenderer;
import com.googlecode.wicket.jquery.core.template.IJQueryTemplate;
import com.googlecode.wicket.jquery.core.utils.RequestCycleUtils;
import com.googlecode.wicket.kendo.ui.KendoTemplateBehavior;
import com.googlecode.wicket.kendo.ui.renderer.ChoiceRenderer;

/**
 * Provides the base class for a Kendo UI auto-complete widget.<br/>
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

	private ChoiceModelBehavior<C> choiceModelBehavior;
	protected final ITextRenderer<? super C> renderer;
	private final IJQueryTemplate template;
	private KendoTemplateBehavior templateBehavior = null;

	/** cache of current choices, needed to retrieve the user selected object */
	private List<C> choices;

	/** inner list width. 0 means that it will not be handled */
	private int width = 0;

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 */
	public AbstractAutoCompleteTextField(String id)
	{
		this(id, new TextRenderer<C>());
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param renderer the {@link ChoiceRenderer}
	 */
	public AbstractAutoCompleteTextField(String id, ITextRenderer<? super C> renderer)
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
	 */
	public AbstractAutoCompleteTextField(String id, IModel<T> model)
	{
		this(id, model, new TextRenderer<C>());
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
		super(id, model);

		this.renderer = renderer;
		this.template = this.newTemplate();
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
	public AbstractAutoCompleteTextField<T, C> setListWidth(int width)
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
	private List<C> internalGetChoices(String input)
	{
		this.choices = this.getChoices(input);

		return this.choices;
	}

	protected abstract List<C> getChoices(String input);

	// Events //

	@Override
	protected void onInitialize()
	{
		super.onInitialize();

		this.add(this.choiceModelBehavior = this.newChoiceModelBehavior());
		this.add(JQueryWidget.newWidgetBehavior(this));

		if (this.template != null)
		{
			this.add(this.templateBehavior = new KendoTemplateBehavior(this.template));
		}
	}

	@Override
	public void onConfigure(JQueryBehavior behavior)
	{
		// set data source //
		behavior.setOption("dataTextField", Options.asString(this.renderer.getTextField()));

		// set template (if any) //
		if (this.template != null)
		{
			behavior.setOption("template", String.format("jQuery('#%s').html()", this.templateBehavior.getToken()));
		}

		// set list-width //
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
				AbstractAutoCompleteTextField.this.onSelect(target, index);
			}
		};
	}

	// Factories //

	/**
	 * Gets a new {@link IJQueryTemplate} to customize the rendering<br/>
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
	 * @return a new {@link ChoiceModelBehavior}
	 */
	protected ChoiceModelBehavior<C> newChoiceModelBehavior()
	{
		return new ChoiceModelBehavior<C>(this.renderer) {

			private static final long serialVersionUID = 1L;
			private static final String TERM = "filter[filters][0][value]";

			@Override
			public List<C> getChoices()
			{
				final String input = RequestCycleUtils.getQueryParameterValue(TERM).toString();

				return AbstractAutoCompleteTextField.this.internalGetChoices(input);
			}

			@Override
			protected List<String> getProperties()
			{
				if (AbstractAutoCompleteTextField.this.template != null)
				{
					return AbstractAutoCompleteTextField.this.template.getTextProperties();
				}

				return super.getProperties();
			}
		};
	}
}
