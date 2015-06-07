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

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.RepeatingView;

import com.googlecode.wicket.kendo.ui.form.button.AjaxButton;

/**
 * Provides a {@link Panel} of {@link WindowButton}{@code s} to be used in {@link Window}
 * 
 * @author Sebastien Briquet - sebfz1
 */
public abstract class WindowButtonPanel extends Panel
{
	private static final long serialVersionUID = 1L;

	public WindowButtonPanel(final String id, final List<WindowButton> buttons)
	{
		super(id);

		RepeatingView view = new RepeatingView("buttons");
		this.add(view);

		for (WindowButton button : buttons)
		{
			view.add(this.newAjaxButton(view.newChildId(), button));
		}
	}

	// Properties /

	/**
	 * Gets the {@link Form} that should be submitted and validated
	 * 
	 * @return the {@link Form}
	 */
	protected abstract Form<?> getForm();

	// Events //

	/**
	 * Triggered when the form is submitted, but the validation failed
	 * 
	 * @param target the {@link AjaxRequestTarget}
	 * @param button the {@link WindowButton}
	 */
	protected abstract void onError(AjaxRequestTarget target, WindowButton button);

	/**
	 * Triggered when the form is submitted, and the validation succeed
	 * 
	 * @param target the {@link AjaxRequestTarget}
	 * @param button the {@link WindowButton}
	 */
	protected abstract void onSubmit(AjaxRequestTarget target, WindowButton button);

	/**
	 * Triggered after the form is submitted, and the validation succeed
	 * 
	 * @param target the {@link AjaxRequestTarget}
	 * @param button the {@link WindowButton}
	 */
	protected abstract void onAfterSubmit(AjaxRequestTarget target, WindowButton button);

	// Factories //

	protected final AjaxButton newAjaxButton(final String id, final WindowButton button)
	{
		return new AjaxButton(id, this.getForm()) {

			private static final long serialVersionUID = 1L;

			// Properties //

			@Override
			protected String getIcon()
			{
				return button.getIcon();
			}

			@Override
			public boolean isEnabled()
			{
				return button.isEnabled();
			}

			@Override
			public boolean isVisible()
			{
				return button.isVisible();
			}

			// Events //

			@Override
			protected void onInitialize()
			{
				super.onInitialize();

				this.add(new Label("text", button.getModel()).setRenderBodyOnly(true));
			}

			@Override
			protected void onConfigure()
			{
				super.onConfigure();

				this.setDefaultFormProcessing(button.getDefaultFormProcessing());
			}

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form)
			{
				WindowButtonPanel.this.onSubmit(target, button);
			}

			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form)
			{
				WindowButtonPanel.this.onError(target, button);
			}

			@Override
			protected void onAfterSubmit(AjaxRequestTarget target, Form<?> form)
			{
				WindowButtonPanel.this.onAfterSubmit(target, button);
			}
		};
	}
}
