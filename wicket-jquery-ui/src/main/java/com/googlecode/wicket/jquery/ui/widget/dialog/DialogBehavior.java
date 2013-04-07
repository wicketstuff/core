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

import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.core.JQueryEvent;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.core.ajax.IJQueryAjaxAware;
import com.googlecode.wicket.jquery.core.ajax.JQueryAjaxBehavior;
import com.googlecode.wicket.jquery.ui.form.button.Button;

/**
 * Provides a jQuery dialog behavior.
 *
 * @author Sebastien Briquet - sebfz1
 * @since 1.2.3
 * @since 6.0.1
 */
public abstract class DialogBehavior extends JQueryBehavior implements IJQueryAjaxAware, IDialogListener
{
	private static final long serialVersionUID = 1L;
	private static final String METHOD = "dialog";

	private JQueryAjaxBehavior onDefaultClose = null;

	/**
	 * Constructor
	 *
	 * @param selector the html selector (ie: "#myId")
	 */
	public DialogBehavior(String selector)
	{
		super(selector, METHOD);
	}

	/**
	 * Constructor
	 *
	 * @param selector the html selector (ie: "#myId")
	 * @param options the {@link Options}
	 */
	public DialogBehavior(String selector, Options options)
	{
		super(selector, METHOD, options);
	}

	// Properties //
	/**
	 * Gets the dialog's buttons.<br/>
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

		if (this.isDefaultCloseEventEnabled())
		{
			component.add(this.onDefaultClose = this.newDefaultCloseBehavior());
		}
	}

	/**
	 * Opens the dialogs in ajax.<br/>
	 * @param target the {@link AjaxRequestTarget}
	 */
	public void open(AjaxRequestTarget target)
	{
		target.appendJavaScript(this.$("'open'"));
	}

	/**
	 * Closes the dialogs in ajax.<br/>
	 * @param target the {@link AjaxRequestTarget}
	 */
	public void close(AjaxRequestTarget target)
	{
		target.appendJavaScript(this.$("'close'"));
	}

	// Events //
	@Override
	public void onConfigure(Component component)
	{
		super.onConfigure(component);

		if (this.onDefaultClose != null)
		{
			this.setOption("close", this.onDefaultClose.getCallbackFunction());
		}

		// buttons events //
		StringBuffer buttons = new StringBuffer("[ ");

		int index = 0;
		for(ButtonAjaxBehavior behavior : component.getBehaviors(ButtonAjaxBehavior.class))
		{
			DialogButton button = behavior.getButton();

			if (index++ > 0) { buttons.append(", "); }
			buttons.append("{");
			buttons.append("'id': '").append(button.getMarkupId()).append("', ");
			buttons.append("'text': '").append(button.toString()).append("', ");
			if (!button.isEnabled()) { buttons.append("'disabled': true, "); }
			if (button.getIcon() != null) { buttons.append("icons: { primary: '").append(button.getIcon()).append("' }, "); }
			buttons.append("'click': function() { ").append(behavior.getCallbackScript()).append(" }");
			buttons.append("}");
		}

		buttons.append(" ]");

		this.setOption("buttons", buttons);
	}

	@Override
	public void onAjax(AjaxRequestTarget target, JQueryEvent event)
	{
		if (event instanceof ClickEvent)
		{
			this.onClick(target, ((ClickEvent) event).getButton());
		}

		else if (event instanceof CloseEvent)
		{
			this.onClose(target, null);
		}
	}

	// Factories //
	/**
	 * Gets a new ButtonAjaxBehavior that will be called by the corresponding dialog's button.<br/>
	 * This method mays be overridden internally to provide another behavior;
	 *
	 * @param button the button that is passed to the behavior so it can be retrieved via the {@link ClickEvent}
	 */
	protected abstract ButtonAjaxBehavior newButtonAjaxBehavior(IJQueryAjaxAware source, DialogButton button);

	/**
	 * Gets the ajax behavior that will be triggered when the user clicks on the X-icon
	 *
	 * @return the {@link JQueryAjaxBehavior}
	 */
	protected JQueryAjaxBehavior newDefaultCloseBehavior()
	{
		return new JQueryAjaxBehavior(this) {

			private static final long serialVersionUID = 1L;

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
		};
	}


	// Ajax behaviors //
	/**
	 * Provides the {@link JQueryAjaxBehavior} being called by the button(s).
	 */
	protected static class ButtonAjaxBehavior extends JQueryAjaxBehavior
	{
		private static final long serialVersionUID = 1L;

		private final DialogButton button;

		/**
		 * Constructor
		 * @param source the {@link IJQueryAjaxAware}
		 * @param button the {@link DialogButton} to attach to the {@link ClickEvent}
		 */
		public ButtonAjaxBehavior(IJQueryAjaxAware source, DialogButton button)
		{
			super(source);

			this.button = button;
		}

		/**
		 * Gets the {@link DialogButton}
		 * @return the {@link DialogButton}
		 */
		public DialogButton getButton()
		{
			return this.button;
		}

		@Override
		protected JQueryEvent newEvent()
		{
			return new ClickEvent(this.button);
		}
	}


	// Events classes //
	/**
	 * Provides a dialog event that will be transmitted to the dialog {@link AbstractDialog}
	 */
	protected static class ClickEvent extends JQueryEvent
	{
		private final DialogButton button;

		public ClickEvent(DialogButton button)
		{
			super();

			this.button = button;
		}

		/**
		 * Get the button that has been attached to this event object
		 * @return the button
		 */
		public DialogButton getButton()
		{
			return this.button;
		}
	}

	/**
	 * An event object that will be broadcasted when the user clicks on the X-icon
	 */
	protected static class CloseEvent extends JQueryEvent
	{
	}
}
