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
package com.googlecode.wicket.jquery.ui.kendo.editor;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.model.IModel;

import com.googlecode.wicket.jquery.core.IJQueryWidget;
import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.ui.kendo.KendoAbstractBehavior;

/**
 * Provides a Kendo UI Editor widget.<br/>
 * It should be created on a HTML &lt;textarea /&gt; element
 *
 * @author Sebastien Briquet - sebfz1
 *
 * @param <T> the object model type
 */
public class Editor<T> extends TextArea<T> implements IJQueryWidget
{
	private static final long serialVersionUID = 1L;
	private static final String METHOD = "kendoEditor";

	private final Options options;

	/**
	 * Constructor that provides a default {@link Options} that indicates the {@link Editor}
	 * should submit encoded HTML tags (<code>{ encoded: false }</code>),
	 * and sets the {@link #setEscapeModelStrings(boolean)} to false.
	 *
	 * @param id the markup id
	 */
	public Editor(String id)
	{
		this(id, new Options("encoded", false));
		this.setEscapeModelStrings(false);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param options the {@link Options}
	 */
	public Editor(String id, Options options)
	{
		super(id);

		this.options = options;
	}

	/**
	 * Constructor that provides a default {@link Options} that indicates the {@link Editor}
	 * should submit encoded HTML tags (<code>{ encoded: false }</code>),
	 * and sets the {@link #setEscapeModelStrings(boolean)} to false.
	 *
	 * @param id the markup id
	 * @param model the {@link IModel}
	 */
	public Editor(String id, IModel<T> model)
	{
		this(id, model, new Options("encoded", false));
		this.setEscapeModelStrings(false);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param model the {@link IModel}
	 * @param options the {@link Options}
	 */
	public Editor(String id, IModel<T> model, Options options)
	{
		super(id, model);

		this.options = options;
	}

	@Override
	protected void onInitialize()
	{
		super.onInitialize();

		this.add(JQueryWidget.newWidgetBehavior(this));
	}

	/**
	 * Called immediately after the onConfigure method in a behavior. Since this is before the rendering
	 * cycle has begun, the behavior can modify the configuration of the component (i.e. {@link Options})
	 *
	 * @param behavior the {@link JQueryBehavior}
	 */
	protected void onConfigure(JQueryBehavior behavior)
	{
		behavior.setOptions(this.options);
	}

	// IJQueryWidget //
	@Override
	public JQueryBehavior newWidgetBehavior(String selector)
	{
		return new KendoAbstractBehavior(selector, Editor.METHOD) {

			private static final long serialVersionUID = 1L;

			@Override
			public void onConfigure(Component component)
			{
				Editor.this.onConfigure(this);
			}
		};
	}

}
