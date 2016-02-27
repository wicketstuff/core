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
package com.googlecode.wicket.kendo.ui.form.dropdown;

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;

import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.core.event.ISelectionChangedListener;
import com.googlecode.wicket.kendo.ui.ajax.OnChangeAjaxBehavior;

/**
 * Provides a Kendo UI DropDownList widget.<br/>
 * This ajax version will post the component, using a {@link OnChangeAjaxBehavior}, when the 'change' javascript method is called.
 *
 * @author Sebastien Briquet - sebfz1
 *
 * @param <T>
 */
public class AjaxDropDownList<T> extends DropDownList<T> implements ISelectionChangedListener
{
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 */
	public AjaxDropDownList(String id)
	{
		super(id);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param choices the list of choices
	 */
	public AjaxDropDownList(String id, List<? extends T> choices)
	{
		super(id, choices);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param choices the list of choices
	 * @param renderer the rendering engine
	 */
	public AjaxDropDownList(String id, List<? extends T> choices, IChoiceRenderer<? super T> renderer)
	{
		super(id, choices, renderer);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param model the {@link IModel}
	 * @param choices the list of choices
	 */
	public AjaxDropDownList(String id, IModel<T> model, List<? extends T> choices)
	{
		super(id, model, choices);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param model the {@link IModel}
	 * @param choices the list of choices
	 * @param renderer the rendering engine
	 */
	public AjaxDropDownList(String id, IModel<T> model, List<? extends T> choices, IChoiceRenderer<? super T> renderer)
	{
		super(id, model, choices, renderer);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param choices the list of choices
	 */
	public AjaxDropDownList(String id, IModel<? extends List<? extends T>> choices)
	{
		super(id, choices);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param model the {@link IModel}
	 * @param choices the list of choices
	 */
	public AjaxDropDownList(String id, IModel<T> model, IModel<? extends List<? extends T>> choices)
	{
		super(id, model, choices);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param choices the list of choices
	 * @param renderer the rendering engine
	 */
	public AjaxDropDownList(String id, IModel<? extends List<? extends T>> choices, IChoiceRenderer<? super T> renderer)
	{
		super(id, choices, renderer);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param model the {@link IModel}
	 * @param choices the list of choices
	 * @param renderer the rendering engine
	 */
	public AjaxDropDownList(String id, IModel<T> model, IModel<? extends List<? extends T>> choices, IChoiceRenderer<? super T> renderer)
	{
		super(id, model, choices, renderer);
	}
	
	// Properties //
	
	@Override
	public boolean isSelectionChangedEventEnabled()
	{
		return true;
	}

	// Events //

	@Override
	public void onSelectionChanged(AjaxRequestTarget target)
	{
		// noop
	}

	@Override
	public final JQueryBehavior newWidgetBehavior(String selector)
	{
		return new DropDownListBehavior(selector, new SelectionChangedListenerWrapper(this) {

			private static final long serialVersionUID = 1L;

			@Override
			public void onSelectionChanged(AjaxRequestTarget target)
			{
				AjaxDropDownList.this.onSelectionChanged(); // updates the model
				AjaxDropDownList.this.onSelectionChanged(target);
			}
		});
	}
}
