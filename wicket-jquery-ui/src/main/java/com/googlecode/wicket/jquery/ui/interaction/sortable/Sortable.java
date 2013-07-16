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
package com.googlecode.wicket.jquery.ui.interaction.sortable;

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.util.ListModel;

import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.core.JQueryContainer;

/**
 * Provides a jQuery UI sortable {@link JQueryContainer}.<br/>
 * The <tt>Sortable</tt> is usually associated to an &lt;UL&gt; element.
 *
 * @param <T> the type of the model object
 * @author Sebastien Briquet - sebfz1
 *
 */
public abstract class Sortable<T> extends JQueryContainer implements ISortableListener
{
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 * @param id the markup id
	 * @param list the list the {@link Sortable} should observe.
	 */
	public Sortable(String id, List<T> list)
	{
		this(id, new ListModel<T>(list));
	}

	/**
	 * Constructor
	 * @param id the markup id
	 * @param model the list the {@link Sortable} should observe.
	 */
	public Sortable(String id, IModel<List<T>> model)
	{
		super(id, model);
	}

	// Properties //
	/**
	 * Gets the {@link IModel}
	 * @return the {@link IModel}
	 */
	@SuppressWarnings("unchecked")
	public IModel<List<T>> getModel()
	{
		return (IModel<List<T>>) this.getDefaultModel();
	}

	/**
	 * Gets the {@link IModel}
	 * @return the {@link IModel}
	 */
	@SuppressWarnings("unchecked")
	public List<T> getModelObject()
	{
		return (List<T>) this.getDefaultModelObject();
	}


	// Events //
	@Override
	public void onConfigure(JQueryBehavior behavior)
	{
		//WIP
	}


	// IJQueryWidget //
	@Override
	public JQueryBehavior newWidgetBehavior(String selector)
	{
		return new SortableBehavior(selector) {

			private static final long serialVersionUID = 1L;

			@Override
			public void onSort(AjaxRequestTarget target, int index, int position)
			{
				Sortable.this.onSort(target, index, position);
			}
		};
	}
}