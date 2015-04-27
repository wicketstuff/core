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
package com.googlecode.wicket.kendo.ui.panel;

import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.IGenericComponent;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.feedback.FeedbackMessagesModel;
import org.apache.wicket.feedback.IFeedback;
import org.apache.wicket.feedback.IFeedbackMessageFilter;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.string.Strings;

import com.googlecode.wicket.jquery.core.IJQueryWidget;
import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.kendo.ui.widget.notification.NotificationBehavior;

/**
 * Provides a {@link FeedbackPanel} customized with the Kendo UI theme
 *
 * @author Sebastien Briquet - sebfz1
 *
 */
public class KendoFeedbackPanel extends WebMarkupContainer implements IJQueryWidget, IFeedback, IGenericComponent<List<FeedbackMessage>>
{
	private static final long serialVersionUID = 1L;

	private final Options options;

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 */
	public KendoFeedbackPanel(String id)
	{
		this(id, new Options());
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param options the {@link Options}
	 */
	public KendoFeedbackPanel(String id, Options options)
	{
		super(id);

		this.options = options;
	}

	// Methods //

	/**
	 * Calls {@link Strings#escapeMarkup(CharSequence, boolean, boolean)} by default, if {@link #getEscapeModelStrings()} returns <code>true</code><br />
	 * This can be overridden to provide additional escaping
	 * 
	 * @param message the message to format
	 * @param level the level, ie: info, success, warning, error
	 * @return the escaped markup
	 * @see #setEscapeModelStrings(boolean)
	 */
	protected CharSequence escape(CharSequence message, String level)
	{
		return Strings.escapeMarkup(message, false, false);
	}

	// Properties //

	@Override
	@SuppressWarnings("unchecked")
	public IModel<List<FeedbackMessage>> getModel()
	{
		return (IModel<List<FeedbackMessage>>) this.getDefaultModel();
	}

	@Override
	public void setModel(IModel<List<FeedbackMessage>> model)
	{
		this.setDefaultModel(model);
	}

	@Override
	public void setModelObject(List<FeedbackMessage> object)
	{
		this.setDefaultModelObject(object);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<FeedbackMessage> getModelObject()
	{
		return (List<FeedbackMessage>) this.getDefaultModelObject();
	}

	// Events //

	@Override
	protected void onInitialize()
	{
		super.onInitialize();

		this.setDefaultModel(this.newFeedbackMessagesModel());
		this.add(JQueryWidget.newWidgetBehavior(this));
	}

	@Override
	public void onConfigure(JQueryBehavior behavior)
	{
		behavior.setOption("hideOnClick", false);
		behavior.setOption("autoHideAfter", 0);
	}

	@Override
	public void onBeforeRender(JQueryBehavior behavior)
	{
		// noop
	}

	// IJQueryWidget //

	@Override
	public NotificationBehavior newWidgetBehavior(String selector)
	{
		return new NotificationBehavior(selector, this.options) {

			private static final long serialVersionUID = 1L;

			@Override
			public void onConfigure(Component component)
			{
				super.onConfigure(component);

				this.setOption("appendTo", Options.asString(this.selector));
			}

			@Override
			protected CharSequence format(CharSequence message, String level)
			{
				if (KendoFeedbackPanel.this.getEscapeModelStrings())
				{
					return KendoFeedbackPanel.this.escape(message, level);
				}

				return super.format(message, level);
			}

			@Override
			protected String $()
			{
				StringBuilder builder = new StringBuilder(super.$());

				for (FeedbackMessage message : KendoFeedbackPanel.this.getModelObject())
				{
					builder.append(this.$(message.getMessage(), message.getLevelAsString().toLowerCase()));

					message.markRendered();
				}

				return builder.toString();
			}
		};
	}

	// Factories //

	/**
	 * Gets a new instance of the FeedbackMessagesModel to use.<br/>
	 * This method can be overridden to provide a {@link IFeedbackMessageFilter}
	 *
	 * @return a new {@link FeedbackMessagesModel}
	 */
	protected FeedbackMessagesModel newFeedbackMessagesModel()
	{
		return new FeedbackMessagesModel(this);
	}
}
