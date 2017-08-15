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

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.HeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.request.IRequestHandler;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.handler.resource.ResourceReferenceRequestHandler;

import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.kendo.ui.KendoIcon;

/**
 * Provides a Kendo UI button {@link JQueryBehavior} with an ajax indicator
 *
 * @author Sebastien Briquet - sebfz1
 *
 */
public class AjaxIndicatingButtonBehavior extends ButtonBehavior
{
	private static final long serialVersionUID = 1L;

	protected static final String CSS_INDICATOR = "indicator";

	public AjaxIndicatingButtonBehavior(String selector)
	{
		super(selector);
	}

	public AjaxIndicatingButtonBehavior(String selector, Options options)
	{
		super(selector, options);
	}

	public AjaxIndicatingButtonBehavior(String selector, String icon)
	{
		super(selector, icon);
	}

	// Methods //

	@Override
	public void renderHead(Component component, IHeaderResponse response)
	{
		super.renderHead(component, response);

		response.render(newIndicatorCssHeaderItem());
	}

	@Override
	protected String $()
	{
		StringBuilder builder = new StringBuilder(super.$());

		// busy indicator starts //
		// caution: in specific cases, #getSelector may return a different selector that this.selector
		builder.append("jQuery('").append(this.getSelector()).append("')").append(".click(function() { ");
		builder.append($(this.newOnClickOptions()));
		builder.append(" }); ");

		builder.append("jQuery(document).ajaxStart(function() { ");
		builder.append($(this.newAjaxStartOptions())); //TODO WIP
		builder.append(" }); ");

		// busy indicator stops //
		builder.append("jQuery(document).ajaxStop(function() { ");
		builder.append("jQuery('").append(this.selector).append(" .").append(KendoIcon.K_ICON).append("').remove(); "); // TODO: open issue (icon should be removed manually!)
		builder.append("jQuery('").append(this.selector).append("').removeAttr('disabled'); "); // TODO: open issue ({enable: true} does not remove disabled attr!)
		builder.append($(this.options));
		builder.append(" }); ");

		return builder.toString();
	}

	// Factories //

	/**
	 * Build the {@link CssHeaderItem} with the indicator style
	 *
	 * @return the {@link HeaderItem}
	 */
	private static HeaderItem newIndicatorCssHeaderItem()
	{
		IRequestHandler handler = new ResourceReferenceRequestHandler(AbstractDefaultAjaxBehavior.INDICATOR);
		String css = String.format(".k-i-%s { background-image: url(%s); background-position: 0 0; }", CSS_INDICATOR, RequestCycle.get().urlFor(handler));

		return CssHeaderItem.forCSS(css, "kendo-ui-icon-indicator");
	}

	/**
	 * Gets the new {@link Button}'s {@link Options} to be used on click
	 *
	 * @return the {@link Options}
	 */
	protected Options newOnClickOptions()
	{
		return new Options("icon", Options.asString(CSS_INDICATOR));
	}

	/**
	 * Gets the new {@link Button}'s {@link Options} to be used on ajax-start
	 *
	 * @return the {@link Options}
	 */
	protected Options newAjaxStartOptions()
	{
		return new Options();
	}
}
