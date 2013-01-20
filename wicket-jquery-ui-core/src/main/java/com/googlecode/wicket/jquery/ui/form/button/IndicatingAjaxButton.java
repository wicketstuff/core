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

import org.apache.wicket.ajax.IAjaxIndicatorAware;
import org.apache.wicket.extensions.ajax.markup.html.AjaxIndicatorAppender;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;

/**
 * Provides a jQuery button based on the built-in AjaxButton, with an ajax indicator the time the {@link #onSubmit()} process.
 * <b>Note</b> Designed for version 6+
 *
 * @author Sebastien Briquet - sebfz1
 */
public abstract class IndicatingAjaxButton extends AjaxButton implements IAjaxIndicatorAware
{
	private static final long serialVersionUID = 1L;
	private final AjaxIndicatorAppender indicatorAppender = new AjaxIndicatorAppender();

	/**
	 * Constructor
	 * @param id the markup id
	 */
	public IndicatingAjaxButton(String id)
	{
		super(id);
		this.init();
	}

	/**
	 * Constructor
	 * @param id the markup id
	 * @param form the {@link Form}
	 */
	public IndicatingAjaxButton(String id, Form<?> form)
	{
		super(id, form);
		this.init();
	}

	/**
	 * Constructor
	 * @param id the markup id
	 * @param model the {@link IModel}
	 */
	public IndicatingAjaxButton(String id, IModel<String> model)
	{
		super(id, model);
		this.init();
	}

	/**
	 * Constructor
	 * @param id the markup id
	 * @param model the {@link IModel}
	 * @param form the {@link Form}
	 */
	public IndicatingAjaxButton(String id, IModel<String> model, Form<?> form)
	{
		super(id, model, form);
		this.init();
	}


	/**
	 * Initialization
	 */
	private void init()
	{
		this.add(this.indicatorAppender);
	}

	/**
	 * @see IAjaxIndicatorAware#getAjaxIndicatorMarkupId()
	 * @return the markup id of the ajax indicator
	 *
	 */
	@Override
	public String getAjaxIndicatorMarkupId()
	{
		return this.indicatorAppender.getMarkupId();
	}
}
