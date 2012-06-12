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
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.markup.html.tabs.AbstractTab;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

import com.googlecode.wicket.jquery.ui.panel.LoadingPanel;

/**
 * Provides an {@link AbstractTab} where the panel is loading when the {@link ITab} is clicked. 
 * 
 * @author Sebastien Briquet - sebfz1
 * @since 1.2.1
 */
public abstract class AjaxTab extends AbstractTab
{
	private static final long serialVersionUID = 1L;

	private static final byte STATE_INIT = 0;
	private static final byte STATE_LOAD = 1;
	private static final byte STATE_ADDED = 2;

	private LoadingPanel panel;
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
	public final Panel getPanel(String panelId)
	{
		this.panel = new LoadingPanel(panelId);
		this.state = STATE_LOAD;

		return this.panel;
	}

	/**
	 * Get the {@link Component} that will be lazy loaded
	 * 
	 * @return the {@link Component}
	 */
	protected final Component getLazyPanel()
	{
		return this.getLazyPanel(LoadingPanel.LAZY_LOAD_COMPONENT_ID);
	}

	/**
	 * Gets the {@link WebMarkupContainer} that will be lazy loaded
	 * 
	 * @param panelId the markup id to use
	 * @return the {@link WebMarkupContainer}
	 */
	public abstract WebMarkupContainer getLazyPanel(String panelId);

	/**
	 * Gets the {@link AjaxLink} for this {@link ITab}, which will handle the lazy-panel load.
	 *  
	 * @param id the markup id
	 * @return the {@link AjaxLink}
	 */
	public AjaxLink<Void> newLink(String id)
	{
		return new AjaxLink<Void>(id) {

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target)
			{
				if (AjaxTab.this.state == STATE_LOAD)
				{
					Component inner = AjaxTab.this.panel.getInnerLabel();
					Component panel = AjaxTab.this.getLazyPanel();
	
					target.add(inner.replaceWith(panel)); //warning, inner panel is detached here.

					AjaxTab.this.state = STATE_ADDED;
					this.getPage().dirty();

					//this.unregisterHandler(); //to implement, unregister the click handler (wicket 1.5.x & wicket 6.x)
				}
			}
		};
	}
}
