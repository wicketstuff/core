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
package com.googlecode.wicket.jquery.ui.kendo.splitter;

import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.ui.kendo.KendoAbstractBehavior;

/**
 * Provides a Kendo UI splitter behavior
 *
 * @author Sebastien Briquet - sebfz1
 *
 */
public class SplitterBehavior extends KendoAbstractBehavior
{
	private static final long serialVersionUID = 1L;
	private static final String METHOD = "kendoSplitter";

	/**
	 * Constructor
	 * @param selector the html selector (ie: "#myId")
	 */
	public SplitterBehavior(String selector)
	{
		this(selector, new Options());
	}

	/**
	 * Constructor
	 * @param selector the html selector (ie: "#myId")
	 * @param options the {@link Options}
	 */
	public SplitterBehavior(String selector, Options options)
	{
		super(selector, METHOD, options);
	}
}
