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
package com.googlecode.wicket.kendo.ui.form.button;

import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.kendo.ui.KendoUIBehavior;

/**
 * Provides a Kendo UI button {@link JQueryBehavior}
 *
 * @author Sebastien Briquet - sebfz1
 *
 */
//XXX: moved button.Button.ButtonBehavior to button.ButtonBehavior
public class ButtonBehavior extends KendoUIBehavior
{
	private static final long serialVersionUID = 1L;
	private static final String METHOD = "kendoButton";

	public ButtonBehavior(String selector)
	{
		super(selector, METHOD);
	}

	public ButtonBehavior(String selector, Options options)
	{
		super(selector, METHOD, options);
	}

	public ButtonBehavior(String selector, String icon)
	{
		super(selector, METHOD, new Options("icon", Options.asString(icon)));
	}
}
