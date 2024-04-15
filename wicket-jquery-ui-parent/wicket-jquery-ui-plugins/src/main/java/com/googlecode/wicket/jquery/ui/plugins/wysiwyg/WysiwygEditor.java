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
package com.googlecode.wicket.jquery.ui.plugins.wysiwyg;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnLoadHeaderItem;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponentPanel;
import org.apache.wicket.markup.html.form.HiddenField;
import org.apache.wicket.model.IModel;
import org.owasp.html.HtmlPolicyBuilder;
import org.owasp.html.PolicyFactory;

import com.googlecode.wicket.jquery.core.IJQueryWidget;
import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.ui.plugins.wysiwyg.toolbar.IWysiwygToolbar;

/**
 * Provides an implementation of bootstrap-wysiwyg<br>
 * {@link WysiwygEditor} should be contained in a {@link Form} in order to work properly
 *
 * @author sebfz1
 * @author solomax
 * @author andunslg
 */
public class WysiwygEditor extends FormComponentPanel<String> implements IJQueryWidget
{
	private static final long serialVersionUID = 1L;

	private HiddenField<String> textarea;
	private final WebMarkupContainer container;

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 */
	public WysiwygEditor(String id)
	{
		this(id, null, null);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param toolbar the {@link IWysiwygToolbar}
	 */
	public WysiwygEditor(String id, IWysiwygToolbar toolbar)
	{
		this(id, null, toolbar);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param model the {@link IModel}
	 */
	public WysiwygEditor(String id, IModel<String> model)
	{
		this(id, model, null);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param model the {@link IModel}
	 * @param toolbar the {@link IWysiwygToolbar}
	 */
	public WysiwygEditor(String id, IModel<String> model, IWysiwygToolbar toolbar)
	{
		super(id, model);

		this.container = this.newMarkupContainer();
		this.add(this.container);

		if (toolbar != null)
		{
			toolbar.attachToEditor(this.container);
		}

		this.setEscapeModelStrings(false); // fixes #121
	}

	// Properties //

	/**
	 * Gets the editor markup-id
	 *
	 * @return the editor markup-id
	 */
	public String getEditorMarkupId()
	{
		return this.container.getMarkupId();
	}

	// Methods //

	@Override
	public void convertInput()
	{
		final PolicyFactory policy = this.newPolicyFactory();
		final String input = this.textarea.getConvertedInput();

		this.setConvertedInput(policy.sanitize(input));
	}

	@Override
	public void renderHead(IHeaderResponse response)
	{
		super.renderHead(response);

		response.render(OnLoadHeaderItem.forScript(String.format("wysiwygAddTextAreaMapper('%s', '%s')", this.container.getMarkupId(), this.textarea.getMarkupId())));
	}

	// Events //

	@Override
	protected void onInitialize()
	{
		super.onInitialize();

		this.textarea = new HiddenField<>("textarea", this.getModel());
		this.textarea.setOutputMarkupId(true);
		this.textarea.setEscapeModelStrings(false);
		this.add(this.textarea);

		this.add(JQueryWidget.newWidgetBehavior(this, this.container));
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
		return new WysiwygBehavior(selector);
	}

	// Factories //

	private WebMarkupContainer newMarkupContainer()
	{
		return new WebMarkupContainer("container") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onComponentTag(ComponentTag tag)
			{
				super.onComponentTag(tag);

				if (!WysiwygEditor.this.getForm().isEnabled())
				{
					tag.put("contenteditable", "false");
				}
			}
		};
	}

	/**
	 * Gets a new {@link PolicyFactory} to sanitize editor input
	 *
	 * @return a new {@code PolicyFactory}
	 */
	protected PolicyFactory newPolicyFactory()
	{
		return new HtmlPolicyBuilder() // lf
				.allowCommonInlineFormattingElements() // lf
				.allowCommonBlockElements() // lf
				.allowElements("a").allowStandardUrlProtocols() // lf
				.allowAttributes("href", "target").onElements("a") // lf
				.allowAttributes("size").onElements("font") // lf
				.allowAttributes("class", "style").globally() // lf
				.toFactory();
	}
}
