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

	public enum Position
	{
		LEFT, RIGHT
	}

	private Position position = Position.LEFT;

	/**
	 * Constructor
	 * 
	 * @param id the markup id
	 */
	public IndicatingAjaxButton(String id)
	{
		super(id);
	}

	/**
	 * Constructor
	 * 
	 * @param id the markup id
	 * @param form the {@link Form}
	 */
	public IndicatingAjaxButton(String id, Form<?> form)
	{
		super(id, form);
	}

	/**
	 * Constructor
	 * 
	 * @param id the markup id
	 * @param model the {@link IModel}
	 */
	public IndicatingAjaxButton(String id, IModel<String> model)
	{
		super(id, model);
	}

	/**
	 * Constructor
	 * 
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
	public void onConfigure(JQueryBehavior behavior)
	{
		// noop
	}

	@Override
	public void onBeforeRender(JQueryBehavior behavior)
	{
		// noop
	}

	@Override
	protected void onError(AjaxRequestTarget target, Form<?> form)
	{
		// noop
	}

	// IJQueryWidget //
	@Override
	public JQueryBehavior newWidgetBehavior(String selector)
	{
		return new ButtonBehavior(selector) {

			private static final long serialVersionUID = 1L;

			@Override
			public void renderHead(Component component, IHeaderResponse response)
			{
				super.renderHead(component, response);

				// adds the busy indicator style //
				IRequestHandler handler = new ResourceReferenceRequestHandler(AbstractDefaultAjaxBehavior.INDICATOR);
				String css = String.format(".ui-icon.ui-icon-indicator { background-image: url(%s) !important; background-position: 0 0; }", RequestCycle.get().urlFor(handler));

				response.render(CssHeaderItem.forCSS(css, "jquery-ui-icon-indicator"));
			}

			@Override
			protected String $()
			{
				// configure the busy indicator start & stop //
				StringBuilder builder = new StringBuilder(super.$());

				builder.append("jQuery('").append(this.selector).append("')").append(".click(function() { jQuery(this).button('option', 'icons', {").append(position == Position.LEFT ? "primary" : "secondary")
						.append(": 'ui-icon-indicator' }); }); ");
				builder.append("jQuery(document).ajaxStop(function() { jQuery('").append(this.selector).append("').button('option', 'icons', {").append(position == Position.LEFT ? "primary" : "secondary").append(": null }); }); ");

				return builder.toString();
			}
		};
	}
}
