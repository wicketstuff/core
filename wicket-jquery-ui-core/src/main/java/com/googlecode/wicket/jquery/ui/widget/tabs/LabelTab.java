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
package com.googlecode.wicket.jquery.ui.widget.tabs;

import org.apache.wicket.extensions.markup.html.tabs.AbstractTab;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;

import com.googlecode.wicket.jquery.ui.panel.LabelPanel;

/**
 * Provides a simple {@link AbstractTab} that embeds a {@link LabelPanel}
 *
 * @author Sebastien Briquet - sebfz1
 * @since 1.2.3
 */
public class LabelTab extends AbstractTab
{
	private static final long serialVersionUID = 1L;

	private final IModel<String> content;

	/**
	 * Constructor
	 *
	 * @param title IModel used to represent the title of the tab
	 * @param content IModel used to represent the content of the tab
	 */
	public LabelTab(IModel<String> title, IModel<String> content)
	{
		super(title);

		this.content = content;
	}

	/**
	 * Gets the {@link LabelPanel} that contains the content supplied in the constructor
	 * @return the {@link LabelPanel}
	 */
	@Override
	public final WebMarkupContainer getPanel(String panelId)
	{
		return new LabelPanel(panelId, this.content);
	}
}
