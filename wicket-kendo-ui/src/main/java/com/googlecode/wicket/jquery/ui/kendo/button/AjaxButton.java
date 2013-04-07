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
package com.googlecode.wicket.jquery.ui.kendo.button;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;

/**
 * Provides a jQuery button based on the built-in AjaxButton
 *
 * @author Sebastien Briquet - sebfz1
 *
 */
public abstract class AjaxButton extends org.apache.wicket.ajax.markup.html.form.AjaxButton
{
	private static final long serialVersionUID = 1L;
	private static final String CSS_CLASS = "k-button";

	/**
	 * Constructor
	 * @param id the markup id
	 */
	public AjaxButton(String id)
	{
		super(id);
	}

	/**
	 * Constructor
	 * @param id the markup id
	 * @param form the {@link Form}
	 */
	public AjaxButton(String id, Form<?> form)
	{
		super(id, form);
	}

	/**
	 * Constructor
	 * @param id the markup id
	 * @param model the {@link IModel}
	 */
	public AjaxButton(String id, IModel<String> model)
	{
		super(id, model);
	}

	/**
	 * Constructor
	 * @param id the markup id
	 * @param model the {@link IModel}
	 * @param form the {@link Form}
	 */
	public AjaxButton(String id, IModel<String> model, Form<?> form)
	{
		super(id, model, form);
	}

	// Events //
	@Override
	protected void onInitialize()
	{
		super.onInitialize();

		this.add(AttributeModifier.replace("class", CSS_CLASS));
	}

	@Override
	protected void onError(AjaxRequestTarget target, Form<?> form)
	{
	}
}
