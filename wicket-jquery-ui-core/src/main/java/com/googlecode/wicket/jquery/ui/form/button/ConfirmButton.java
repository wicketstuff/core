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

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.googlecode.wicket.jquery.ui.JQueryIcon;
import com.googlecode.wicket.jquery.ui.panel.FormSubmittingPanel;
import com.googlecode.wicket.jquery.ui.widget.dialog.AbstractDialog;
import com.googlecode.wicket.jquery.ui.widget.dialog.DialogButton;
import com.googlecode.wicket.jquery.ui.widget.dialog.DialogButtons;
import com.googlecode.wicket.jquery.ui.widget.dialog.DialogIcon;
import com.googlecode.wicket.jquery.ui.widget.dialog.MessageDialog;

/**
 * Provides a {@link AjaxButton} which pop-ups an OK-Cancel confirmation dialog when clicked. In case of confirmation, the form is sent via a http submit.<br/>
 * <br/>
 * <b>Note: </b> this component is not an {@link Button} itself but a Panel, it should not be attached to a &lt;button /&gt;; it can be attached on a &lt;div /&gt; or a &lt;span /&gt; for instance.<br/>
 * <br/>
 * <b>Warning: </b> it is not possible to get a form component value - that is going to be changed - to be displayed in the dialog box message. The reason is that in order to get a form component (updated) model object, the form component should be validated. The dialog does not proceed to a (whole) form validation while being opened, because the form validation will occur when the user will confirm (by clicking on OK button). This the intended behavior.
 *
 * @author Sebastien Briquet - sebfz1
 */
public abstract class ConfirmButton extends FormSubmittingPanel<String>
{
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 * @param id markup id
	 * @param label the button text
	 * @param title the dialog title
	 * @param message the dialog message, also acts as model object
	 */
	public ConfirmButton(String id, String label, String title, String message)
	{
		this(id, label, title, new Model<String>(message));
	}

	/**
	 * Constructor
	 * @param id markup id
	 * @param label the button text
	 * @param title the dialog title
	 * @param message the dialog message, also acts as model object
	 */
	public ConfirmButton(String id, String label, String title, IModel<String> message)
	{
		super(id, message);

		final AbstractDialog<?> dialog = this.newDialog("dialog", title, this.getModel());
		this.add(dialog);

		final AjaxButton button = new AjaxButton("button") {

			private static final long serialVersionUID = 1L;

			@Override
			protected JQueryIcon getIcon()
			{
				return ConfirmButton.this.getIcon();
			}

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form)
			{
				dialog.open(target);
			}
		};

		this.add(button.setDefaultFormProcessing(false)); //does not validate the form before the dialog is being displayed

		button.add(new Label("label", new Model<String>(label)).setRenderBodyOnly(true));
	}

	// Properties //
	/**
	 * Gets the icon being displayed in the button
	 * @return the {@link JQueryIcon}
	 */
	protected JQueryIcon getIcon()
	{
		return JQueryIcon.ALERT;
	}

	// Factories //
	/**
	 * Create the dialog instance<br/>
	 * <b>Warning:</b> to be overridden with care!
	 *
	 * @param id the markupId
	 * @param title the title of the dialog
	 * @param message the message to be displayed
	 * @return the dialog instance
	 */
	protected AbstractDialog<?> newDialog(String id, String title, IModel<String> message)
	{
		return new MessageDialog(id, title, message, DialogButtons.OK_CANCEL, DialogIcon.WARN) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onClose(AjaxRequestTarget target, DialogButton button)
			{
				if (button != null && button.equals(LBL_OK))
				{
					ConfirmButton.this.submit(target);
				}
			}
		};
	}
}
