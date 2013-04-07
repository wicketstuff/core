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
package com.googlecode.wicket.jquery.core;

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

/**
 * Base class for a jQuery widget based on a {@link Panel}.
 *
 * @author Sebastien Briquet - sebfz1
 *
 */
public abstract class JQueryPanel extends Panel implements IJQueryWidget
{
	private static final long serialVersionUID = 1L;

	protected final Options options;

	/**
	 * Constructor
	 * @param id the markup id
	 */
	protected JQueryPanel(String id)
	{
		this(id, new Options());
	}

	/**
	 * Constructor
	 * @param id the markup id
	 * @param options the {@link Options}
	 */
	public JQueryPanel(String id, Options options)
	{
		super(id);

		this.options = options;
	}
	/**
	 * Constructor
	 * @param id the markup id
	 * @param model the {@link IModel}
	 */
	protected JQueryPanel(String id, IModel<?> model)
	{
		this(id, model, new Options());
	}

	/**
	 * Constructor
	 * @param id the markup id
	 * @param model the {@link IModel}
	 * @param options the {@link Options}
	 */
	public JQueryPanel(String id, IModel<?> model, Options options)
	{
		super(id, model);

		this.options = options;
	}
}
