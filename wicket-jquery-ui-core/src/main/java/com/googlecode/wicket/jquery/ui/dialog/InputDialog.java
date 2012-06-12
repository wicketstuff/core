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
package com.googlecode.wicket.jquery.ui.dialog;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;

/**
 * Provides a modal dialog box that ask an input to the user
 * 
 * @author Sebastien Briquet - sebfz1
 *
 * @param <T> the type of the model object of the {@link RequiredTextField}
 */
public abstract class InputDialog<T extends Serializable> extends AbstractFormDialog<T>
{
	private static final long serialVersionUID = 1L;
	
	private final Form<?> form;
	
	/**
	 * Constructor supplying a new default model.
	 * @param id the markupId, an html div suffice to host a dialog.
	 * @param title the title of the dialog 
	 * @param label text that will be displayed in front of the text field.
	 */
	public InputDialog(String id, String title, String label)
	{
		this(id, title, label, new Model<T>());
	}

	/**
	 *  
	 * @param id the markupId, an html div suffice to host a dialog.
	 * @param title the title of the dialog 
	 * @param label text that will be displayed in front of the text field.
	 * @param model the model to be used
	 */
	public InputDialog(String id, String title, String label, IModel<T> model)
	{
		super(id, title, model, true);

		this.form = new Form<T>("form");
		this.add(this.form);

		this.form.add(new Label("label", label));
		this.form.add(new RequiredTextField<T>("input", this.getModel()));
		
		FeedbackPanel feedback = new JQueryFeedbackPanel("feedback", this.form.get("input"));
		this.form.add(feedback.setOutputMarkupId(true));
	}

	@Override
	public Form<?> getForm()
	{
		return this.form;
	}
	
	@Override
	public boolean isResizable()
	{
		return true;
	}
	
	@Override
	protected List<String> getButtons()
	{
		return Arrays.asList(BTN_OK, BTN_CANCEL);
	}
	
	@Override
	protected String getSubmitButton()
	{
		return BTN_OK;
	}
	
	@Override
	protected void onOpen(AjaxRequestTarget target)
	{
		//re-attach the feedback panel to clear previously displayed error message(s)
		target.add(this.form.get("feedback"));
	}

	@Override
	public final void onError(AjaxRequestTarget target)
	{
		target.add(this.form.get("feedback"));
	}
}
