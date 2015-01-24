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
package com.googlecode.wicket.jquery.ui.widget.tooltip;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.core.util.string.ComponentRenderer;
import org.apache.wicket.markup.html.WebMarkupContainer;

import com.googlecode.wicket.jquery.core.IJQueryWidget.JQueryWidget;
import com.googlecode.wicket.jquery.core.Options;

/**
 * Provides the jQuery tooltip behavior, with custom content.<br/>
 * <br/>
 * <b>Warning:</b> there is no selector supplied to the constructor, but it does not means that this behavior will
 * be applied to the <i>document</i>, like for the {@link TooltipBehavior}. The selector will be retrieved from the
 * component this behavior will be bound to, because this is a mandatory condition.
 *
 * @author Sebastien Briquet - sebfz1
 * @since 6.12.0
 */
public abstract class CustomTooltipBehavior extends TooltipBehavior
{
	private static final long serialVersionUID = 1L;

	/** The component id which will be used for the custom tooltip markup-container */
	private static final String CONTENT_ID = "tooltip";

	/**
	 * Constructor
	 */
	public CustomTooltipBehavior()
	{
		super();
	}

	/**
	 * Constructor
	 *
	 * @param options the {@link Options}
	 */
	public CustomTooltipBehavior(Options options)
	{
		super(options);
	}

	@Override
	public void bind(Component component)
	{
		super.bind(component);

		component.add(AttributeModifier.replace("data-tooltip", true));

		this.selector = JQueryWidget.getSelector(component);
		this.setOption("items", Options.asString("[data-tooltip]"));
		this.setOption("content", String.format("function() { return \"%s\"; }", this.render(this.newContent(CONTENT_ID))));
	}

	// Methods //

	/**
	 * Gets the {@link WebMarkupContainer} which will represent the tooltip content
	 *
	 * @param markupId the markup-id to be used
	 * @return a new {@link WebMarkupContainer}
	 */
	protected abstract WebMarkupContainer newContent(String markupId);

	/**
	 * Renders the supplied container using {@link ComponentRenderer}
	 *
	 * @param container the {@link WebMarkupContainer}
	 * @return the escaped string of the rendered container
	 * @see #escape(String)
	 */
	private String render(WebMarkupContainer container)
	{
		return this.escape(String.valueOf(ComponentRenderer.renderComponent(container)));
	}

	/**
	 * Escapes the supplied content<br/>
	 * This can be overridden to provide additional escaping
	 *
	 * @param content the content, likely html
	 * @return the escaped content
	 */
	protected String escape(String content)
	{
		return content.replace("\t", "").replace("\n", "").replace("\"", "'");
	}

	@Override
	public String $()
	{
		if (this.selector == null)
		{
			return CustomTooltipBehavior.$(this.method, this.options.toString());
		}

		return super.$();
	}

	/**
	 * Gets the jQuery statement for the <i>document</i> level tooltip
	 *
	 * @param method the jQuery method to invoke
	 * @param options the options to be applied
	 * @return the jQuery statement
	 */
	private static String $(String method, String options)
	{
		return String.format("jQuery(document).%s(%s);", method, options);
	}
}
