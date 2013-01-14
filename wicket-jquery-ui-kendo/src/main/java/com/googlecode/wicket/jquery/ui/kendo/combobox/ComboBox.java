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
package com.googlecode.wicket.jquery.ui.kendo.combobox;

import java.util.List;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.googlecode.wicket.jquery.ui.IJQueryWidget;
import com.googlecode.wicket.jquery.ui.JQueryBehavior;
import com.googlecode.wicket.jquery.ui.Options;
import com.googlecode.wicket.jquery.ui.kendo.KendoAbstractBehavior;
import com.googlecode.wicket.jquery.ui.kendo.KendoTemplateBehavior;
import com.googlecode.wicket.jquery.ui.template.IJQueryTemplate;

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
	private static final String METHOD = "kendoComboBox";

	private final IModel<List<? extends T>> choices;
	private final ComboBoxRenderer<? super T> renderer;
	private final IJQueryTemplate template;
	private KendoTemplateBehavior templateBehavior = null;

	/**
	 * Constructor
	 * @param id the markup id
	 * @param choices the list of choices
	 */
	public ComboBox(String id, List<? extends T> choices)
	{
		this(id, Model.ofList(choices), new ComboBoxRenderer<T>());
	}

	/**
	 * Constructor
	 * @param id the markup id
	 * @param choices the list of choices
	 * @param renderer the renderer to be used, so the renderer item text and its values can be dissociated
	 */
	public ComboBox(String id, List<? extends T> choices, ComboBoxRenderer<? super T> renderer)
	{
		this(id, Model.ofList(choices), renderer);
	}

	/**
	 * Constructor
	 * @param id the markup id
	 * @param choices the list model of choices
	 */
	public ComboBox(String id, IModel<List<? extends T>> choices)
	{
		this(id, choices, new ComboBoxRenderer<T>());
	}

	/**
	 * Constructor
	 * @param id the markup id
	 * @param choices the list model of choices
	 * @param renderer the renderer to be used, so the renderer item text and its values can be dissociated
	 */
	public ComboBox(String id, IModel<List<? extends T>> choices, ComboBoxRenderer<? super T> renderer)
	{
		super(id);

		this.choices = choices;
		this.renderer = renderer;
		this.template = this.newTemplate();
	}

	/**
	 * Constructor
	 * @param id the markup id
	 * @param model the {@link IModel}
	 * @param choices the list of choices
	 */
	public ComboBox(String id, IModel<String> model, List<? extends T> choices)
	{
		this(id, model, Model.ofList(choices), new ComboBoxRenderer<T>());
	}

	/**
	 * Constructor
	 * @param id the markup id
	 * @param model the {@link IModel}
	 * @param choices the list model of choices
	 * @param renderer the renderer to be used, so the renderer item text and its values can be dissociated
	 */
	public ComboBox(String id, IModel<String> model, List<? extends T> choices, ComboBoxRenderer<? super T> renderer)
	{
		this(id, model, Model.ofList(choices), renderer);
	}

	/**
	 * Constructor
	 * @param id the markup id
	 * @param model the {@link IModel}
	 * @param choices the list model of choices
	 */
	public ComboBox(String id, IModel<String> model, IModel<List<? extends T>> choices)
	{
		this(id, model, choices, new ComboBoxRenderer<T>());
	}

	/**
	 * Constructor
	 * @param id the markup id
	 * @param model the {@link IModel}
	 * @param choices the list model of choices
	 * @param renderer the renderer to be used, so the renderer item text and its values can be dissociated
	 */
	public ComboBox(String id, IModel<String> model, IModel<List<? extends T>> choices, ComboBoxRenderer<? super T> renderer)
	{
		super(id, model);

		this.choices = choices;
		this.renderer = renderer;
		this.template = this.newTemplate();
	}


	// Events //
	@Override
	protected void onInitialize()
	{
		super.onInitialize();

		this.add(JQueryWidget.newWidgetBehavior(this));

		if (this.template != null)
		{
			this.add(this.templateBehavior = new KendoTemplateBehavior(this.template));
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
		// set template (if any) //
		if (this.template != null)
		{
			behavior.setOption("template", String.format("jQuery('#%s').html()", this.templateBehavior.getToken()));
		}

		// set data source //
		behavior.setOption("dataTextField", Options.asString(this.renderer.getTextField()));
		behavior.setOption("dataValueField", Options.asString(this.renderer.getValueField()));

		StringBuilder dataSource = new StringBuilder("[");

		List<? extends T> list = this.choices.getObject();

		if (list != null)
		{
			for (int index = 0 ; index < list.size(); index++)
			{
				T object = list.get(index);

				if (index > 0)
				{
					dataSource.append(", ");
				}

				dataSource.append("{ ");
				dataSource.append(this.renderer.getTextField()).append(": '").append(this.renderer.getText(object)).append("'");
				dataSource.append(", ");
				dataSource.append(this.renderer.getValueField()).append(": '").append(this.renderer.getValue(object)).append("'");

				if (this.template != null)
				{
					for (String property : this.template.getTextProperties())
					{
						dataSource.append(", ");
						dataSource.append(property).append(": '").append(this.renderer.getText(object, property)).append("'");
					}
				}

				dataSource.append(" }");
			}
		}

		dataSource.append("]");

		behavior.setOption("dataSource", dataSource.toString());
	}

	// IJQueryWidget //
	@Override
	public JQueryBehavior newWidgetBehavior(String selector)
	{
		return new KendoAbstractBehavior(selector, ComboBox.METHOD) {

			private static final long serialVersionUID = 1L;

			@Override
			public void onConfigure(Component component)
			{
				ComboBox.this.onConfigure(this);
			}
		};
	}

	// Factories //
	/**
	 * Gets a new {@link IJQueryTemplate} to customize the rendering<br/>
	 * The properties used in the template text (ie: ${data.name}) should be of the prefixed by "data." and should be identified in the list returned by {@link IJQueryTemplate#getTextProperties()} (without "data.")
	 * @return null by default
	 */
	protected IJQueryTemplate newTemplate()
	{
		return null;
	}
}
