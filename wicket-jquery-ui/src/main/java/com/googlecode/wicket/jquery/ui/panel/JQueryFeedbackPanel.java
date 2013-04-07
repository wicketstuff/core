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
package com.googlecode.wicket.jquery.ui.panel;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.feedback.ComponentFeedbackMessageFilter;
import org.apache.wicket.feedback.ContainerFeedbackMessageFilter;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.markup.html.panel.FeedbackPanel;

import com.googlecode.wicket.jquery.ui.JQueryIcon;

/**
 * Provides a {@link FeedbackPanel} customized with the jQuery theme
 *
 * @author Sebastien Briquet - sebfz1
 *
 */
public class JQueryFeedbackPanel extends FeedbackPanel
{
	private static final long serialVersionUID = 1L;

	public static final String INFO_ICO = "ui-icon " + JQueryIcon.INFO;
	public static final String INFO_CSS = "ui-state-highlight ui-corner-all";

	public static final String WARN_ICO = "ui-icon " + JQueryIcon.ALERT;
	public static final String WARN_CSS = "ui-state-highlight ui-corner-all";

	public static final String LIGHT_ICO = "ui-icon " + JQueryIcon.LIGHTBULB;
	public static final String LIGHT_CSS = "ui-state-highlight ui-corner-all";

	public static final String ERROR_ICO = "ui-icon " + JQueryIcon.ALERT; //ui-icon-closethick
	public static final String ERROR_CSS = "ui-state-error ui-corner-all";

	/**
	 * Constructor
	 * @param id the markup id
	 */
	public JQueryFeedbackPanel(String id)
	{
		super(id);

		this.init();
	}

	/**
	 * Constructor
	 * @param id the markup id
	 * @param filter the component to filter on
	 */
	public JQueryFeedbackPanel(String id, Component filter)
	{
		super(id, new ComponentFeedbackMessageFilter(filter));

		this.init();
	}

	/**
	 * Constructor
	 * @param id the markup id
	 * @param filter the container that message reporters must be a child of
	 */
	public JQueryFeedbackPanel(String id, MarkupContainer filter)
	{
		super(id, new ContainerFeedbackMessageFilter(filter));

		this.init();
	}

	/**
	 * Initialization
	 */
	private void init()
	{
		this.setOutputMarkupId(true);
	}

	@Override
	protected Component newMessageDisplayComponent(String id, FeedbackMessage message) {

		WebMarkupContainer container = new WebMarkupContainer(id);
		container.add(AttributeModifier.replace("class", this.getMessageClass(message)));
		container.add(new EmptyPanel("icon").add(AttributeModifier.replace("class", this.getIconClass(message))));
		container.add(super.newMessageDisplayComponent("label", message));

		return container;
	}

	@Override
	protected String getCSSClass(FeedbackMessage message)
	{
		return ""; //not used, because it would be applied onto both 'message' and 'label'
	}

	/**
	 * Gets the icon CSS class for the given message.
	 * @param message the {@link FeedbackMessage}
	 * @return the icon class
	 */
	protected String getIconClass(FeedbackMessage message)
	{
		switch (message.getLevel())
		{
			case FeedbackMessage.INFO:
				return INFO_ICO;

			case FeedbackMessage.WARNING:
				return WARN_ICO;

			case FeedbackMessage.ERROR:
				return ERROR_ICO;

			default:
				return this.getCSSClass(message);
		}
	}

	/**
	 * Gets the CSS class for the given message.
	 * @param message the {@link FeedbackMessage}
	 * @return the label class
	 */
	protected String getMessageClass(FeedbackMessage message)
	{
		switch (message.getLevel())
		{
			case FeedbackMessage.INFO:
				return INFO_CSS;

			case FeedbackMessage.WARNING:
				return WARN_CSS;

			case FeedbackMessage.ERROR:
				return ERROR_CSS;

			default:
				return this.getCSSClass(message);
		}
	}
}
