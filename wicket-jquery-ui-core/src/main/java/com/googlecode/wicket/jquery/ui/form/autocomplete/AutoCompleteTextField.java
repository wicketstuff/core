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
package com.googlecode.wicket.jquery.ui.form.autocomplete;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.convert.IConverter;

import com.googlecode.wicket.jquery.ui.IJQueryWidget;
import com.googlecode.wicket.jquery.ui.JQueryBehavior;
import com.googlecode.wicket.jquery.ui.JQueryEvent;
import com.googlecode.wicket.jquery.ui.Options;
import com.googlecode.wicket.jquery.ui.ajax.JQueryAjaxBehavior;
import com.googlecode.wicket.jquery.ui.renderer.ITextRenderer;
import com.googlecode.wicket.jquery.ui.renderer.TextRenderer;
import com.googlecode.wicket.jquery.ui.template.IJQueryTemplate;
import com.googlecode.wicket.jquery.ui.template.JQueryTemplateBehavior;
import com.googlecode.wicket.jquery.ui.utils.RequestCycleUtils;

/**
 * Provides a jQuery auto-complete widget
 *
 * @author Sebastien Briquet - sebfz1
 *
 * @param <T> the type of the model object
 */
public abstract class AutoCompleteTextField<T extends Serializable> extends TextField<T> implements IJQueryWidget
{
	private static final long serialVersionUID = 1L;
	private static final String METHOD = "autocomplete";

	/**
	 * Behavior that will be called when the user enters an input
	 */
	private AutoCompleteBehavior<T> sourceBehavior;

	/**
	 * Behavior that will called when the user selects a value from results
	 */
	private JQueryAjaxBehavior onSelectBehavior;

	private final ITextRenderer<? super T> renderer;
	private final IConverter<T> converter;

	private final IJQueryTemplate template;
	private JQueryTemplateBehavior templateBehavior = null;

	/**
	 * Cache of current choices, needed to retrieve the user selected object
	 */
	private List<T> choices;

	/**
	 * Constructor
	 * @param id the markup id
	 */
	public AutoCompleteTextField(String id)
	{
		this(id, new TextRenderer<T>(), null);
	}

	/**
	 * Constructor
	 * @param id the markup id
	 * @param type the type of the bean. This parameter should be supplied for the internal converter ({@link #getConverter(Class)}) to be used.
	 */
	public AutoCompleteTextField(String id, Class<T> type)
	{
		this(id, new TextRenderer<T>(), type);
	}

	/**
	 * Constructor
	 * @param id the markup id
	 * @param renderer the {@link ITextRenderer}
	 */
	public AutoCompleteTextField(String id, ITextRenderer<? super T> renderer)
	{
		this(id, renderer, null);
	}

	/**
	 * Constructor
	 * @param id the markup id
	 * @param renderer the {@link ITextRenderer}
	 * @param type the type of the bean. This parameter should be supplied for the internal converter ({@link #getConverter(Class)}) to be used.
	 */
	public AutoCompleteTextField(String id, ITextRenderer<? super T> renderer, Class<T> type)
	{
		super(id, type);

		this.renderer = renderer;
		this.template = this.newTemplate();
		this.converter = this.newConverter();
	}

	/**
	 * Constructor
	 * @param id the markup id
	 * @param model the {@link IModel}
	 */
	public AutoCompleteTextField(String id, IModel<T> model)
	{
		this(id, model, new TextRenderer<T>(), null);
	}

	/**
	 * Constructor
	 * @param id the markup id
	 * @param model the {@link IModel}
	 * @param type the type of the bean. This parameter should be supplied for the internal converter ({@link #getConverter(Class)}) to be used.
	 */
	public AutoCompleteTextField(String id, IModel<T> model, Class<T> type)
	{
		this(id, model, new TextRenderer<T>(), type);
	}

	/**
	 * Constructor
	 * @param id the markup id
	 * @param model the {@link IModel}
	 * @param renderer the {@link ITextRenderer}
	 */
	public AutoCompleteTextField(String id, IModel<T> model, ITextRenderer<? super T> renderer)
	{
		this(id, model, renderer, null);
	}

	/**
	 * Constructor
	 * @param id the markup id
	 * @param model the {@link IModel}
	 * @param renderer the {@link ITextRenderer}
	 * @param type the type of the bean. This parameter should be supplied for the internal converter ({@link #getConverter(Class)}) to be used.
	 */
	public AutoCompleteTextField(String id, IModel<T> model, ITextRenderer<? super T> renderer, Class<T> type)
	{
		super(id, model, type);

		this.renderer = renderer;
		this.template = this.newTemplate();
		this.converter = this.newConverter();
	}


	// Methods //
	@Override
	protected String getModelValue()
	{
		return this.renderer.getText(this.getModelObject()); //renderer cannot be null.
	}

	/**
	 * Gets choices matching the provided input
	 * @param input String that represent the query
	 * @return the list of choices
	 */
	protected abstract List<T> getChoices(String input);

	/**
	 * Call {@link #getChoices(String)} and cache the result<br/>
	 * Internal use only
	 * @param input String that represent the query
	 * @return the list of choices
	 */
	private List<T> internalGetChoices(String input)
	{
		this.choices = this.getChoices(input);

		return this.choices;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <C> IConverter<C> getConverter(Class<C> type)
	{
		if (type != null && type.isAssignableFrom(this.getType()))
		{
			return (IConverter<C>) this.converter;
		}

		return super.getConverter(type);
	}


	// Events //
	@Override
	protected void onInitialize()
	{
		super.onInitialize();

		this.add(this.sourceBehavior = this.newAutoCompleteBehavior());
		this.add(this.onSelectBehavior = this.newSelectBehavior());
		this.add(JQueryWidget.newWidgetBehavior(this)); //cannot be in ctor as the markupId may be set manually afterward

		if (this.template != null)
		{
			this.add(this.templateBehavior = new JQueryTemplateBehavior(this.template));
		}
	}


	/**
	 * Called immediately after the onConfigure method in a behavior. Since this is before the rendering
	 * cycle has begun, the behavior can modify the configuration of the component (i.e. {@link Options})
	 *
	 * @param behavior the {@link JQueryBehavior}
	 */
	protected void onConfigure(JQueryBehavior behavior)
	{
	}

	@Override
	protected void onComponentTag(final ComponentTag tag)
	{
		super.onComponentTag(tag);

		tag.put("autocomplete", "off"); // disable browser's autocomplete
	}

	@Override
	@SuppressWarnings("unchecked")
	public void onEvent(IEvent<?> event)
	{
		if (event.getPayload() instanceof AutoCompleteTextField.SelectEvent)
		{
			SelectEvent payload = (SelectEvent) event.getPayload();

			int index = payload.getIndex();
			if (index < this.choices.size())
			{
				T choice = AutoCompleteTextField.this.choices.get(index);

				this.setModelObject(choice);
				this.onSelected(payload.getTarget());
			}
		}
	}

	/**
	 * Triggered when the user selects an item from results that matched its input
	 * @param target the {@link AjaxRequestTarget}
	 */
	protected void onSelected(AjaxRequestTarget target)
	{
	}


	// IJQueryWidget //
	@Override
	public JQueryBehavior newWidgetBehavior(String selector)
	{
		return new JQueryBehavior(selector, METHOD) {

			private static final long serialVersionUID = 1L;

			@Override
			public void onConfigure(Component component)
			{
				AutoCompleteTextField.this.onConfigure(this);

//				this.setOption("delay", Duration.ONE_SECOND.getMilliseconds());
				this.setOption("source", Options.asString(AutoCompleteTextField.this.sourceBehavior.getCallbackUrl()));
				this.setOption("select", AutoCompleteTextField.this.onSelectBehavior.getCallbackFunction());
			}

			@Override
			protected String $()
			{
				if (templateBehavior != null)
				{
					// warning, the template text should be of the form <a>...</a> in order to work
					String render = "jQuery(function() { jQuery('%s').data('autocomplete')._renderItem = function( ul, item ) { return jQuery('<li></li>').data('item.autocomplete', item).append(jQuery.tmpl(jQuery('#%s').html(), item)).appendTo(ul) } });";
					return super.$() + String.format(render, this.selector, templateBehavior.getToken());
				}

				return super.$();
			}
		};
	}


	// Factories //
	/**
	 * Gets a new {@link IJQueryTemplate} to customize the rendering<br/>
	 * The {@link IJQueryTemplate#getText()} should return a template text of the form "&lt;a&gt;...&lt;/a&gt;".<br/>
	 * The properties used in the template text (ie: ${name}) should be identified in the list returned by {@link IJQueryTemplate#getTextProperties()}
	 * @return null by default
	 */
	protected IJQueryTemplate newTemplate()
	{
		return null;
	}

	/**
	 * Gets a new {@link IConverter}.
	 * Used when/if the bean type has been supplied to the constructor.
	 * @return the {@link IConverter}
	 */
	private IConverter<T> newConverter()
	{
		return new IConverter<T>() {

			private static final long serialVersionUID = 1L;

			@Override
			public T convertToObject(String value, Locale locale)
			{
				if (value != null && value.equals(AutoCompleteTextField.this.getModelValue()))
				{
					return AutoCompleteTextField.this.getModelObject();
				}

				return null; //if the TextField value (string) does not corresponds to the current object model (ie: user specific value), returns null.
			}

			@Override
			public String convertToString(T value, Locale locale)
			{
				return AutoCompleteTextField.this.renderer.getText(value);
			}
		};
	}

	/**
	 * Gets a new {@link AutoCompleteBehavior}
	 * @return the {@link AutoCompleteBehavior}
	 */
	private AutoCompleteBehavior<T> newAutoCompleteBehavior()
	{
		return new AutoCompleteBehavior<T>(this.renderer) {

			private static final long serialVersionUID = 1L;

			@Override
			protected List<T> getChoices(String input)
			{
				return AutoCompleteTextField.this.internalGetChoices(input);
			}

			@Override
			protected List<String> getProperties()
			{
				if (AutoCompleteTextField.this.template != null)
				{
					return AutoCompleteTextField.this.template.getTextProperties();
				}

				return super.getProperties();
			}
		};
	}

	/**
	 * Gets a new {@link JQueryAjaxBehavior} that will be called on 'select' javascript method
	 * @param source {@link Component} to which the event returned by {@link #newEvent(AjaxRequestTarget)} will be broadcasted.
	 * @return the {@link JQueryAjaxBehavior}
	 */
	private JQueryAjaxBehavior newSelectBehavior()
	{
		return new JQueryAjaxBehavior(this) {

			private static final long serialVersionUID = 1L;

			@Override
			public String getCallbackFunction()
			{
				return "function(event, ui) { " + this.getCallbackScript() + " }";
			}

			@Override
			public CharSequence getCallbackScript()
			{
				return generateCallbackScript("wicketAjaxGet('" + getCallbackUrl() + "&index=' + ui.item.id");
			}

			@Override
			protected JQueryEvent newEvent(AjaxRequestTarget target)
			{
				return new SelectEvent(target);
			}
		};
	}


	// Event classes //
	/**
	 * Provides an event object that will be broadcasted by the {@link JQueryAjaxBehavior} select callback
	 *
	 */
	class SelectEvent extends JQueryEvent
	{
		private final int index;

		public SelectEvent(AjaxRequestTarget target)
		{
			super(target);

			this.index = RequestCycleUtils.getQueryParameterValue("index").toInt(1) - 1;
		}

		public int getIndex()
		{
			return this.index;
		}
	}
}
