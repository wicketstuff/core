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
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.feedback.FencedFeedbackPanel;
import org.apache.wicket.feedback.IFeedbackMessageFilter;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.EmptyPanel;

import com.googlecode.wicket.jquery.ui.JQueryIcon;

/**
 * Provides a {@link FencedFeedbackPanel} customized with the jQuery theme
 *
 * @since 6.8.0
 * @author Sebastien Briquet - sebfz1
 *
 */
public class JQueryFencedFeedbackPanel extends FencedFeedbackPanel
{
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 * @param id the markup id
	 */
	public JQueryFencedFeedbackPanel(String id)
	{
		super(id);

		this.initialize();
	}

	/**
	 * Constructor
	 * @see FencedFeedbackPanel#FencedFeedbackPanel(String, Component)
	 *
	 * @param id the markup id
	 * @param fence the {@link Component}
	 */
	public JQueryFencedFeedbackPanel(String id, Component fence)
	{
		super(id, fence);

		this.initialize();
	}

	/**
	 * Constructor
	 * @see FencedFeedbackPanel#FencedFeedbackPanel(String, IFeedbackMessageFilter)
	 *
	 * @param id the markup id
	 * @param filter the {@link IFeedbackMessageFilter}
	 */
	public JQueryFencedFeedbackPanel(String id, IFeedbackMessageFilter filter)
	{
		super(id, filter);

		this.initialize();
	}

	/**
	 * Constructor
	 * @see FencedFeedbackPanel#FencedFeedbackPanel(String, Component, IFeedbackMessageFilter)
	 *
	 * @param id the markup id
	 * @param fence the {@link Component}
	 * @param filter the {@link IFeedbackMessageFilter}
	 */
	public JQueryFencedFeedbackPanel(String id, Component fence, IFeedbackMessageFilter filter)
	{
		super(id, fence, filter);

		this.initialize();
	}

	/**
	 * Initialization
	 */
	private void initialize()
	{
		this.setOutputMarkupId(true);
	}

	@Override
	protected Component newMessageDisplayComponent(String id, FeedbackMessage message)
	{
		WebMarkupContainer container = new WebMarkupContainer(id);
		container.add(new EmptyPanel("icon").add(AttributeModifier.replace("class", this.getIconClass(message))));
		container.add(super.newMessageDisplayComponent("label", message));

		return container;
	}

	@Override
	protected String getCSSClass(FeedbackMessage message)
	{
		switch (message.getLevel())
		{
			case FeedbackMessage.INFO:
				return JQueryFeedbackPanel.INFO_CSS;

			case FeedbackMessage.SUCCESS:
				return JQueryFeedbackPanel.LIGHT_CSS;

			case FeedbackMessage.WARNING:
				return JQueryFeedbackPanel.WARN_CSS;

			case FeedbackMessage.ERROR:
				return JQueryFeedbackPanel.ERROR_CSS;

			default:
				return super.getCSSClass(message);
		}
	}

	/**
	 * Gets the icon CSS class for the given message.
	 *
	 * @param message the {@link FeedbackMessage}
	 * @return the icon class
	 */
	protected String getIconClass(FeedbackMessage message)
	{
		switch (message.getLevel())
		{
			case FeedbackMessage.INFO:
				return JQueryFeedbackPanel.INFO_ICO;

			case FeedbackMessage.SUCCESS:
				return JQueryFeedbackPanel.LIGHT_ICO;

			case FeedbackMessage.WARNING:
				return JQueryFeedbackPanel.WARN_ICO;

			case FeedbackMessage.ERROR:
				return JQueryFeedbackPanel.ERROR_ICO;

			default:
				return JQueryIcon.NONE;
		}
	}
}
