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
package com.googlecode.wicket.kendo.ui.widget;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;

import com.googlecode.wicket.jquery.core.resource.StyleSheetPackageHeaderItem;
import com.googlecode.wicket.kendo.ui.KendoIcon;
import com.googlecode.wicket.kendo.ui.form.button.AjaxButton;

/**
 * Provides a simple navigation panel, having 'backward' &#38; 'forward' buttons
 *
 * @author Sebastien Briquet - sebfz1
 */
public class NavigationPanel extends Panel
{
	private static final long serialVersionUID = 1L;
	private final AjaxButton backwardButton;
	private final AjaxButton forwardButton;

	/**
	 * Contructor
	 * 
	 * @param id the markup id
	 */
	public NavigationPanel(String id)
	{
		super(id);

		this.setOutputMarkupId(true);

		// form //
		final Form<Void> form = this.newForm("form");
		this.add(form);

		// buttons //
		this.backwardButton = this.newBackwardButton();
		form.add(this.backwardButton);

		this.forwardButton = this.newForwardButton();
		form.add(this.forwardButton);
	}

	// methods //

	@Override
	public void renderHead(IHeaderResponse response)
	{
		super.renderHead(response);

		response.render(new StyleSheetPackageHeaderItem(NavigationPanel.class));
	}

	// properties //

	/**
	 * Gets the 'backward' {@link AjaxButton}
	 * 
	 * @return the 'backward' {@code AjaxButton}
	 */
	public final AjaxButton getBackwardButton()
	{
		return this.backwardButton;
	}

	/**
	 * Gets the 'forward' {@link AjaxButton}
	 * 
	 * @return the 'forward' {@code AjaxButton}
	 */
	public final AjaxButton getForwardButton()
	{
		return this.forwardButton;
	}

	// factories //

	/**
	 * Gets a new {@link Form}
	 * 
	 * @param id the markup id
	 * @return a new {@code Form}
	 */
	private Form<Void> newForm(String id)
	{
		return new Form<Void>(id) {

			private static final long serialVersionUID = 1L;

			@Override
			protected boolean wantSubmitOnParentFormSubmit()
			{
				return false;
			}
		};
	}

	/**
	 * Gets a new 'backward' {@link AjaxButton}
	 * 
	 * @return a new 'backward' {@code AjaxButton}
	 */
	private final AjaxButton newBackwardButton()
	{
		return new AjaxButton("backward") { // NOSONAR

			private static final long serialVersionUID = 1L;

			@Override
			protected String getIcon()
			{
				return KendoIcon.ARROW_60_LEFT;
			}

			@Override
			protected void onSubmit(AjaxRequestTarget target)
			{
				NavigationPanel.this.onBackward(target, this);
			}
		};
	}

	/**
	 * Gets a new 'forward' {@link AjaxButton}
	 * 
	 * @return a new 'forward' {@code AjaxButton}
	 */
	private final AjaxButton newForwardButton()
	{
		return new AjaxButton("forward") { // NOSONAR

			private static final long serialVersionUID = 1L;

			@Override
			protected String getIcon()
			{
				return KendoIcon.ARROW_60_RIGHT;
			}

			@Override
			protected void onSubmit(AjaxRequestTarget target)
			{
				NavigationPanel.this.onForward(target, this);
			}
		};
	}

	/**
	 * Triggered when the 'backward' button has been clicked (and the form has no error)
	 * 
	 * @param target the {@link AjaxRequestTarget}
	 * @param button the {@link AjaxButton}
	 */
	protected void onBackward(AjaxRequestTarget target, AjaxButton button)
	{
		// noop
	}

	/**
	 * Triggered when the 'forward' button has been clicked (and the form has no error)
	 * 
	 * @param target the {@link AjaxRequestTarget}
	 * @param button the {@link AjaxButton}
	 */
	protected void onForward(AjaxRequestTarget target, AjaxButton button)
	{
		// noop
	}
}
