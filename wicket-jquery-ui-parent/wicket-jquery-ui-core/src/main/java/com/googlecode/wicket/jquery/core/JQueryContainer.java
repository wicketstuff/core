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
package com.googlecode.wicket.jquery.core;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;

/**
 * Base class for a JQuery {@link WebMarkupContainer}
 *
 * @author Sebastien Briquet - sebfz1
 *
 */
public abstract class JQueryContainer extends WebMarkupContainer implements IJQueryWidget
{
	private static final long serialVersionUID = 1L;

	/**
	 * keep a reference of the behavior, in case of special needs, ie: ProgressBar#refresh()
	 */
	protected JQueryBehavior widgetBehavior = null;

	/**
	 * Constructor.
	 * 
	 * @param id the markup id
	 */
	public JQueryContainer(String id)
	{
		super(id);
	}

	/**
	 * Constructor.
	 * 
	 * @param id the markup id
	 * @param model the model
	 */
	public JQueryContainer(String id, IModel<?> model)
	{
		super(id, model);
	}

	// Events //

	@Override
	protected void onInitialize()
	{
		super.onInitialize();

		this.widgetBehavior = JQueryWidget.newWidgetBehavior(this);
		this.add(this.widgetBehavior); // cannot be in ctor as the markupId may be set manually afterward
	}

	@Override
	public void onConfigure(JQueryBehavior behavior)
	{
	}

	@Override
	public void onBeforeRender(JQueryBehavior behavior)
	{
	}
}
