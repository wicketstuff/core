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

import com.googlecode.wicket.jquery.core.template.IJQueryTemplate;
import com.googlecode.wicket.jquery.core.template.JQueryAbstractTemplateBehavior;
import com.googlecode.wicket.jquery.core.template.JQueryResourceStream;

/**
 * Provides the kendo-ui implementation of {@link JQueryAbstractTemplateBehavior} that works with a {@link IJQueryTemplate}.<br/>
 * The content of the &lt;script /&gt; block (the resource stream) is given by the {@link IJQueryTemplate#getText()}.<br/>
 * <br/>
 * This implementation is different from JQueryTemplateBehavior as the &lt;script /&gt; block will have the kendo-ui type ("text/x-kendo-template")
 * 
 * @author Sebastien Briquet - sebfz1
 *
 */
public class KendoTemplateBehavior extends JQueryAbstractTemplateBehavior
{
	private static final long serialVersionUID = 1L;

	private final IJQueryTemplate template;

	/**
	 * Constructor
	 * @param template the {@link IJQueryTemplate} that this behavior should render via the resource stream
	 */
	public KendoTemplateBehavior(IJQueryTemplate template)
	{
		this.template = template;
	}

	@Override
	protected JQueryResourceStream newResourceStream()
	{
		return new KendoResourceStream(this.template.getText(), this.getToken());
	}
}
