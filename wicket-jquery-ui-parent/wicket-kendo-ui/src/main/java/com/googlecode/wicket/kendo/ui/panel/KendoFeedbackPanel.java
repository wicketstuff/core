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

import org.apache.wicket.IGenericComponent;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.core.request.handler.IPartialPageRequestHandler;
import org.apache.wicket.feedback.ContainerFeedbackMessageFilter;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.feedback.FeedbackMessagesModel;
import org.apache.wicket.feedback.IFeedback;
import org.apache.wicket.feedback.IFeedbackMessageFilter;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.lang.Args;
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
public class KendoFeedbackPanel extends WebMarkupContainer implements IJQueryWidget, IFeedback, IGenericComponent<List<FeedbackMessage>, KendoFeedbackPanel>
{
	private static final long serialVersionUID = 1L;

	private NotificationBehavior widgetBehavior;
	private final IFeedbackMessageFilter filter;

	protected final Options options;

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 */
	public KendoFeedbackPanel(String id)
	{
		this(id, (IFeedbackMessageFilter) null, new Options());
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param container the container that message reporters must be a child of
	 */
	public KendoFeedbackPanel(String id, MarkupContainer container)
	{
		this(id, new ContainerFeedbackMessageFilter(container), new Options());
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param filter the {@link IFeedbackMessageFilter}
	 */
	public KendoFeedbackPanel(String id, IFeedbackMessageFilter filter)
	{
		this(id, filter, new Options());
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param options the {@link Options}
	 */
	public KendoFeedbackPanel(String id, Options options)
	{
		this(id, (IFeedbackMessageFilter) null, options);

	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param container the container that message reporters must be a child of
	 * @param options the {@link Options}
	 */
	public KendoFeedbackPanel(String id, MarkupContainer container, Options options)
	{
		this(id, new ContainerFeedbackMessageFilter(container), options);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param filter the {@link IFeedbackMessageFilter}
	 * @param options the {@link Options}
	 */
	public KendoFeedbackPanel(String id, IFeedbackMessageFilter filter, Options options)
	{
		super(id);

		this.filter = filter;
		this.options = Args.notNull(options, "options");
	}

	// Methods //

	@Override
	protected IModel<?> initModel()
	{
		return this.newFeedbackMessagesModel(this.filter);
	}

	/**
	 * Calls {@link Strings#escapeMarkup(CharSequence, boolean, boolean)} by default, if {@link #getEscapeModelStrings()} returns {@code true}<br>
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

	/**
	 * Refreshes the {@code KendoFeedbackPanel}.
	 *
	 * @param handler the {@link IPartialPageRequestHandler}
	 */
	public final void refresh(IPartialPageRequestHandler handler)
	{
		for (FeedbackMessage message : this.getModelObject())
		{
			if (!message.isRendered())
			{
				this.widgetBehavior.show(handler, message.getMessage(), message.getLevelAsString());
				message.markRendered();
			}
		}

		this.getModel().detach(); // forces the retrieval of next messages
	}

	/**
	 * Refreshes the {@code KendoFeedbackPanel}.
	 * 
	 * @param handler the {@link IPartialPageRequestHandler}
	 * @param hide whether previous notifications should be hidden
	 */
	public final void refresh(IPartialPageRequestHandler handler, Boolean hide)
	{
		if (hide)
		{
			this.hide(handler);
		}

		this.refresh(handler);
	}

	/**
	 * Hides all notifications
	 *
	 * @param handler the {@link IPartialPageRequestHandler}
	 */
	public final void hide(IPartialPageRequestHandler handler)
	{
		this.widgetBehavior.hide(handler);
	}

	// Events //

	@Override
	protected void onInitialize()
	{
		super.onInitialize();

		this.widgetBehavior = (NotificationBehavior) JQueryWidget.newWidgetBehavior(this);
		this.add(this.widgetBehavior); // cannot be in ctor as the markupId may be set manually afterward
	}

	@Override
	public void onConfigure(JQueryBehavior behavior)
	{
		behavior.setOption("hideOnClick", false);
		behavior.setOption("autoHideAfter", 0);
		behavior.setOption("appendTo", Options.asString(behavior.getSelector()));
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
		return new NotificationBehavior(selector, this.options) { // NOSONAR

			private static final long serialVersionUID = 1L;

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
					builder.append(this.$(message.getMessage(), message.getLevelAsString()));

					message.markRendered();
				}

				return builder.toString();
			}
		};
	}

	// Factories //

	/**
	 * Gets a new instance of the FeedbackMessagesModel to use.
	 * 
	 * @param filter the {@link IFeedbackMessageFilter}
	 * @return a new {@code FeedbackMessagesModel}
	 */
	protected FeedbackMessagesModel newFeedbackMessagesModel(IFeedbackMessageFilter filter)
	{
		return new FeedbackMessagesModel(this).setFilter(filter);
	}
}
