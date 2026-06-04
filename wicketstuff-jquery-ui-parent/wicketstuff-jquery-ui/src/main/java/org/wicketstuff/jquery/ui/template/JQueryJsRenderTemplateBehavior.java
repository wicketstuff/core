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
package org.wicketstuff.jquery.ui.template;

import org.apache.wicket.Component;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.PriorityHeaderItem;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.apache.wicket.resource.JQueryPluginResourceReference;
import org.wicketstuff.jquery.core.template.IJQueryTemplate;
import org.wicketstuff.jquery.core.template.JQueryAbstractTemplateBehavior;
import org.wicketstuff.jquery.core.template.JQueryTemplateResourceStream;

/**
 * Templates machinery based on <a href="https://github.com/BorisMoore/jsrender">jsrender</a>
 *
 * @author reiern70
 */
public class JQueryJsRenderTemplateBehavior extends JQueryAbstractTemplateBehavior
{
	private static final long serialVersionUID = 1L;
	public static final PackageResourceReference TMPL_JS = new JQueryPluginResourceReference(JQueryJsRenderTemplateBehavior.class, "jsrender.min.js");

	private String token = null;
	private final IJQueryTemplate template;

	/**
	 * Constructor
	 *
	 * @param template the {@link IJQueryTemplate} that this behavior should render via the resource stream
	 */
	public JQueryJsRenderTemplateBehavior(IJQueryTemplate template)
	{
		super();

		this.template = template;
	}

	@Override
	public void renderHead(Component component, IHeaderResponse response)
	{
		super.renderHead(component, response);

		response.render(new PriorityHeaderItem(JavaScriptHeaderItem.forReference(JQueryJsRenderTemplateBehavior.TMPL_JS)));
	}

	// Methods //

	@Override
	public void bind(Component component)
	{
		super.bind(component);

		this.token = String.format("%s-template", component.getMarkupId());
	}

	// Properties //

	@Override
	public String getToken()
	{
		return this.token;
	}

	// Factories //

	@Override
	protected JQueryTemplateResourceStream newResourceStream()
	{
		return new JQueryTemplateResourceStream(this.template.getText(), this.getToken());
	}

	@Override
	public String getTemplateRenderingCode()
	{
		return "jQuery.templates(jQuery('#%s').html()).render(item);";
	}
}
