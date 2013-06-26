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

import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnLoadHeaderItem;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponentPanel;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.googlecode.wicket.jquery.core.IJQueryWidget;
import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.ui.plugins.wysiwyg.toolbar.IWysiwygToolbar;

/**
 * Provides an implementation of bootstrap-wysiwyg<br/>
 * {@link WysiwygEditor} should be contained in a {@link Form} in order to work properly
 *
 * @author sebfz1
 * @author solomax
 * @author andunslg
 */
public class WysiwygEditor extends FormComponentPanel<String> implements IJQueryWidget
{
	private static final long serialVersionUID = 1L;

	private final TextArea<String> textarea;
	private final WebMarkupContainer container;

	/**
	 * Constructor
	 *
	 * @param id the markup-id
	 */
	public WysiwygEditor(String id)
	{
		this(id, null, null);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup-id
	 * @param toolbar the {@link IWysiwygToolbar}
	 */
	public WysiwygEditor(String id, IWysiwygToolbar toolbar)
	{
		this(id, null, toolbar);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup-id
	 * @param model the {@link IModel}
	 */
	public WysiwygEditor(String id, IModel<String> model)
	{
		this(id, model, null);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup-id
	 * @param model the {@link IModel}
	 * @param toolbar the {@link IWysiwygToolbar}
	 */
	public WysiwygEditor(String id, IModel<String> model, IWysiwygToolbar toolbar)
	{
		super(id, model);

		this.container = new WebMarkupContainer("container"); //widget component
		this.add(this.container);

		this.textarea = new TextArea<String>("textarea", Model.of(this.getModelObject()));
		this.add(this.textarea.setOutputMarkupId(true));

		if (toolbar != null)
		{
			toolbar.attachToEditor(this.container);
		}
	}


	// Properties //
	/**
	 * Gets the editor markup-id
	 * @return the editor markup-id
	 */
	public String getEditorMarkupId()
	{
		return this.container.getMarkupId();
	}


	// Methods //
	@Override
	protected void convertInput()
	{
		this.setConvertedInput(this.textarea.getConvertedInput());
	}

	@Override
	public void renderHead(IHeaderResponse response)
	{
		super.renderHead(response);

		response.render(OnLoadHeaderItem.forScript(String.format("addTextAreaMapper('%s', '%s');", this.container.getMarkupId(), this.textarea.getMarkupId())));
	}


	// Events //
	@Override
	protected void onInitialize()
	{
		super.onInitialize();

		this.add(JQueryWidget.newWidgetBehavior(this, this.container));
	}

	@Override
	protected void onModelChanged()
	{
		super.onModelChanged();

		this.textarea.setModelObject(this.getDefaultModelObjectAsString());
	}


	// IJQueryWidget //
	@Override
	public JQueryBehavior newWidgetBehavior(String selector)
	{
		return new WysiwygBehavior(selector);
	}
}
