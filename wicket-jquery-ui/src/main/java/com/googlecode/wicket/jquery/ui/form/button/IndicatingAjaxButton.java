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
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.IRequestHandler;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.handler.resource.ResourceReferenceRequestHandler;

import com.googlecode.wicket.jquery.core.IJQueryWidget;
import com.googlecode.wicket.jquery.core.JQueryBehavior;

/**
 * Provides a jQuery button based on the built-in AjaxButton, with an ajax indicator the time the {@link #onSubmit()} process.
 *
 * @since 6.0
 * @author Sebastien Briquet - sebfz1
 */
public abstract class IndicatingAjaxButton extends AjaxButton implements IJQueryWidget
{
	private static final long serialVersionUID = 1L;

	public enum Position { LEFT, RIGHT }

	private Position position = Position.LEFT;

	/**
	 * Constructor
	 * @param id the markup id
	 */
	public IndicatingAjaxButton(String id)
	{
		super(id);
	}

	/**
	 * Constructor
	 * @param id the markup id
	 * @param form the {@link Form}
	 */
	public IndicatingAjaxButton(String id, Form<?> form)
	{
		super(id, form);
	}

	/**
	 * Constructor
	 * @param id the markup id
	 * @param model the {@link IModel}
	 */
	public IndicatingAjaxButton(String id, IModel<String> model)
	{
		super(id, model);
	}

	/**
	 * Constructor
	 * @param id the markup id
	 * @param model the {@link IModel}
	 * @param form the {@link Form}
	 */
	public IndicatingAjaxButton(String id, IModel<String> model, Form<?> form)
	{
		super(id, model, form);
	}

	public IndicatingAjaxButton setPosition(Position position)
	{
		this.position = position;
		return this;
	}

	// Events //
	@Override
	protected void onInitialize()
	{
		super.onInitialize();

		this.add(JQueryWidget.newWidgetBehavior(this));
	}

	@Override
	protected void onError(AjaxRequestTarget target, Form<?> form)
	{
	}

	// IJQueryWidget //
	@Override
	public JQueryBehavior newWidgetBehavior(String selector)
	{
		return new JQueryBehavior(selector, "button") {

			private static final long serialVersionUID = 1L;

			@Override
			public void renderHead(Component component, IHeaderResponse response)
			{
				super.renderHead(component, response);

				IRequestHandler handler = new ResourceReferenceRequestHandler(AbstractDefaultAjaxBehavior.INDICATOR);

				/* adds and configure the busy indicator */
				response.render(CssHeaderItem.forCSS(".ui-icon.ui-icon-indicator { background-image: url(" + RequestCycle.get().urlFor(handler).toString() + ") !important; background-position: 0 0; }", "jquery-ui-icon-indicator"));

				/* adds and configure the busy indicator */
				StringBuilder script = new StringBuilder("jQuery(function() {");
				script.append("jQuery('").append(selector).append("')");
				script.append(".click(function() { jQuery(this).button('option', 'icons', {").append(position == Position.LEFT ? "primary" : "secondary").append(": 'ui-icon-indicator' }); })");
				script.append(".ajaxStop(function() { jQuery(this).button('option', 'icons', {").append(position == Position.LEFT ? "primary" : "secondary").append(": null }); })");
				script.append("});");

				response.render(JavaScriptHeaderItem.forScript(script, this.getClass().getName() + "-" + selector));
			}
		};
	}
}
