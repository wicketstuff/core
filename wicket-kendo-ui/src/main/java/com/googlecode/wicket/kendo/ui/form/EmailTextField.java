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
package com.googlecode.wicket.kendo.ui.form;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.model.IModel;
import org.apache.wicket.validation.IValidator;

import com.googlecode.wicket.jquery.core.IJQueryWidget;

/**
 * Provides a {@link org.apache.wicket.markup.html.form.EmailTextField} with the Kendo-ui style<br>
 * <br>
 * <b>Note:</b> {@link EmailTextField} is not a {@link IJQueryWidget} (no corresponding widget is supplied by Kendo UI)<br>
 * It means that required Kendo UI dependencies (javascript/stylesheet) are not automatically added.
 *
 * @author Sebastien Briquet - sebfz1
 *
 */
public class EmailTextField extends org.apache.wicket.markup.html.form.EmailTextField
{
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 */
	public EmailTextField(String id)
	{
		super(id);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param emailAddress the email input value
	 */
	public EmailTextField(String id, String emailAddress)
	{
		super(id, emailAddress);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param model the {@link IModel}
	 */
	public EmailTextField(String id, IModel<String> model)
	{
		super(id, model);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param model the {@link IModel}
	 * @param emailValidator the validator that will check the correctness of the input value
	 */
	public EmailTextField(String id, IModel<String> model, IValidator<String> emailValidator)
	{
		super(id, model, emailValidator);
	}

	// Events //

	@Override
	protected void onComponentTag(ComponentTag tag)
	{
		super.onComponentTag(tag);

		tag.append("class", "k-textbox", " ");

		if (!this.isEnabledInHierarchy())
		{
			tag.append("class", "k-state-disabled", " ");
		}
	}
}
