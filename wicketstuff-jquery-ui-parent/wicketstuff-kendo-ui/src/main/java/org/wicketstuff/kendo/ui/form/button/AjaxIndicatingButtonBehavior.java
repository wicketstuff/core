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
package org.wicketstuff.kendo.ui.form.button;

import org.apache.wicket.Component;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.HeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.wicketstuff.jquery.core.JQueryBehavior;
import org.wicketstuff.jquery.core.Options;
import org.wicketstuff.jquery.core.utils.RequestCycleUtils;

/**
 * Provides a Kendo UI button {@link JQueryBehavior} with an ajax indicator
 *
 * @author Sebastien Briquet - sebfz1
 *
 */
public class AjaxIndicatingButtonBehavior extends ButtonBehavior
{
	private static final long serialVersionUID = 1L;

	protected static final String CSS_INDICATOR = "wkui-indicator";

	private IIndicatingButton button = null;

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
	public void bind(Component component)
	{
		super.bind(component);

		if (component instanceof IIndicatingButton)
		{
			this.button = (IIndicatingButton) component;
		}
	}

	@Override
	protected String $()
	{
		final StringBuilder builder = new StringBuilder(super.$());
		final String selector = this.getSelector();

		// caution: in specific cases, #getSelector may return a different selector that this.selector
		builder.append("jQuery('").append(selector).append("')").append(".click(function() { ");
		builder.append($(this.newOnClickOptions()));
		builder.append(" }); ");

		// busy indicator starts //
		builder.append("jQuery(document).ajaxStart(function() { ");
		builder.append($(this.newAjaxStartOptions()));
		builder.append(" }); ");

		// busy indicator stops //
		builder.append("jQuery(document).ajaxStop(function() { ");
		builder.append("jQuery('").append(selector).append("').removeAttr('disabled'); "); // TODO: open issue ({enable: true} does not remove disabled attr!)
		builder.append($(this.options)); // reinitialize button with original options (without indicator)
		builder.append("jQuery('").append(selector).append(" .").append(CSS_INDICATOR).append("').remove(); "); // remove indicator icon if it still exists after reinitialization
		builder.append(" }); ");
		
		return builder.toString();
	}

	// Events //

	@Override
	public void onConfigure(Component component)
	{
		super.onConfigure(component);

		// explicitly sets the 'enable' flag to be able to restore the state after click if/when #isDisabledOnClick is true
		this.options.set("enable", component.isEnabledInHierarchy());
	}

	// Factories //

	/**
	 * Build the {@link CssHeaderItem} with the indicator style
	 *
	 * @return the {@link HeaderItem}
	 */
	private static HeaderItem newIndicatorCssHeaderItem()
	{
		String css = String.format(".%s { background-image: url(%s); background-position: 0 0; }", CSS_INDICATOR, RequestCycleUtils.getAjaxIndicatorUrl());

		return CssHeaderItem.forCSS(css, "kendo-ui-icon-indicator");
	}

	/**
	 * Gets the new {@link Button}'s {@link Options} to be used on click
	 *
	 * @return the {@link Options}
	 */
	protected Options newOnClickOptions()
	{
		Options options = new Options();
		options.set("iconClass", Options.asString(CSS_INDICATOR));

		if (this.button != null && this.button.isDisabledOnClick())
		{
			options.set("enable", false);
		}

		return options;		
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
