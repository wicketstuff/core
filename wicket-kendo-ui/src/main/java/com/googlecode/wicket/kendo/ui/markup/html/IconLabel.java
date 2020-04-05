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
package com.googlecode.wicket.kendo.ui.markup.html;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

import com.googlecode.wicket.jquery.core.resource.StyleSheetPackageHeaderItem;
import com.googlecode.wicket.kendo.ui.KendoIcon;

/**
 * Provides a {@link Label} with a Kendo UI icon decoration
 *
 * @author Sebastien Briquet - sebfz1
 * @since 6.21.0
 * @since 7.1.0
 */
public class IconLabel extends Panel
{
	private static final long serialVersionUID = 1L;

	private final String icon;

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 */
	public IconLabel(String id)
	{
		this(id, KendoIcon.NONE);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param icon either a {@link KendoIcon} constant or a 'k-i-<i>icon</i>' css class
	 */
	public IconLabel(String id, String icon)
	{
		super(id);

		this.icon = icon;
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param model the {@link IModel}
	 */
	public IconLabel(String id, IModel<?> model)
	{
		this(id, model, KendoIcon.NONE);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param model the {@link IModel}
	 * @param icon either a {@link KendoIcon} constant or a 'k-i-<i>icon</i>' css class
	 */
	public IconLabel(String id, IModel<?> model, String icon)
	{
		super(id, model);

		this.icon = icon;
	}

	// Methods //

	@Override
	public void renderHead(IHeaderResponse response)
	{
		super.renderHead(response);

		response.render(new StyleSheetPackageHeaderItem(IconLabel.class));
	}

	// Events //

	@Override
	protected void onInitialize()
	{
		super.onInitialize();

		this.add(new EmptyPanel("icon").add(AttributeModifier.append("class", KendoIcon.getCssClass(icon))));
		this.add(new Label("text", this.getDefaultModel()));
	}
}
