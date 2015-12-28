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
package com.googlecode.wicket.kendo.ui.form.dropdown.lazy;

import org.apache.wicket.Component;

import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.kendo.ui.KendoDataSource;
import com.googlecode.wicket.kendo.ui.KendoUIBehavior;

/**
 * Provides a {@value #METHOD} behavior
 *
 * @author Sebastien Briquet - sebfz1
 *
 */
public abstract class DropDownListBehavior extends KendoUIBehavior
{
	private static final long serialVersionUID = 1L;
	public static final String METHOD = "kendoDropDownList";

	private KendoDataSource dataSource;

	public DropDownListBehavior(String selector)
	{
		super(selector, METHOD);
	}

	// Methods //

	@Override
	public void bind(Component component)
	{
		super.bind(component);

		// data source //
		this.dataSource = new KendoDataSource("datasource" + this.selector);
		this.add(this.dataSource);
	}

	// Properties //
	
	@Override
	public boolean isEnabled(Component component)
	{
		return component.isEnabledInHierarchy();
	}

	protected abstract CharSequence getChoiceCallbackUrl();

	// Events //

	@Override
	public void onConfigure(Component component)
	{
		super.onConfigure(component);

		this.setOption("autoBind", true); // immutable
		this.setOption("dataSource", this.dataSource.getName());

		// data source //
		if (this.isEnabled(component))
		{
			this.dataSource.setTransportRead(Options.asString(this.getChoiceCallbackUrl()));
		}
	}
}
