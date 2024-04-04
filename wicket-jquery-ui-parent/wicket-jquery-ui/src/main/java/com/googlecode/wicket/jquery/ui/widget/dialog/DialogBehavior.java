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
package com.googlecode.wicket.jquery.ui.widget.dialog;

import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.core.request.handler.IPartialPageRequestHandler;
import org.apache.wicket.util.lang.Args;
import com.github.openjson.JSONObject;

import com.googlecode.wicket.jquery.core.JQueryEvent;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.core.ajax.IJQueryAjaxAware;
import com.googlecode.wicket.jquery.core.ajax.JQueryAjaxBehavior;
import com.googlecode.wicket.jquery.ui.JQueryUIBehavior;
import com.googlecode.wicket.jquery.ui.form.button.Button;
import com.googlecode.wicket.jquery.ui.widget.dialog.ButtonAjaxBehavior.ClickEvent;

/**
 * Provides a jQuery dialog behavior.
 *
 * @author Sebastien Briquet - sebfz1
 * @since 1.2.3
 * @since 6.0.1
 */
public abstract class DialogBehavior extends JQueryUIBehavior implements IJQueryAjaxAware
{
	private static final long serialVersionUID = 1L;
	public static final String METHOD = "dialog";

	private final IDialogListener listener;
	private JQueryAjaxBehavior onDefaultCloseAjaxBehavior = null;
	private JQueryAjaxBehavior onEscapeCloseAjaxBehavior = null;

	/**
	 * Constructor
	 *
	 * @param selector the html selector (ie: "#myId")
	 * @param listener the {@link IDialogListener}
	 */
	public DialogBehavior(String selector, IDialogListener listener)
	{
		this(selector, new Options(), listener);
	}

	/**
	 * Constructor
	 *
	 * @param selector the html selector (ie: "#myId")
	 * @param options the {@link Options}
	 * @param listener the {@link IDialogListener}
	 */
	public DialogBehavior(String selector, Options options, IDialogListener listener)
	{
		super(selector, METHOD, options);

		this.listener = Args.notNull(listener, "listener");
	}

	// Properties //

	/**
	 * Gets the dialog's buttons.<br>
	 *
	 * @return the {@link List} of {@link Button}
	 */
	protected abstract List<DialogButton> getButtons();

	// Methods //

	@Override
	public void bind(Component component)
	{
		super.bind(component);

		for (DialogButton button : this.getButtons())
		{
			component.add(this.newButtonAjaxBehavior(this, button));
		}

		if (this.listener.isDefaultCloseEventEnabled())
		{
			this.onDefaultCloseAjaxBehavior = this.newOnDefaultCloseAjaxBehavior(this);
			component.add(this.onDefaultCloseAjaxBehavior);
		}

		if (this.listener.isEscapeCloseEventEnabled())
		{
			this.onEscapeCloseAjaxBehavior = this.newOnEscapeCloseAjaxBehavior(this);
			component.add(this.onEscapeCloseAjaxBehavior);
		}
	}

	/**
	 * Opens the dialogs in ajax.
	 *
	 * @param handler the {@link IPartialPageRequestHandler}
	 */
	public void open(IPartialPageRequestHandler handler)
	{
		handler.appendJavaScript(this.$(Options.asString("open")));
	}

	/**
	 * Closes the dialogs in ajax.
	 *
	 * @param handler the {@link IPartialPageRequestHandler}
	 */
	public void close(IPartialPageRequestHandler handler)
	{
		handler.prependJavaScript(this.$(Options.asString("close"))); // fixes #88
	}

	// Events //

	@Override
	public void onConfigure(Component component)
	{
		if (this.onDefaultCloseAjaxBehavior != null)
		{
			this.setOption("close", this.onDefaultCloseAjaxBehavior.getCallbackFunction());
		}

		if (this.onEscapeCloseAjaxBehavior != null)
		{
			this.setOption("beforeClose", this.onEscapeCloseAjaxBehavior.getCallbackFunction());
		}

		// buttons events //
		StringBuilder buttons = new StringBuilder("[ ");

		int index = 0;
		for (ButtonAjaxBehavior behavior : component.getBehaviors(ButtonAjaxBehavior.class))
		{
			DialogButton button = behavior.getButton();

			if (index++ > 0)
			{
				buttons.append(", ");
			}

			buttons.append("{");
			buttons.append("'id': '").append(button.getMarkupId()).append("', ");
			buttons.append("'text': ").append(JSONObject.quote(button.toString())).append(", ");

			if (!button.isEnabled())
			{
				buttons.append("'disabled': true, ");
			}

			if (button.getIcon() != null)
			{
				buttons.append("icons: { primary: '").append(button.getIcon()).append("' }, ");
			}

			buttons.append("'click': function() { ").append(button.getCallbackScript(behavior)).append(" }");
			buttons.append("}");
		}

		buttons.append(" ]");

		this.setOption("buttons", buttons);

		super.onConfigure(component);
	}

	@Override
	public void onAjax(AjaxRequestTarget target, JQueryEvent event)
	{
		if (event instanceof ClickEvent)
		{
			this.listener.onClick(target, ((ClickEvent) event).getButton());
		}

		else if (event instanceof CloseEvent)
		{
			this.listener.onClose(target, null);
		}
	}

	// Factories //

	/**
	 * Gets a new {@link ButtonAjaxBehavior} that will be called by the corresponding {@link DialogButton}.
	 *
	 * @param source the {@link IJQueryAjaxAware} source
	 * @param button the button that is passed to the behavior so it can be retrieved via the {@link ClickEvent}
	 * @return the {@link ButtonAjaxBehavior}
	 */
	protected abstract ButtonAjaxBehavior newButtonAjaxBehavior(IJQueryAjaxAware source, DialogButton button);

	/**
	 * Gets a new {@link JQueryAjaxBehavior} that will be wired to the 'close' event, triggered when the user clicks on the X-icon
	 *
	 * @param source the {@link IJQueryAjaxAware}
	 * @return a new {@code OnDefaultCloseAjaxBehavior} by default
	 */
	protected JQueryAjaxBehavior newOnDefaultCloseAjaxBehavior(IJQueryAjaxAware source)
	{
		return new OnDefaultCloseAjaxBehavior(source);
	}

	/**
	 * Gets a new {@link JQueryAjaxBehavior} that will be wired to the 'beforeClose' event, triggered when the user presses the escape key
	 *
	 * @param source the {@link IJQueryAjaxAware}
	 * @return a new {@code OnEscapeCloseAjaxBehavior} by default
	 */
	protected JQueryAjaxBehavior newOnEscapeCloseAjaxBehavior(IJQueryAjaxAware source)
	{
		return new OnEscapeCloseAjaxBehavior(source);
	}

	// Ajax class //

	/**
	 * Provides a {@link JQueryAjaxBehavior} that aims to be wired to the 'close' event<br>
	 * Underlying callback will be triggered when the user clicks on the X-icon
	 */
	protected static class OnDefaultCloseAjaxBehavior extends JQueryAjaxBehavior
	{
		private static final long serialVersionUID = 1L;

		public OnDefaultCloseAjaxBehavior(IJQueryAjaxAware source)
		{
			super(source);
		}

		@Override
		public String getCallbackFunction()
		{
			return "function(event, ui) { if (event.button == 0) { " + this.getCallbackScript() + " } }";
		}

		@Override
		protected JQueryEvent newEvent()
		{
			return new CloseEvent();
		}
	}

	/**
	 * Provides a {@link JQueryAjaxBehavior} that aims to be wired to the 'beforeClose' event<br>
	 * Underlying callback will be triggered when the user presses the escape key
	 */
	protected static class OnEscapeCloseAjaxBehavior extends JQueryAjaxBehavior
	{
		private static final long serialVersionUID = 1L;

		public OnEscapeCloseAjaxBehavior(IJQueryAjaxAware source)
		{
			super(source);
		}

		@Override
		public String getCallbackFunction()
		{
			return "function(event, ui) { if (event.keyCode == jQuery.ui.keyCode.ESCAPE) { " + this.getCallbackScript() + " } }";
		}

		@Override
		protected JQueryEvent newEvent()
		{
			return new CloseEvent();
		}
	}

	// Event objects //

	/**
	 * Provides an event object that will be broadcasted by the {@link OnDefaultCloseAjaxBehavior} and the {@link OnEscapeCloseAjaxBehavior} callbacks
	 */
	protected static class CloseEvent extends JQueryEvent
	{
	}
}
