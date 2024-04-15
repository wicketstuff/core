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
package com.googlecode.wicket.jquery.core.template;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.head.IHeaderResponse;

/**
 * Provides the base class for jQuery template behavior.<br>
 * A template behavior is designed to render a &lt;script id="jquery-template-123456" type="text/x-xxx" /&gt; block, coming from the JQueryResourceStream returned by {@link #newResourceStream()}
 *
 * @author Sebastien Briquet - sebfz1
 */
public abstract class JQueryAbstractTemplateBehavior extends Behavior
{
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 */
	public JQueryAbstractTemplateBehavior()
	{
	}

	@Override
	public void renderHead(Component component, IHeaderResponse response)
	{
		super.renderHead(component, response);

		response.render(new JQueryTemplateHeaderItem(this.newResourceStream()));
	}

	/**
	 * Get the unique resource-stream token that acts as the script id.
	 *
	 * @return the token
	 */
	public abstract String getToken();

	/**
	 * Gets a new {@link JQueryTemplateResourceStream} which contains the &lt;script /&gt; block.
	 *
	 * @return the {@link JQueryTemplateResourceStream}
	 */
	protected abstract JQueryTemplateResourceStream newResourceStream();
}
