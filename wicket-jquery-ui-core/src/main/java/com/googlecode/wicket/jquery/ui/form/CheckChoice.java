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

import com.googlecode.wicket.jquery.ui.IJQueryWidget;
import com.googlecode.wicket.jquery.ui.JQueryBehavior;

/**
 * Provides jQuery check-buttons based on a {@link CheckBoxMultipleChoice}
 *
 * @param <T> the model object type
 * @author Sebastien Briquet - sebfz1
 */
public class CheckChoice<T> extends CheckBoxMultipleChoice<T> implements IJQueryWidget
{
	private static final long serialVersionUID = 1L;
	private static final String METHOD = "buttonset";

	/**
	 * Constructor
	 * @param id the markup id
	 */
	public CheckChoice(String id)
	{
		super(id);
		this.init();
	}

	/**
	 * Constructor
	 * @param id the markup id
	 * @param choices the collection of choices in the check choice
	 */
	public CheckChoice(String id, List<? extends T> choices)
	{
		super(id, choices);
		this.init();
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
		this.init();
	}

	/**
	 * Constructor
	 * @param id the markup id
	 * @param choices the collection of choices in the check choice
	 */
	public CheckChoice(String id, IModel<? extends List<? extends T>> choices)
	{
		super(id, choices);
		this.init();
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
		this.init();
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
		this.init();
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
		this.init();
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
		this.init();
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
		this.init();
	}

	/**
	 * Initialization
	 */
	private void init()
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

	// IJQueryWidget //
	@Override
	public JQueryBehavior newWidgetBehavior(String selector)
	{
		return new JQueryBehavior(selector, METHOD);
	}
}
