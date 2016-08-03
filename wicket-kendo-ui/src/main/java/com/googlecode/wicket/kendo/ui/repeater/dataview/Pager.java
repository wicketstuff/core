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
package com.googlecode.wicket.kendo.ui.repeater.dataview;

import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.core.JQueryContainer;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.kendo.ui.KendoDataSource;
import com.googlecode.wicket.kendo.ui.KendoUIBehavior;

/**
 * Provides a Kendo UI Pager
 *
 * @author Sebastien Briquet - sebfz1
 */
public class Pager extends JQueryContainer
{
	private static final long serialVersionUID = 1L;

	public static final String METHOD = "kendoPager";

	private final Options options;

	/**
	 * constructor
	 *
	 * @param id the markup id
	 * @param dataSource the {@link KendoDataSource}
	 */
	public Pager(String id, final KendoDataSource dataSource)
	{
		this(id, dataSource, new Options());
	}

	/**
	 * Main constructor
	 *
	 * @param id the markup id
	 * @param dataSource the {@link KendoDataSource}
	 * @param options the {@link Options}
	 */
	public Pager(String id, final KendoDataSource dataSource, Options options)
	{
		super(id);

		this.options = options;
		this.options.set("dataSource", dataSource.getName());
	}

	// Methods //

	/**
	 * Gets the Kendo UI widget
	 *
	 * @return the jQuery object
	 */
	public String widget()
	{
		return KendoUIBehavior.widget(this, Pager.METHOD);
	}

	// Events //

	@Override
	protected void onInitialize()
	{
		super.onInitialize();

		this.add(JQueryWidget.newWidgetBehavior(this)); // cannot be in ctor as the markupId may be set manually afterward
	}

	// IJQueryWidget //

	@Override
	public JQueryBehavior newWidgetBehavior(String selector)
	{
		return new KendoUIBehavior(selector, Pager.METHOD, this.options);
	}
}
