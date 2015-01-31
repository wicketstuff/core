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
package com.googlecode.wicket.jquery.core.panel;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IFormSubmittingComponent;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;

import com.googlecode.wicket.jquery.core.IJQueryWidget.JQueryWidget;

/**
 * Provides a <code>GenericPanel</code> that implements {@link IFormSubmittingComponent}, so it is able to perform a form submit trough HTTP
 *
 * @author Sebastien Briquet - sebfz1
 *
 * @param <T> the object model type
 */
public abstract class FormSubmittingPanel<T> extends GenericPanel<T> implements IFormSubmittingComponent
{
	private static final long serialVersionUID = 1L;

	/**
	 * If false, all standard processing like validating and model updating is skipped.
	 *
	 * @see IFormSubmittingComponent
	 */
	private boolean processForm = true;

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 */
	public FormSubmittingPanel(String id)
	{
		super(id);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param model the {@link IModel}
	 */
	public FormSubmittingPanel(String id, IModel<T> model)
	{
		super(id, model);
	}

	// Methods //

	/**
	 * Performs a form submit through the target
	 *
	 * @param target the {@link AjaxRequestTarget}
	 */
	protected void submit(AjaxRequestTarget target)
	{
		/**
		 * As the Form is posted, Form#findSubmittingButton() expects to retrieve this component by the request parameter 'name'.<br/>
		 * But this component (panel) is not an input, so it does not have a name attribute. <br/>
		 * The name should mach the #getInputName() path
		 */
		String input = String.format("<input type=\"hidden\" name=\"%s\" value=\"\" />", this.getInputName());

		target.appendJavaScript(String.format("jQuery('%s').append('%s').submit();", JQueryWidget.getSelector(this.getForm()), input)); // not tested in nested forms
	}

	// IFormSubmittingComponent //

	@Override
	public Form<?> getForm()
	{
		return Form.findForm(this);
	}

	/**
	 * Gets the input element name that is supposed to be retrieved in the form once it has been submitted over http.
	 */
	@Override
	public String getInputName()
	{
		return Form.getRootFormRelativeId(this);
	}

	@Override
	public final boolean getDefaultFormProcessing()
	{
		return this.processForm;
	}

	@Override
	public final FormSubmittingPanel<T> setDefaultFormProcessing(boolean processForm)
	{
		this.processForm = processForm;

		return this;
	}

	@Override
	public void onAfterSubmit()
	{
		// wicket6
	}
}
