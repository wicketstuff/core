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
package com.googlecode.wicket.kendo.ui.form.combobox;

import java.util.List;

import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.googlecode.wicket.jquery.core.IJQueryWidget;
import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.core.behavior.ChoiceModelBehavior;
import com.googlecode.wicket.jquery.core.renderer.IChoiceRenderer;
import com.googlecode.wicket.jquery.core.template.IJQueryTemplate;
import com.googlecode.wicket.kendo.ui.renderer.ChoiceRenderer;
import com.googlecode.wicket.kendo.ui.template.KendoTemplateBehavior;

/**
 * Provides a Kendo UI ComboBox widget.<br/>
 * It should be created on a HTML &lt;input type="text" /&gt; element
 *
 * @author Sebastien Briquet - sebfz1
 *
 * @param <T> the type of the model object
 */
public class ComboBox<T> extends TextField<String> implements IJQueryWidget
{
	private static final long serialVersionUID = 1L;

	/** cache of current choices, needed to retrieve the user selected object */
	private final IModel<List<T>> choices;
	private ChoiceModelBehavior<T> choiceModelBehavior;

	/** the data-source renderer */
	private final IChoiceRenderer<? super T> renderer;

	/** the template */
	private final IJQueryTemplate template;
	private KendoTemplateBehavior templateBehavior = null;

	/** inner list width. 0 means that it will not be handled */
	private int width = 0;

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param choices the list of choices
	 */
	public ComboBox(String id, List<T> choices)
	{
		this(id, Model.ofList(choices), new ChoiceRenderer<T>());
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param choices the list of choices
	 * @param renderer the renderer to be used, so the renderer item text and its values can be dissociated
	 */
	public ComboBox(String id, List<T> choices, IChoiceRenderer<? super T> renderer)
	{
		this(id, Model.ofList(choices), renderer);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param choices the list model of choices
	 */
	public ComboBox(String id, IModel<List<T>> choices)
	{
		this(id, choices, new ChoiceRenderer<T>());
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param choices the list model of choices
	 * @param renderer the renderer to be used, so the renderer item text and its values can be dissociated
	 */
	public ComboBox(String id, IModel<List<T>> choices, IChoiceRenderer<? super T> renderer)
	{
		super(id);

		this.choices = choices;
		this.renderer = renderer;
		this.template = this.newTemplate();
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param model the {@link IModel}
	 * @param choices the list of choices
	 */
	public ComboBox(String id, IModel<String> model, List<T> choices)
	{
		this(id, model, Model.ofList(choices), new ChoiceRenderer<T>());
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param model the {@link IModel}
	 * @param choices the list model of choices
	 * @param renderer the renderer to be used, so the renderer item text and its values can be dissociated
	 */
	public ComboBox(String id, IModel<String> model, List<T> choices, IChoiceRenderer<? super T> renderer)
	{
		this(id, model, Model.ofList(choices), renderer);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param model the {@link IModel}
	 * @param choices the list model of choices
	 */
	public ComboBox(String id, IModel<String> model, IModel<List<T>> choices)
	{
		this(id, model, choices, new ChoiceRenderer<T>());
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param model the {@link IModel}
	 * @param choices the list model of choices
	 * @param renderer the renderer to be used, so the renderer item text and its values can be dissociated
	 */
	public ComboBox(String id, IModel<String> model, IModel<List<T>> choices, IChoiceRenderer<? super T> renderer)
	{
		super(id, model);

		this.choices = choices;
		this.renderer = renderer;
		this.template = this.newTemplate(); // TODO: move to onInitialize (check the other classes)
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
	public ComboBox<?> setListWidth(int width)
	{
		this.width = width;

		return this;
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
	public void onConfigure(JQueryBehavior behavior)
	{
		behavior.setOption("dataTextField", Options.asString(this.renderer.getTextField()));
		behavior.setOption("dataValueField", Options.asString(this.renderer.getValueField()));

		// set template (if any) //
		if (this.templateBehavior != null)
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

	// IJQueryWidget //

	@Override
	public JQueryBehavior newWidgetBehavior(String selector)
	{
		return new ComboBoxBehavior(selector) {

			private static final long serialVersionUID = 1L;

			@Override
			protected CharSequence getDataSourceUrl()
			{
				return ComboBox.this.getCallbackUrl();
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
	 * @return a new {@code ChoiceModelBehavior}
	 */
	protected ChoiceModelBehavior<T> newChoiceModelBehavior()
	{
		return new ChoiceModelBehavior<T>(this.renderer, this.template) {

			private static final long serialVersionUID = 1L;

			@Override
			public List<T> getChoices()
			{
				return ComboBox.this.choices.getObject();
			}
		};
	}
}
