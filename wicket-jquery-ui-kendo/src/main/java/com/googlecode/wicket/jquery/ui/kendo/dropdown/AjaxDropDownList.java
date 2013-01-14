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
package com.googlecode.wicket.jquery.ui.kendo.dropdown;

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;

import com.googlecode.wicket.jquery.ui.JQueryBehavior;
import com.googlecode.wicket.jquery.ui.ajax.JQueryAjaxBehavior;
import com.googlecode.wicket.jquery.ui.event.ISelectionChangedListener;
import com.googlecode.wicket.jquery.ui.event.JQueryAjaxChangeBehavior;
import com.googlecode.wicket.jquery.ui.event.JQueryAjaxChangeBehavior.ChangeEvent;

/**
 * Provides a Kendo UI DropDownList widget.<br/>
 * This ajax version will post the component, using a {@link JQueryAjaxChangeBehavior}, when the 'change' javascript method is called.
 *
 * @author Sebastien Briquet - sebfz1
 *
 * @param <T>
 */
public class AjaxDropDownList<T> extends DropDownList<T> implements ISelectionChangedListener
{
	private static final long serialVersionUID = 1L;

	private JQueryAjaxBehavior changeBehavior;

	/**
	 * Constructor
	 * @param id the markup id
	 */
	public AjaxDropDownList(String id)
	{
		super(id);
	}

	/**
	 * Constructor
	 * @param id the markup id
	 * @param choices the collection of choices in the dropdown
	 */
	public AjaxDropDownList(String id, List<? extends T> choices)
	{
		super(id, choices);
	}

	/**
	 * Constructor
	 * @param id the markup id
	 * @param choices the collection of choices in the dropdown
	 * @param renderer the rendering engine
	 */
	public AjaxDropDownList(String id, List<? extends T> choices, IChoiceRenderer<? super T> renderer)
	{
		super(id, choices, renderer);
	}

	/**
	 * Constructor
	 * @param id the markup id
	 * @param model the {@link IModel}
	 * @param choices the collection of choices in the dropdown
	 */
	public AjaxDropDownList(String id, IModel<T> model, List<? extends T> choices)
	{
		super(id, model, choices);
	}

	/**
	 * Constructor
	 * @param id the markup id
	 * @param model the {@link IModel}
	 * @param choices the collection of choices in the dropdown
	 * @param renderer the rendering engine
	 */
	public AjaxDropDownList(String id, IModel<T> model, List<? extends T> choices, IChoiceRenderer<? super T> renderer)
	{
		super(id, model, choices, renderer);
	}

	/**
	 * Constructor
	 * @param id the markup id
	 * @param choices the collection of choices in the dropdown
	 */
	public AjaxDropDownList(String id, IModel<? extends List<? extends T>> choices)
	{
		super(id, choices);
	}

	/**
	 * Constructor
	 * @param id the markup id
	 * @param model the {@link IModel}
	 * @param choices the collection of choices in the dropdown
	 */
	public AjaxDropDownList(String id, IModel<T> model, IModel<? extends List<? extends T>> choices)
	{
		super(id, model, choices);
	}

	/**
	 * Constructor
	 * @param id the markup id
	 * @param choices the collection of choices in the dropdown
	 * @param renderer the rendering engine
	 */
	public AjaxDropDownList(String id, IModel<? extends List<? extends T>> choices, IChoiceRenderer<? super T> renderer)
	{
		super(id, choices, renderer);
	}

	/**
	 * Constructor
	 * @param id the markup id
	 * @param model the {@link IModel}
	 * @param choices the collection of choices in the dropdown
	 * @param renderer the rendering engine
	 */
	public AjaxDropDownList(String id, IModel<T> model, IModel<? extends List<? extends T>> choices, IChoiceRenderer<? super T> renderer)
	{
		super(id, model, choices, renderer);
	}

	// Events //
	@Override
	protected void onInitialize()
	{
		super.onInitialize();

		this.add(this.changeBehavior = new JQueryAjaxChangeBehavior(this));
	}

	@Override
	protected void onConfigure(JQueryBehavior behavior)
	{
		super.onConfigure(behavior);

		behavior.setOption("change", "function( event, ui ) { " + this.changeBehavior.getCallbackScript() + "}");
	}

	@Override
	public void onEvent(IEvent<?> event)
	{
		if (event.getPayload() instanceof ChangeEvent)
		{
			ChangeEvent payload = (ChangeEvent) event.getPayload();

			this.onSelectionChanged();
			this.onSelectionChanged(payload.getTarget(), this.getForm());
		}
	}

	@Override
	public void onSelectionChanged(AjaxRequestTarget target, Form<?> form)
	{
	}
}
