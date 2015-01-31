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
package com.googlecode.wicket.kendo.ui.form.button;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.googlecode.wicket.kendo.ui.KendoIcon;
import com.googlecode.wicket.kendo.ui.widget.window.AbstractWindow;
import com.googlecode.wicket.kendo.ui.widget.window.MessageWindow;
import com.googlecode.wicket.kendo.ui.widget.window.Window;
import com.googlecode.wicket.kendo.ui.widget.window.WindowButton;
import com.googlecode.wicket.kendo.ui.widget.window.WindowButtons;

/**
 * Provides a {@link AjaxButton} which pop-ups an OK-Cancel confirmation dialog when clicked. In case of confirmation, the form is sent via an ajax post.<br/>
 * <br/>
 * <b>Note: </b> this component is not a {@link AjaxButton} itself but a Panel, it should not be attached to a &lt;button /&gt;; it can be attached on a &lt;div /&gt; or a &lt;span /&gt; for instance.<br/>
 * <br/>
 * <b>Warning: </b> it is not possible to get a form component value - that is going to be changed - to be displayed in the dialog box message. The reason is that in order to get a form component (updated) model object, the form component
 * should be validated. The dialog does not proceed to a (whole) form validation while being opened, because the form validation will occur when the user will confirm (by clicking on OK button). This the intended behavior.
 *
 * @author Sebastien Briquet - sebfz1
 *
 */
public abstract class ConfirmAjaxButton extends GenericPanel<String>
{
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 *
	 * @param id markup id
	 * @param label the button text
	 * @param title the dialog title
	 * @param message the dialog message
	 */
	public ConfirmAjaxButton(String id, String label, String title, String message)
	{
		this(id, Model.of(label), Model.of(title), Model.of(message));
	}

	/**
	 * Constructor
	 *
	 * @param id markup id
	 * @param label the button text
	 * @param title the dialog title
	 * @param message the dialog message
	 */
	public ConfirmAjaxButton(String id, IModel<String> label, IModel<String> title, IModel<String> message)
	{
		super(id, message);

		final AbstractWindow<?> window = this.newWindow("window", title, this.getModel());
		this.add(window);

		final AjaxButton button = new AjaxButton("button") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onInitialize()
			{
				super.onInitialize();

				// does not validate the form before the window is being displayed
				this.setDefaultFormProcessing(false);
			}

			@Override
			protected String getIcon()
			{
				return ConfirmAjaxButton.this.getIcon();
			}

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form)
			{
				window.open(target);
			}
		};

		this.add(button);

		button.add(new Label("label", label).setRenderBodyOnly(true));
	}

	// Properties //
	/**
	 * Gets the icon being displayed in the button
	 *
	 * @return the {@link KendoIcon}
	 */
	protected String getIcon()
	{
		return KendoIcon.NOTE;
	}

	// Events //

	/**
	 * Triggered when the form is submitted, but the validation failed
	 * 
	 * @param target the {@link AjaxRequestTarget}
	 */
	protected abstract void onError(AjaxRequestTarget target);

	/**
	 * Triggered when the form is submitted, and the validation succeed
	 * 
	 * @param target the {@link AjaxRequestTarget}
	 */
	protected abstract void onSubmit(AjaxRequestTarget target);

	/**
	 * Triggered when the cancel button has been clicked
	 * 
	 * @param target the {@link AjaxRequestTarget}
	 */
	protected abstract void onCancel(AjaxRequestTarget target);

	// Factories //

	/**
	 * Create the window instance<br/>
	 * <b>Warning:</b> to be overridden with care!
	 *
	 * @param id the markup id
	 * @param title the title of the dialog
	 * @param message the message to be displayed
	 * @return the dialog instance
	 */
	protected Window<?> newWindow(String id, IModel<String> title, IModel<String> message)
	{
		return new MessageWindow(id, title, message, WindowButtons.OK_CANCEL, KendoIcon.NOTE) {

			private static final long serialVersionUID = 1L;
			
			@Override
			protected Form<?> getForm()
			{
				return Form.findForm(ConfirmAjaxButton.this);
			}

			@Override
			protected void onError(AjaxRequestTarget target, WindowButton button)
			{
				super.close(target); // closes the window on error.

				ConfirmAjaxButton.this.onError(target);
			}

			@Override
			public void onSubmit(AjaxRequestTarget target, WindowButton button)
			{
				if (button != null)
				{
					if (button.match(LBL_OK))
					{
						ConfirmAjaxButton.this.onSubmit(target);
					}

					if (button.match(LBL_CANCEL))
					{
						ConfirmAjaxButton.this.onCancel(target);
					}
				}
			}
		};
	}
}
