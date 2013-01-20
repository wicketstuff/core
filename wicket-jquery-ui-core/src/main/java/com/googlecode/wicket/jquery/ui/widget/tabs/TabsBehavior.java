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
package com.googlecode.wicket.jquery.ui.widget.tabs;

import org.apache.wicket.ajax.AjaxRequestTarget;

import com.googlecode.wicket.jquery.ui.JQueryBehavior;
import com.googlecode.wicket.jquery.ui.Options;

/**
 * Provides a jQuery tabs behavior.
 *
 * @author Sebastien Briquet - sebfz1
 * @since 1.2.1
 */
public class TabsBehavior extends JQueryBehavior
{
	private static final long serialVersionUID = 1L;
	private static final String METHOD = "tabs";

	/**
	 * Constructor
	 *
	 * @param selector the html selector (ie: "#myId")
	 */
	public TabsBehavior(String selector)
	{
		super(selector, METHOD);
	}

	/**
	 * Constructor
	 *
	 * @param selector the html selector (ie: "#myId")
	 * @param options the {@link Options}
	 */
	public TabsBehavior(String selector, Options options)
	{
		super(selector, METHOD, options);
	}


	/**
	 * Activates the selected tab, identified by the index
	 * @param target the {@link AjaxRequestTarget}
	 * @param index the tab's index
	 */
	public void activate(int index, AjaxRequestTarget target)
	{
		target.appendJavaScript(this.$("'option'", "'active'", index));
	}
}
