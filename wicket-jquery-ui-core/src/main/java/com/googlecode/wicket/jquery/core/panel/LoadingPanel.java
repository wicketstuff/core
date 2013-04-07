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
package com.googlecode.wicket.jquery.core.panel;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.request.IRequestHandler;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.handler.resource.ResourceReferenceRequestHandler;

/**
 * Provides a {@link Panel} containing a loading indicator {@link Component}, which can be accessed (to be replaced for instance) by {@link #getPlaceholderComponent()}
 *
 * @author Sebastien Briquet - sebfz1
 * @since 1.2.1
 */
public class LoadingPanel extends Panel
{
	private static final long serialVersionUID = 1L;

	/**
	 * The component id which will be used to load the lazily loaded component.
	 */
	public static final String LAZY_LOAD_COMPONENT_ID = "lazy";

	private Label label;

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 */
	public LoadingPanel(String id)
	{
		super(id);

		IRequestHandler handler = new ResourceReferenceRequestHandler(AbstractDefaultAjaxBehavior.INDICATOR);

		this.label = new Label(LAZY_LOAD_COMPONENT_ID, String.format("<img alt=\"Loading...\" src=\"%s\"/>", RequestCycle.get().urlFor(handler)));
		this.label.setEscapeModelStrings(false);
		this.label.setOutputMarkupId(true);

		this.add(this.label);
	}

	/**
	 * Gets the placeholder component (the label) that contains the loading indicator.<br/>
	 * This component is designed to be replaced by another component (like a lazy-loaded component)
	 *
	 * @return the {@link Component} being replaced
	 */
	public Component getPlaceholderComponent()
	{
		return this.label;
	}
}
