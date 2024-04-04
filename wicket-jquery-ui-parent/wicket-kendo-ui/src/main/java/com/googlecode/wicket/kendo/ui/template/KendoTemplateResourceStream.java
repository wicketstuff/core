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
package com.googlecode.wicket.kendo.ui.template;

import com.googlecode.wicket.jquery.core.template.IJQueryTemplate;
import com.googlecode.wicket.jquery.core.template.JQueryTemplateResourceStream;

/**
 * Provides the resource stream for Kendo-UI templates.<br>
 * The {@link #getString()} method returns a script block like &lt;script id="jquery-template-123456" type="text/x-kendo-template" /&gt;
 * 
 * @author Sebastien Briquet - sebfz1
 */
public class KendoTemplateResourceStream extends JQueryTemplateResourceStream
{
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 * 
	 * @param content the content inside the &lt;script /&gt; block
	 * @param token the unique resource-stream token that will be the script-id.
	 */
	public KendoTemplateResourceStream(final String content, final String token)
	{
		super(content, token);
	}

	/**
	 * Constructor
	 * 
	 * @param template the {@link IJQueryTemplate}
	 * @param token the unique resource-stream token that will be the script-id.
	 */
	public KendoTemplateResourceStream(final IJQueryTemplate template, final String token)
	{
		super(template, token);
	}

	@Override
	public String getContentType()
	{
		return "text/x-kendo-template";
	}
}
