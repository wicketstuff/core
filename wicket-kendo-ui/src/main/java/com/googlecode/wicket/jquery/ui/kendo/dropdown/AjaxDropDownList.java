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

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;

import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.core.JQueryEvent;
import com.googlecode.wicket.jquery.core.ajax.IJQueryAjaxAware;
import com.googlecode.wicket.jquery.core.ajax.JQueryAjaxBehavior;
import com.googlecode.wicket.jquery.core.event.ISelectionChangedListener;
import com.googlecode.wicket.jquery.core.event.JQueryAjaxChangeBehavior;
import com.googlecode.wicket.jquery.core.event.JQueryAjaxChangeBehavior.ChangeEvent;
import com.googlecode.wicket.jquery.ui.kendo.KendoAbstractBehavior;

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
	public void onSelectionChanged(AjaxRequestTarget target)
	{
	}

	@Override
	public JQueryBehavior newWidgetBehavior(String selector)
	{
		return new AjaxDropDownListBehavior(selector, DropDownList.METHOD) {

			private static final long serialVersionUID = 1L;

			@Override
			public void onConfigure(Component component)
			{
				super.onConfigure(component);

				AjaxDropDownList.this.onConfigure(this);
			}

			@Override
			public void onSelectionChanged(AjaxRequestTarget target)
			{
				AjaxDropDownList.this.onSelectionChanged();
				AjaxDropDownList.this.onSelectionChanged(target);
			}
		};
	}


	/**
	 * TODO javadoc
	 */
	protected static abstract class AjaxDropDownListBehavior extends KendoAbstractBehavior implements IJQueryAjaxAware, ISelectionChangedListener
	{
		private static final long serialVersionUID = 1L;

		private JQueryAjaxBehavior onChangeBehavior;

		public AjaxDropDownListBehavior(String selector, String method)
		{
			super(selector, method);
		}

		@Override
		public void bind(Component component)
		{
			super.bind(component);

			component.add(this.onChangeBehavior = new JQueryAjaxChangeBehavior(this));
		}

		@Override
		public void onConfigure(Component component)
		{
			super.onConfigure(component);

			this.setOption("change", "function( event, ui ) { " + this.onChangeBehavior.getCallbackScript() + "}");
		}

		@Override
		public void onAjax(AjaxRequestTarget target, JQueryEvent event)
		{
			if (event instanceof ChangeEvent)
			{
				this.onSelectionChanged(target);
			}
		}
	}
}
