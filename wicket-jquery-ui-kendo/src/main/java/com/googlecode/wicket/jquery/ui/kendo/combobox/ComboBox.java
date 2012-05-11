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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.googlecode.wicket.jquery.ui.IJQueryWidget;
import com.googlecode.wicket.jquery.ui.JQueryBehavior;
import com.googlecode.wicket.jquery.ui.Options;
import com.googlecode.wicket.jquery.ui.kendo.KendoAbstractBehavior;

/**
 * Provides a Kendo UI ComboBox.<br/>
 * It should be created on a HTML &lt;input type="text" /&gt; element
 * 
 * @author Sebastien Briquet - sebastien@7thweb.net
 *
 * @param <T> the type of the model object
 */
public class ComboBox<T> extends TextField<String> implements IJQueryWidget
{
	private static final long serialVersionUID = 1L;
	private static final String METHOD = "kendoComboBox";
	
	private IModel<List<? extends T>> choices;
	private IComboBoxRenderer<? super T> renderer;
	private IComboBoxTemplate template;
	
	//TODO: do all constructors, to document
	
	/**
	 * @param id
	 */
	public ComboBox(String id, List<? extends T> choices)
	{
		this(id, Model.ofList(choices), null, new ComboBoxRenderer<T>());
	}

	public ComboBox(String id, List<? extends T> choices, IComboBoxTemplate template)
	{
		this(id, Model.ofList(choices), null, new ComboBoxRenderer<T>());
	}

	/**
	 * @param id
	 */
	public ComboBox(String id, IModel<List<? extends T>> choices)
	{
		this(id, choices, null, new ComboBoxRenderer<T>());
	}

	/**
	 * @param id
	 */
	public ComboBox(String id, IModel<List<? extends T>> choices, IComboBoxTemplate template)
	{
		this(id, choices, null, new ComboBoxRenderer<T>());
	}
	
	public ComboBox(String id, IModel<List<? extends T>> choices, IComboBoxTemplate template, IComboBoxRenderer<? super T> renderer)
	{
		super(id);
		
		this.choices = choices;
		this.template = template;
		this.renderer = renderer;
	}
	
	
	

	/**
	 * @param id
	 * @param choices
	 */
	public ComboBox(String id, IModel<String> model, List<? extends T> choices)
	{
		this(id, model, Model.ofList(choices), null, new ComboBoxRenderer<T>());
	}

	public ComboBox(String id, IModel<String> model, List<? extends T> choices, IComboBoxTemplate template)
	{
		this(id, model, Model.ofList(choices), template, new ComboBoxRenderer<T>());
	}

	/**
	 * @param id
	 * @param choices
	 */
	public ComboBox(String id, IModel<String> model, IModel<List<? extends T>> choices)
	{
		this(id, model, choices, null, new ComboBoxRenderer<T>());
	}

	public ComboBox(String id, IModel<String> model, IModel<List<? extends T>> choices, IComboBoxTemplate template)
	{
		this(id, model, choices, template, new ComboBoxRenderer<T>());
	}


	public ComboBox(String id, IModel<String> model, IModel<List<? extends T>> choices, IComboBoxTemplate template, IComboBoxRenderer<? super T> renderer)
	{
		super(id, model);
		
		this.choices = choices;
		this.template = template;
		this.renderer = renderer;
	}
	
	@Override
	protected void onInitialize()
	{
		super.onInitialize();
		
		this.add(JQueryWidget.newWidgetBehavior(this));
	}

	/**
	 * to document
	 * @param behavior
	 */
	protected void onConfigure(JQueryBehavior behavior)
	{
		behavior.setOption("dataTextField", Options.asString("text"));
		behavior.setOption("dataValueField", Options.asString("value"));

		// set template (if any) //
		Set<String> properties = new HashSet<String>(); //Arrays.asList(dataTextField, dataValueField)
		
		if (this.template != null)
		{
			properties.addAll(this.template.getProperties());
			behavior.setOption("template", Options.asString(this.template.getHtml()));
		}

		// set data source //
		if (this.renderer != null)
		{
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
					dataSource.append("value").append(": '").append(this.renderer.getValue(object, index)).append("'");
					dataSource.append(", ");
					dataSource.append("text").append(": '").append(this.renderer.getText(object)).append("'");
					
					for (String property : properties)
					{
						dataSource.append(", ");
						dataSource.append(property).append(": '").append(this.renderer.getText(object, property)).append("'");
					}

					dataSource.append(" }");
				}
			}

			dataSource.append("]");
			
			behavior.setOption("dataSource", dataSource.toString());
		}
		
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
}
