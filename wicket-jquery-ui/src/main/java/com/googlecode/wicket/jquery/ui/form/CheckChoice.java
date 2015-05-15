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
package com.googlecode.wicket.jquery.ui.form;

import java.util.Collection;
import java.util.List;

import org.apache.wicket.markup.html.form.CheckBoxMultipleChoice;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;

import com.googlecode.wicket.jquery.core.IJQueryWidget;
import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.ui.JQueryUIBehavior;

/**
 * Provides jQuery check-buttons based on a {@link CheckBoxMultipleChoice}
 *
 * @param <T> the model object type
 * @author Sebastien Briquet - sebfz1
 */
public class CheckChoice<T> extends CheckBoxMultipleChoice<T> implements IJQueryWidget
{
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 * @param id the markup id
	 */
	public CheckChoice(String id)
	{
		super(id);
		this.initialize();
	}

	/**
	 * Constructor
	 * @param id the markup id
	 * @param choices the collection of choices in the check choice
	 */
	public CheckChoice(String id, List<? extends T> choices)
	{
		super(id, choices);
		this.initialize();
	}

	/**
	 * Constructor
	 * @param id the markup id
	 * @param choices the collection of choices in the check choice
	 * @param renderer the rendering engine
	 */
	public CheckChoice(String id, List<? extends T> choices, IChoiceRenderer<? super T> renderer)
	{
		super(id, choices, renderer);
		this.initialize();
	}

	/**
	 * Constructor
	 * @param id the markup id
	 * @param choices the collection of choices in the check choice
	 */
	public CheckChoice(String id, IModel<? extends List<? extends T>> choices)
	{
		super(id, choices);
		this.initialize();
	}

	/**
	 * Constructor
	 * @param id the markup id
	 * @param choices the collection of choices in the check choice
	 * @param renderer the rendering engine
	 */
	public CheckChoice(String id, IModel<? extends List<? extends T>> choices, IChoiceRenderer<? super T> renderer)
	{
		super(id, choices, renderer);
		this.initialize();
	}

	/**
	 * Constructor
	 * @param id the markup id
	 * @param model the {@link IModel}
	 * @param choices the collection of choices in the check choice
	 */
	public CheckChoice(String id, IModel<? extends Collection<T>> model, List<? extends T> choices)
	{
		super(id, model, choices);
		this.initialize();
	}

	/**
	 * Constructor
	 * @param id the markup id
	 * @param model the {@link IModel}
	 * @param choices the collection of choices in the check choice
	 */
	public CheckChoice(String id, IModel<? extends Collection<T>> model, IModel<? extends List<? extends T>> choices)
	{
		super(id, model, choices);
		this.initialize();
	}

	/**
	 * Constructor
	 * @param id the markup id
	 * @param model the {@link IModel}
	 * @param choices the collection of choices in the check choice
	 * @param renderer the rendering engine
	 */
	public CheckChoice(String id, IModel<? extends Collection<T>> model, List<? extends T> choices, IChoiceRenderer<? super T> renderer)
	{
		super(id, model, choices, renderer);
		this.initialize();
	}

	/**
	 * Constructor
	 * @param id the markup id
	 * @param model the {@link IModel}
	 * @param choices the collection of choices in the check choice
	 * @param renderer the rendering engine
	 */
	public CheckChoice(String id, IModel<? extends Collection<T>> model, IModel<? extends List<? extends T>> choices, IChoiceRenderer<? super T> renderer)
	{
		super(id, model, choices, renderer);
		this.initialize();
	}

	/**
	 * Initialization
	 */
	private void initialize()
	{
		this.setSuffix(""); // prevent the <br/> tag
	}

	// Events //
	@Override
	protected void onInitialize()
	{
		super.onInitialize();

		this.add(JQueryWidget.newWidgetBehavior(this)); //cannot be in ctor as the markupId may be set manually afterward
	}

	@Override
	public void onConfigure(JQueryBehavior behavior)
	{
		// noop
	}

	@Override
	public void onBeforeRender(JQueryBehavior behavior)
	{
		// noop
	}

	// IJQueryWidget //
	@Override
	public CheckChoiceBehavior newWidgetBehavior(String selector)
	{
		return new CheckChoiceBehavior(selector);
	}

	/**
	 * Provides jQuery check-buttons {@link JQueryBehavior}
	 */
	public static class CheckChoiceBehavior extends JQueryUIBehavior
	{
		private static final long serialVersionUID = 1L;
		public static final String METHOD = "buttonset";

		public CheckChoiceBehavior(String selector)
		{
			super(selector, METHOD);
		}

		public CheckChoiceBehavior(String selector, Options options)
		{
			super(selector, METHOD, options);
		}
	}
}
