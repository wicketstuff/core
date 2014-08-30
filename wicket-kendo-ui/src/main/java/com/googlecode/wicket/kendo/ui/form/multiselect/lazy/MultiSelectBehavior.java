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
package com.googlecode.wicket.kendo.ui.form.multiselect.lazy;

import org.apache.wicket.Component;

import com.googlecode.wicket.kendo.ui.KendoUIBehavior;
import com.googlecode.wicket.kendo.ui.utils.DebugUtils;

/**
 * Provides a Kendo UI MultiSelect behavior
 *
 * @author Sebastien Briquet - sebfz1
 *
 */
public abstract class MultiSelectBehavior extends KendoUIBehavior
{
	private static final long serialVersionUID = 1L;
	private static final String METHOD = "kendoMultiSelect";

	public MultiSelectBehavior(String selector)
	{
		super(selector, METHOD);
	}

	// Events //

	@Override
	public void onConfigure(Component component)
	{
		super.onConfigure(component);

		this.setOption("autoBind", true); // immutable
		this.setOption("dataSource", this.newDataSource());
	}

	// Methods //

	protected abstract CharSequence getChoiceCallbackUrl();

	// Factories //

	protected String newDataSource()
	{
		return String.format("{ transport: { read: { url: '%s', dataType: 'json' } }, error: %s }", this.getChoiceCallbackUrl(), DebugUtils.errorCallback);
	}
}
