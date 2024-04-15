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

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.core.request.handler.IPartialPageRequestHandler;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.lang.Args;

import com.googlecode.wicket.jquery.core.IJQueryWidget;
import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.core.ajax.IJQueryAjaxAware;
import com.googlecode.wicket.jquery.core.behavior.DisplayNoneBehavior;
import com.googlecode.wicket.jquery.ui.widget.dialog.ButtonAjaxBehavior.ClickEvent;

/**
 * Base class for implementing jQuery dialogs
 *
 * @author Sebastien Briquet - sebfz1
 *
 * @param <T> the type of the model object
 */
public abstract class AbstractDialog<T extends Serializable> extends GenericPanel<T> implements IJQueryWidget, IDialogListener
{
	private static final long serialVersionUID = 1L;

	/* Default Button names */
	public static final String OK = "OK";
	public static final String NO = "NO";
	public static final String YES = "YES";
	public static final String CLOSE = "CLOSE";
	public static final String CANCEL = "CANCEL";
	public static final String SUBMIT = "SUBMIT";

	/* Default Button labels (cannot be ResourceModel because not wrapped to a component) */
	public static final IModel<String> LBL_OK = Model.of("Ok");
	public static final IModel<String> LBL_NO = Model.of("No");
	public static final IModel<String> LBL_YES = Model.of("Yes");
	public static final IModel<String> LBL_CLOSE = Model.of("Close");
	public static final IModel<String> LBL_CANCEL = Model.of("Cancel");
	public static final IModel<String> LBL_SUBMIT = Model.of("Submit");

	/** Default width */
	private static final int WIDTH = 450;

	private IModel<String> title;
	private boolean modal;
	private DialogBehavior widgetBehavior;

	/** Default button */
	private final DialogButton btnOk = new DialogButton(OK, LBL_OK);

	/**
	 * Constructor
	 *
	 * @param id the markupId, an html div suffice to host a dialog.
	 * @param title the title of the dialog
	 */
	public AbstractDialog(String id, String title)
	{
		this(id, title, null, true);
	}

	/**
	 * Constructor
	 *
	 * @param id the markupId, an html div suffice to host a dialog.
	 * @param title the title of the dialog
	 */
	public AbstractDialog(String id, IModel<String> title)
	{
		this(id, title, null, true);
	}

	/**
	 * Constructor
	 *
	 * @param id the markupId, an html div suffice to host a dialog.
	 * @param title the title of the dialog
	 * @param model the model to be used in the dialog.
	 */
	public AbstractDialog(String id, String title, IModel<T> model)
	{
		this(id, title, model, true);
	}

	/**
	 * Constructor
	 *
	 * @param id the markupId, an html div suffice to host a dialog.
	 * @param title the title of the dialog
	 * @param model the model to be used in the dialog.
	 */
	public AbstractDialog(String id, IModel<String> title, IModel<T> model)
	{
		this(id, title, model, true);
	}

	/**
	 * Constructor
	 *
	 * @param id the markupId, an html div suffice to host a dialog.
	 * @param title the title of the dialog
	 * @param modal indicates whether the dialog is modal
	 */
	public AbstractDialog(String id, String title, boolean modal)
	{
		this(id, title, null, modal);
	}

	/**
	 * Constructor
	 *
	 * @param id the markupId, an html div suffice to host a dialog.
	 * @param title the title of the dialog
	 * @param modal indicates whether the dialog is modal
	 */
	public AbstractDialog(String id, IModel<String> title, boolean modal)
	{
		this(id, title, null, modal);
	}

	/**
	 * Constructor
	 *
	 * @param id markupId, an html div suffice to host a dialog.
	 * @param title the title of the dialog
	 * @param modal indicates whether the dialog is modal
	 * @param model the model to be used in the dialog
	 */
	public AbstractDialog(String id, String title, IModel<T> model, boolean modal)
	{
		this(id, Model.of(title), model, modal);
	}

	/**
	 * Constructor
	 *
	 * @param id markupId, an html div suffice to host a dialog.
	 * @param title the title of the dialog
	 * @param modal indicates whether the dialog is modal
	 * @param model the model to be used in the dialog
	 */
	public AbstractDialog(String id, IModel<String> title, IModel<T> model, boolean modal)
	{
		super(id, model);

		this.title = title;
		this.modal = modal;

		this.add(new DisplayNoneBehavior()); // enhancement, fixes issue #22
		this.setOutputMarkupPlaceholderTag(true);
	}

	// Events //

	@Override
	protected void onInitialize()
	{
		super.onInitialize();

		// warning: ButtonAjaxBehavior(s) should be set at this point!
		this.widgetBehavior = (DialogBehavior) JQueryWidget.newWidgetBehavior(this);
		this.add(this.widgetBehavior);
	}

	@Override
	public void onConfigure(JQueryBehavior behavior)
	{
		// class options //
		behavior.setOption("autoOpen", false);
		behavior.setOption("title", Options.asString(this.title.getObject()));
		behavior.setOption("modal", this.isModal());
		behavior.setOption("resizable", this.isResizable());
		behavior.setOption("width", this.getWidth());
	}

	@Override
	public void onBeforeRender(JQueryBehavior behavior)
	{
		// noop
	}

	/**
	 * Triggered when the dialog opens
	 *
	 * @param handler the {@link IPartialPageRequestHandler}
	 */
	protected void onOpen(IPartialPageRequestHandler handler)
	{
	}

	/**
	 * Triggered when a button is clicked<br>
	 * This method may be overridden to handle button behaviors, but the dialog will not been closed until {@code super.onClick(event)} or {@link #close(IPartialPageRequestHandler, DialogButton)} is called.
	 */
	@Override
	public void onClick(AjaxRequestTarget target, DialogButton button)
	{
		this.close(target, button);
	}

	/**
	 * Internal onClick method, fired by the behavior<br>
	 * The purpose of this method is to prevent the behavior calling {@link #onClick(AjaxRequestTarget, DialogButton)} directly, as {@code onClick} is implemented by default
	 * 
	 * @param target the {@link AjaxRequestTarget}
	 * @param button the {@link DialogButton}
	 */
	void internalOnClick(AjaxRequestTarget target, DialogButton button)
	{
		this.onClick(target, button);
	}

	@Override
	protected void onDetach()
	{
		super.onDetach();

		this.title.detach(); // fixes #120
	}

	// Properties //

	/**
	 * Gets the dialog's buttons.<br>
	 * It is allowed to return a predefined list (ie: DialogButtons#OK_CANCEL#toList()) as long as the buttons state (enable and/or visible) are not modified<br>
	 * <b>Warning: </b>It is not legal to create the buttons to be returned in this method.
	 *
	 * @return {@link #btnOk} by default
	 */
	protected List<DialogButton> getButtons()
	{
		return Arrays.asList(this.btnOk);
	}

	/**
	 * Gets the dialog's with
	 *
	 * @return {@link #WIDTH} by default
	 */
	public int getWidth()
	{
		return AbstractDialog.WIDTH;
	}

	/**
	 * Gets the dialog's title
	 *
	 * @return the dialog's title
	 */
	public IModel<String> getTitle()
	{
		return this.title;
	}

	/**
	 * Sets the dialog's title
	 *
	 * @param title the dialog's title
	 */
	public void setTitle(IModel<String> title)
	{
		Args.notNull(title, "title");

		this.title = title;
	}

	/**
	 * Sets the dialog's title dynamically
	 *
	 * @param handler the {@link IPartialPageRequestHandler}
	 * @param title the dialog's title
	 */
	public void setTitle(IPartialPageRequestHandler handler, String title)
	{
		this.setTitle(handler, Model.of(title));
	}

	/**
	 * Sets the dialog's title dynamically
	 *
	 * @param handler the {@link IPartialPageRequestHandler}
	 * @param title the dialog's title
	 */
	public void setTitle(IPartialPageRequestHandler handler, IModel<String> title)
	{
		this.setTitle(title);

		handler.appendJavaScript(this.widgetBehavior.$(Options.asString("option"), Options.asString("title"), Options.asString(title.getObject())));
	}

	/**
	 * Gets the modal flag
	 *
	 * @return the modal flag supplied to the constructor by default
	 */
	public boolean isModal()
	{
		return this.modal;
	}

	/**
	 * Indicates whether the dialog is resizable
	 *
	 * @return false by default
	 */
	public boolean isResizable()
	{
		return false;
	}

	@Override
	public boolean isDefaultCloseEventEnabled()
	{
		return false;
	}

	@Override
	public boolean isEscapeCloseEventEnabled()
	{
		return false;
	}

	// Methods //

	/**
	 * Finds a {@link DialogButton} - identified by its name - within the list of buttons returned by {@link #getButtons()}
	 *
	 * @param name the button's name
	 * @return the {@link DialogButton} if found, null otherwise
	 */
	public DialogButton findButton(String name)
	{
		for (DialogButton button : this.getButtons())
		{
			if (button != null && button.match(name))
			{
				return button;
			}
		}

		return null;
	}

	/**
	 * Opens the dialogs in ajax.
	 *
	 * @param handler the {@link IPartialPageRequestHandler}
	 */
	public final void open(IPartialPageRequestHandler handler)
	{
		this.onOpen(handler);

		if (this.widgetBehavior != null)
		{
			this.widgetBehavior.open(handler);
		}
	}

	/**
	 * Closes the dialogs in ajax/websocket.
	 *
	 * @param handler the {@link IPartialPageRequestHandler}
	 * @param button the button that closes the dialog
	 */
	public final void close(IPartialPageRequestHandler handler, DialogButton button)
	{
		if (this.widgetBehavior != null)
		{
			this.widgetBehavior.close(handler);
		}

		this.onClose(handler, button);
	}

	// IJQueryWidget //

	/**
	 * @see IJQueryWidget#newWidgetBehavior(String)
	 */
	@Override
	public DialogBehavior newWidgetBehavior(String selector)
	{
		return new DialogBehavior(selector, this.newDialogListenerWrapper()) {

			private static final long serialVersionUID = 1L;

			@Override
			protected List<DialogButton> getButtons()
			{
				return AbstractDialog.this.getButtons();
			}

			@Override
			protected ButtonAjaxBehavior newButtonAjaxBehavior(IJQueryAjaxAware source, DialogButton button)
			{
				return AbstractDialog.this.newButtonAjaxBehavior(source, button);
			}
		};
	}

	// Factories //

	/**
	 * Gets a new {@link IDialogListener} that allow to redirect {@link IDialogListener#onClick(AjaxRequestTarget, DialogButton)} to {@link #internalOnClick(AjaxRequestTarget, DialogButton)}
	 * 
	 * @return a new {@link DialogListenerWrapper}
	 */
	protected final IDialogListener newDialogListenerWrapper()
	{
		return new DialogListenerWrapper(this) {

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target, DialogButton button)
			{
				AbstractDialog.this.internalOnClick(target, button);
			}
		};
	}

	/**
	 * Gets a new {@link ButtonAjaxBehavior} that will be called by the corresponding {@link DialogButton}.<br>
	 * This method may be overridden to provide additional behaviors
	 *
	 * @param source the {@link IJQueryAjaxAware} source
	 * @param button the button that is passed to the behavior so it can be retrieved via the {@link ClickEvent}
	 * @return the {@link ButtonAjaxBehavior}
	 */
	protected ButtonAjaxBehavior newButtonAjaxBehavior(IJQueryAjaxAware source, DialogButton button)
	{
		return new ButtonAjaxBehavior(source, button);
	}
}
