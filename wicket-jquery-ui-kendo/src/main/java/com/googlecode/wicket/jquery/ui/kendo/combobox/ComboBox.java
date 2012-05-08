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

import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;

import com.googlecode.wicket.jquery.ui.IJQueryWidget;
import com.googlecode.wicket.jquery.ui.JQueryBehavior;
import com.googlecode.wicket.jquery.ui.kendo.KendoAbstractBehavior;

public class ComboBox<T> extends DropDownChoice<T> implements IJQueryWidget
{
	private static final long serialVersionUID = 1L;
	private static final String METHOD = "kendoComboBox";

	/**
	 * @param id
	 */
	public ComboBox(String id)
	{
		super(id);
	}

	/**
	 * @param id
	 * @param choices
	 */
	public ComboBox(String id, List<? extends T> choices)
	{
		super(id, choices);
	}

	/**
	 * @param id
	 * @param choices
	 * @param renderer
	 */
	public ComboBox(String id, List<? extends T> choices, IChoiceRenderer<? super T> renderer)
	{
		super(id, choices, renderer);
	}

	/**
	 * @param id
	 * @param model
	 * @param choices
	 */
	public ComboBox(String id, IModel<T> model, List<? extends T> choices)
	{
		super(id, model, choices);
	}

	/**
	 * @param id
	 * @param model
	 * @param choices
	 * @param renderer
	 */
	public ComboBox(String id, IModel<T> model, List<? extends T> choices, IChoiceRenderer<? super T> renderer)
	{
		super(id, model, choices, renderer);
	}

	/**
	 * @param id
	 * @param choices
	 */
	public ComboBox(String id, IModel<? extends List<? extends T>> choices)
	{
		super(id, choices);
	}

	/**
	 * @param id
	 * @param model
	 * @param choices
	 */
	public ComboBox(String id, IModel<T> model, IModel<? extends List<? extends T>> choices)
	{
		super(id, model, choices);
	}

	/**
	 * @param id
	 * @param choices
	 * @param renderer
	 */
	public ComboBox(String id, IModel<? extends List<? extends T>> choices, IChoiceRenderer<? super T> renderer)
	{
		super(id, choices, renderer);
	}

	/**
	 * @param id
	 * @param model
	 * @param choices
	 * @param renderer
	 */
	public ComboBox(String id, IModel<T> model, IModel<? extends List<? extends T>> choices, IChoiceRenderer<? super T> renderer)
	{
		super(id, model, choices, renderer);
	}
	
	@Override
	protected void onInitialize()
	{
		super.onInitialize();
		
		this.add(JQueryWidget.newWidgetBehavior(this));
	}

	// IJQueryWidget //
	@Override
	public JQueryBehavior newWidgetBehavior(String selector)
	{
		return new KendoAbstractBehavior(selector, ComboBox.METHOD);
	}
}
