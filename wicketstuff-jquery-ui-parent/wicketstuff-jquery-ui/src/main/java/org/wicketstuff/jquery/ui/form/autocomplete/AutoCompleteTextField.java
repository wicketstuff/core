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
package org.wicketstuff.jquery.ui.form.autocomplete;

import java.io.Serializable;
import java.util.List;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.wicketstuff.jquery.core.renderer.ITextRenderer;
import org.wicketstuff.jquery.core.renderer.TextRenderer;

/**
 * Provides a jQuery auto-complete widget
 *
 * @param <T> the type of the model object
 * @author Sebastien Briquet - sebfz1
 */
public abstract class AutoCompleteTextField<T extends Serializable> extends AbstractAutoCompleteTextField<T> // NOSONAR
{
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 */
	public AutoCompleteTextField(String id)
	{
		this(id, new TextRenderer<T>(), null);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param type the type of the bean. This parameter should be supplied for the internal converter ({@link #getConverter(Class)}) to be used.
	 */
	public AutoCompleteTextField(String id, Class<T> type)
	{
		this(id, new TextRenderer<T>(), type);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param renderer the {@link ITextRenderer}
	 */
	public AutoCompleteTextField(String id, ITextRenderer<? super T> renderer)
	{
		this(id, renderer, null);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param renderer the {@link ITextRenderer}
	 * @param type the type of the bean. This parameter should be supplied for the internal converter ({@link #getConverter(Class)}) to be used.
	 */
	public AutoCompleteTextField(String id, ITextRenderer<? super T> renderer, Class<T> type)
	{
		super(id, renderer, type);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param model the {@link IModel}
	 */
	public AutoCompleteTextField(String id, IModel<T> model)
	{
		this(id, model, new TextRenderer<T>(), null);
	}

	/**
	 * Constructor
	 *
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
	 *
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
	 *
	 * @param id the markup id
	 * @param model the {@link IModel}
	 * @param renderer the {@link ITextRenderer}
	 * @param type the type of the bean. This parameter should be supplied for the internal converter ({@link #getConverter(Class)}) to be used.
	 */
	public AutoCompleteTextField(String id, IModel<T> model, ITextRenderer<? super T> renderer, Class<T> type)
	{
		super(id, model, renderer, type);
	}

	// Methods //


	@Override
	protected final IModel<List<T>> getChoicesModel(String input) {
		return Model.ofList(getChoices(input));
	}

	/**
	 * Gets choices matching the provided input
	 *
	 * @param input String that represent the query
	 * @return the list of choices
	 */
	protected abstract List<T> getChoices(String input);
}
