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
package com.googlecode.wicket.kendo.ui.widget.window;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.core.util.string.JavaScriptUtils;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.lang.Args;

import com.googlecode.wicket.jquery.core.IJQueryWidget;
import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.core.Options;

/**
 * Base class for implementing Kendo UI windows
 *
 * @param <T> the type of the model object
 *
 * @author Sebastien Briquet - sebfz1
 * @since 6.17.0
 */
public abstract class AbstractWindow<T> extends GenericPanel<T> implements IJQueryWidget, IWindowListener
{
	private static final long serialVersionUID = 1L;

	protected static final String ACTION_PIN = "pin";
	protected static final String ACTION_CLOSE = "close";
	protected static final String ACTION_REFRESH = "refresh";
	protected static final String ACTION_CUSTOM = "custom";
	protected static final String ACTION_MINIMIZE = "minimize";
	protected static final String ACTION_MAXIMIZE = "maximize";

	/** Default width */
	private static final int WIDTH = 450;

	private IModel<String> title;
	private boolean modal;
	private WindowBehavior widgetBehavior;

	/**
	 * Constructor
	 *
	 * @param id the markupId, an html div suffice to host a window.
	 * @param title the title of the window
	 */
	public AbstractWindow(String id, String title)
	{
		this(id, title, null, true);
	}

	/**
	 * Constructor
	 *
	 * @param id the markupId, an html div suffice to host a window.
	 * @param title the title of the window
	 */
	public AbstractWindow(String id, IModel<String> title)
	{
		this(id, title, null, true);
	}

	/**
	 * Constructor
	 *
	 * @param id the markupId, an html div suffice to host a window.
	 * @param title the title of the window
	 * @param model the model to be used in the window.
	 */
	public AbstractWindow(String id, String title, IModel<T> model)
	{
		this(id, title, model, true);
	}

	/**
	 * Constructor
	 *
	 * @param id the markupId, an html div suffice to host a window.
	 * @param title the title of the window
	 * @param model the model to be used in the window.
	 */
	public AbstractWindow(String id, IModel<String> title, IModel<T> model)
	{
		this(id, title, model, true);
	}

	/**
	 * Constructor
	 *
	 * @param id the markupId, an html div suffice to host a window.
	 * @param title the title of the window
	 * @param modal indicates whether the window is modal
	 */
	public AbstractWindow(String id, String title, boolean modal)
	{
		this(id, title, null, modal);
	}

	/**
	 * Constructor
	 *
	 * @param id the markupId, an html div suffice to host a window.
	 * @param title the title of the window
	 * @param modal indicates whether the window is modal
	 */
	public AbstractWindow(String id, IModel<String> title, boolean modal)
	{
		this(id, title, null, modal);
	}

	/**
	 * Constructor
	 *
	 * @param id markupId, an html div suffice to host a window.
	 * @param title the title of the window
	 * @param modal indicates whether the window is modal
	 * @param model the model to be used in the window
	 */
	public AbstractWindow(String id, String title, IModel<T> model, boolean modal)
	{
		this(id, Model.of(title), model, modal);
	}

	/**
	 * Constructor
	 *
	 * @param id markupId, an html div suffice to host a window.
	 * @param title the title of the window
	 * @param modal indicates whether the window is modal
	 * @param model the model to be used in the window
	 */
	public AbstractWindow(String id, IModel<String> title, IModel<T> model, boolean modal)
	{
		super(id, model);

		this.title = title;
		this.modal = modal;
	}

	// Methods //

	/**
	 * Opens the window in ajax.
	 *
	 * @param target the {@link AjaxRequestTarget}
	 */
	public final void open(AjaxRequestTarget target)
	{
		this.onOpen(target);

		if (this.widgetBehavior != null)
		{
			this.widgetBehavior.open(target);
		}
	}

	/**
	 * Closes the window in ajax.
	 *
	 * @param target the {@link AjaxRequestTarget}
	 */
	public final void close(AjaxRequestTarget target)
	{
		if (this.widgetBehavior != null)
		{
			this.widgetBehavior.close(target);
		}

		this.onClose(target);
	}

	// Properties //

	/**
	 * Gets the window's with
	 *
	 * @return {@link #WIDTH} by default
	 */
	public int getWidth()
	{
		return AbstractWindow.WIDTH;
	}

	/**
	 * Gets the window's title
	 *
	 * @return the window's title
	 */
	public IModel<String> getTitle()
	{
		return this.title;
	}

	/**
	 * Sets the window's title
	 *
	 * @param title the window's title
	 */
	public void setTitle(IModel<String> title)
	{
		Args.notNull(title, "title");

		this.title = title;
	}

	/**
	 * Sets the window's title dynamically
	 *
	 * @param target the {@link AjaxRequestTarget}
	 * @param title the window's title
	 */
	public void setTitle(AjaxRequestTarget target, String title)
	{
		this.setTitle(target, Model.of(title));
	}

	/**
	 * Sets the window's title dynamically
	 *
	 * @param target the {@link AjaxRequestTarget}
	 * @param title the window's title
	 */
	public void setTitle(AjaxRequestTarget target, IModel<String> title)
	{
		this.setTitle(title);

		target.appendJavaScript(String.format("%s.title('%s');", this.widgetBehavior.widget(), JavaScriptUtils.escapeQuotes(title.getObject())));
	}

	/**
	 * Gets the modal flag
	 *
	 * @return the modal flag supplied to the constructor by default
	 */
	public final boolean isModal()
	{
		return this.modal;
	}

	/**
	 * Indicated whether the window is resizable
	 *
	 * @return false by default
	 */
	public boolean isResizable()
	{
		return false;
	}

	@Override
	public boolean isActionEventEnabled()
	{
		return false;
	}

	@Override
	public boolean isCloseEventEnabled()
	{
		return false;
	}

	// Events //

	@Override
	protected void onInitialize()
	{
		super.onInitialize();

		this.add(this.widgetBehavior = this.newWidgetBehavior(JQueryWidget.getSelector(this)));
	}

	@Override
	public void onConfigure(JQueryBehavior behavior)
	{
		// class options //
		behavior.setOption("title", Options.asString(this.getTitle().getObject()));
		behavior.setOption("modal", this.isModal());
		behavior.setOption("resizable", this.isResizable());
		behavior.setOption("width", this.getWidth());
		behavior.setOption("visible", false);
	}

	@Override
	public void onBeforeRender(JQueryBehavior behavior)
	{
		// noop
	}

	/**
	 * Triggered when the window opens
	 *
	 * @param target the {@link AjaxRequestTarget}
	 */
	protected void onOpen(AjaxRequestTarget target)
	{
		// noop
	}

	@Override
	public void onAction(AjaxRequestTarget target, String action)
	{
		// noop
	}

	@Override
	public void onClose(AjaxRequestTarget target)
	{
		// noop
	}

	// IJQueryWidget //

	/**
	 * @see IJQueryWidget#newWidgetBehavior(String)
	 */
	@Override
	public WindowBehavior newWidgetBehavior(String selector)
	{
		return new WindowBehavior(selector) {

			private static final long serialVersionUID = 1L;

			// Properties //

			@Override
			public boolean isActionEventEnabled()
			{
				return AbstractWindow.this.isActionEventEnabled();
			}

			@Override
			public boolean isCloseEventEnabled()
			{
				return AbstractWindow.this.isCloseEventEnabled();
			}

			// Events //

			@Override
			public void onAction(AjaxRequestTarget target, String action)
			{
				AbstractWindow.this.onAction(target, action);
			}

			@Override
			public void onClose(AjaxRequestTarget target)
			{
				AbstractWindow.this.onClose(target);
			}
		};
	}
}
