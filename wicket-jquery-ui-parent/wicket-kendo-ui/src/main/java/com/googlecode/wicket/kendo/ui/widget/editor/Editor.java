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
package com.googlecode.wicket.kendo.ui.widget.editor;

import org.apache.wicket.model.IModel;
import org.owasp.html.PolicyFactory;

import com.googlecode.wicket.jquery.core.IJQueryWidget;
import com.googlecode.wicket.jquery.core.Options;

/**
 * Provides a Kendo UI Editor widget.<br>
 * It should be created on a HTML &lt;textarea /&gt; element
 *
 * @author Sebastien Briquet - sebfz1
 */
public class Editor extends AbstractEditor<String> implements IJQueryWidget // NOSONAR
{
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor that provides a default {@link Options} that indicates the {@link Editor} should submit encoded HTML tags (<code>{ encoded: false }</code>)
	 *
	 * @param id the markup id
	 */
	public Editor(String id)
	{
		super(id);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param options the {@link Options}
	 */
	public Editor(String id, Options options)
	{
		super(id, options);
	}

	/**
	 * Constructor that provides a default {@link Options} that indicates the {@link Editor} should submit encoded HTML tags (<code>{ encoded: false }</code>)
	 *
	 * @param id the markup id
	 * @param model the {@link IModel}
	 */
	public Editor(String id, IModel<String> model)
	{
		super(id, model);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param model the {@link IModel}
	 * @param options the {@link Options}
	 */
	public Editor(String id, IModel<String> model, Options options)
	{
		super(id, model, options);
	}

	// Methods //

	@Override
	public void convertInput()
	{
		super.convertInput();

		final PolicyFactory policy = this.newPolicyFactory();
		final String input = this.getConvertedInput();

		this.setConvertedInput(policy.sanitize(input));
	}
}
