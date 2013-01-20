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

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.tabs.AbstractTab;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;

import com.googlecode.wicket.jquery.ui.panel.LoadingPanel;

/**
 * Provides an {@link AbstractTab} which loads the panel when the {@link ITab} is clicked.
 *
 * @author Sebastien Briquet - sebfz1
 * @since 1.2.1
 */
public abstract class AjaxTab extends AbstractTab
{
	private static final long serialVersionUID = 1L;

	private static final byte STATE_INIT = 0;
	private static final byte STATE_LOAD = 1;
	private static final byte STATE_LOADED = 2;

	private LoadingPanel panel = null;
	private byte state = STATE_INIT;

	/**
	 * Constructor
	 *
	 * @param title IModel used to represent the title of the tab.
	 */
	public AjaxTab(IModel<String> title)
	{
		super(title);
	}

	/**
	 * Gets the default panel (loading indicator)
	 */
	@Override
	public final WebMarkupContainer getPanel(String panelId)
	{
		if (this.state == STATE_INIT)
		{
			this.panel = new LoadingPanel(panelId);
			this.state = STATE_LOAD;
		}

		return this.panel;
	}

	/**
	 * Get the {@link WebMarkupContainer} that will be lazy loaded
	 *
	 * @return the {@link WebMarkupContainer}
	 */
	protected final WebMarkupContainer getLazyPanel()
	{
		return this.getLazyPanel(LoadingPanel.LAZY_LOAD_COMPONENT_ID);
	}

	/**
	 * Gets the {@link WebMarkupContainer} that will be lazy loaded
	 *
	 * @param panelId the markup id to use
	 * @return the {@link WebMarkupContainer}
	 */
	protected abstract WebMarkupContainer getLazyPanel(String panelId);

	/**
	 * Replaces the loading panel's placeholder component (indicator) by the lazy-loaded component.
	 * Warning, should be called only once!
	 *
	 * @return the lazy-loaded component
	 */
	private Component replaceComponent()
	{
		return this.panel.getPlaceholderComponent().replaceWith(this.getLazyPanel()); //warning, inner panel is detached here.
//		return this.panel.replace(this.getLazyPanel()); //warning, inner panel is detached here.
	}

	/**
	 * Loads the lazy component, if not already loaded.
	 * @param target the {@link AjaxRequestTarget}
	 *
	 * @return True if the component has just been loaded. Otherwise false if the component has already been loaded
	 */
	public boolean load(AjaxRequestTarget target)
	{
		boolean load = (this.state == STATE_LOAD);

		if (load)
		{
			target.add(this.replaceComponent());
			this.state = STATE_LOADED;
			//this.getPage().dirty();
		}

		return load;
	}
}
