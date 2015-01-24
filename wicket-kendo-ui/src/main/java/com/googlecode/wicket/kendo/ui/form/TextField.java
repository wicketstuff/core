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
package com.googlecode.wicket.kendo.ui.form;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.model.IModel;

import com.googlecode.wicket.jquery.core.IJQueryWidget;

/**
 * Provides a {@link org.apache.wicket.markup.html.form.TextField} with the Kendo-ui style<br/>
 * <br/>
 * <b>Note:</b> {@link TextField} is not a {@link IJQueryWidget} (no corresponding widget is supplied by Kendo UI)<br/>
 * It means that required Kendo UI dependencies (javascript/stylesheet) are not automatically added. 
 *
 * @param <T> the model object type
 * @author Sebastien Briquet - sebfz1
 *
 */
public class TextField<T> extends org.apache.wicket.markup.html.form.TextField<T>
{
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 */
	public TextField(String id)
	{
		super(id);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param type the type for field validation
	 */
	public TextField(final String id, final Class<T> type)
	{
		super(id, type);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param model the {@link IModel}
	 */
	public TextField(String id, IModel<T> model)
	{
		super(id, model);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param model the {@link IModel}
	 * @param type the type for field validation
	 */
	public TextField(final String id, IModel<T> model, final Class<T> type)
	{
		super(id, model, type);
	}

	// Events //

	@Override
	protected void onComponentTag(ComponentTag tag)
	{
		super.onComponentTag(tag);

		tag.append("class", "k-textbox", " ");
		
		if (!this.isEnabledInHierarchy())
		{
			tag.append("class", "k-state-disabled", " ");
		}
	}
}
