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

import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.lang.Args;
import org.owasp.html.HtmlPolicyBuilder;
import org.owasp.html.PolicyFactory;

import com.googlecode.wicket.jquery.core.IJQueryWidget;
import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.kendo.ui.KendoUIBehavior;

/**
 * Provides the Kendo UI Editor base widget.<br>
 * It should be created on a HTML &lt;textarea /&gt; element
 *
 * @author Sebastien Briquet - sebfz1
 */
public abstract class AbstractEditor<T> extends TextArea<T> implements IJQueryWidget
{
	private static final long serialVersionUID = 1L;
	public static final String METHOD = "kendoEditor";

	protected final Options options;

	/**
	 * Constructor that provides a default {@link Options} that indicates the {@link AbstractEditor} should submit encoded HTML tags (<code>{ encoded: false }</code>)
	 *
	 * @param id the markup id
	 */
	public AbstractEditor(String id)
	{
		this(id, new Options("encoded", false));
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param options the {@link Options}
	 */
	public AbstractEditor(String id, Options options)
	{
		super(id);

		this.options = Args.notNull(options, "options");
	}

	/**
	 * Constructor that provides a default {@link Options} that indicates the {@link AbstractEditor} should submit encoded HTML tags (<code>{ encoded: false }</code>)
	 *
	 * @param id the markup id
	 * @param model the {@link IModel}
	 */
	public AbstractEditor(String id, IModel<T> model)
	{
		this(id, model, new Options("encoded", false));
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param model the {@link IModel}
	 * @param options the {@link Options}
	 */
	public AbstractEditor(String id, IModel<T> model, Options options)
	{
		super(id, model);

		this.options = Args.notNull(options, "options");
	}

	// Methods //

	// Events //

	@Override
	protected void onInitialize()
	{
		super.onInitialize();

		this.setEscapeModelStrings(false);
		this.add(JQueryWidget.newWidgetBehavior(this));
	}

	@Override
	public void onConfigure(JQueryBehavior behavior)
	{
		// noop
	}

	@Override
	public void onBeforeRender(JQueryBehavior behavior)
	{
		// noop
	}

	// IJQueryWidget //

	@Override
	public JQueryBehavior newWidgetBehavior(String selector)
	{
		return new KendoUIBehavior(selector, AbstractEditor.METHOD, this.options);
	}

	// Factories //

	/**
	 * Gets a new {@link PolicyFactory} to sanitize editor input
	 * 
	 * @return a new {@code PolicyFactory}
	 */
	protected PolicyFactory newPolicyFactory()
	{
		return new HtmlPolicyBuilder() // lf
				.allowStyling() // lf
				.allowCommonBlockElements() // lf
				.allowCommonInlineFormattingElements() // lf
				.allowElements("a").allowAttributes("href", "target").onElements("a") // lf
				.allowElements("table", "tbody", "thead", "th", "tr", "td") // lf
				.allowAttributes("size").onElements("font") // lf
				.allowAttributes("class", "style").globally() // lf
				.toFactory();
	}
}
