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
package com.googlecode.wicket.jquery.ui.form.button;

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
import com.googlecode.wicket.jquery.ui.JQueryIcon;
import com.googlecode.wicket.jquery.ui.form.button.IndicatingAjaxButton.Position;

/**
 * Provides a jQuery UI button {@link JQueryBehavior} with an ajax indicator
 *
 * @author Sebastien Briquet - sebfz1
 *
 */
public class AjaxIndicatingButtonBehavior extends ButtonBehavior
{
	private static final long serialVersionUID = 1L;

	public static final String CSS_INDICATOR = "ui-icon-indicator";

	private final String icon;
	private final Position position;

	/**
	 * Constructor
	 * 
	 * @param selector the html selector (ie: "#myId")
	 * @param icon the button's icon
	 * @param position the {@code Position} of the ajax-indicator
	 */
	protected AjaxIndicatingButtonBehavior(String selector, String icon, Position position)
	{
		super(selector);

		this.icon = icon;
		this.position = position;
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
		// configure the busy indicator start & stop //
		// caution: in specific cases, #getSelector may return a different selector that this.selector
		StringBuilder builder = new StringBuilder(super.$());

		builder.append("jQuery('").append(this.getSelector()).append("')").append(".click(function() { ");
		builder.append($(this.newOnClickOptions()));
		builder.append("}); ");

		builder.append("jQuery(document).ajaxStop(function() { ");
		builder.append($(this.newOnAjaxStopOptions()));
		builder.append("}); ");

		return builder.toString();
	}

	// Factories //

	/**
	 * Build the {@link CssHeaderItem} with the indicator style
	 *
	 * @return the {@link HeaderItem}
	 */
	public static HeaderItem newIndicatorCssHeaderItem()
	{
		IRequestHandler handler = new ResourceReferenceRequestHandler(AbstractDefaultAjaxBehavior.INDICATOR);
		String css = String.format(".ui-icon.ui-icon-indicator { background-image: url(%s) !important; background-position: 0 0; }", RequestCycle.get().urlFor(handler));

		return CssHeaderItem.forCSS(css, "jquery-ui-icon-indicator");
	}

	/**
	 * Gets the new {@link Button}'s {@link Options} to be used on click
	 *
	 * @return the {@link Options}
	 */
	protected Options newOnClickOptions()
	{
		Options icons = new Options();

		if (this.position == Position.LEFT)
		{
			icons.set("primary", Options.asString(CSS_INDICATOR));
		}
		else
		{
			icons.set("primary", JQueryIcon.isNone(this.icon) ? "null" : Options.asString(this.icon));
			icons.set("secondary", Options.asString(CSS_INDICATOR));
		}

		return new Options("icons", icons);
	}

	/**
	 * Gets the new {@link Button}'s {@link Options} to be used on when ajax stops
	 *
	 * @return the {@link Options}
	 */
	protected Options newOnAjaxStopOptions()
	{
		Options icons = new Options();

		icons.set("primary", JQueryIcon.isNone(this.icon) ? "null" : Options.asString(this.icon));
		icons.set("secondary", "null");

		return new Options("icons", icons);
	}
}
