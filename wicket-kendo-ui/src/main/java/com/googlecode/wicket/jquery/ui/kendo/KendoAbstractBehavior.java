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
package com.googlecode.wicket.jquery.ui.kendo;

import org.apache.wicket.request.resource.JavaScriptResourceReference;

import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.core.Options;

/**
 * Provides the base class for Kendo UI behavior implementations
 *
 * @author Sebastien Briquet - sebfz1
 * XXX: report as changed: jquery-ui-kendo > wicket-kendo-ui
 */
public class KendoAbstractBehavior extends JQueryBehavior
{
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 * @param selector the html selector (ie: "#myId")
	 * @param method the kendo-ui method
	 */
	public KendoAbstractBehavior(String selector, String method)
	{
		this(selector, method, new Options());
	}

	/**
	 * Constructor
	 * @param selector the html selector (ie: "#myId")
	 * @param method the kendo-ui method
	 * @param options the {@link Options}
	 */
	public KendoAbstractBehavior(String selector, String method, Options options)
	{
		super(selector, method, options);
//TODO: KendoUIResourceReference.get()
		this.add(new JavaScriptResourceReference(KendoAbstractBehavior.class, "kendo.web.min.js"));
	}

}
