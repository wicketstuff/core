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
package org.apache.wicket.security.examples.tabs.components.tabs;

import org.apache.wicket.extensions.markup.html.tabs.PanelCachingTab;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.security.extensions.markup.html.tabs.ISecureTab;

/**
 * Wrapper around {@link ISecureTab}. behaves just like {@link PanelCachingTab}. Note this
 * class is not used in this example.
 * 
 * @author marrink
 */
public class SecurePanelCachingTab implements ISecureTab
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Panel cachedPanel;

	private ISecureTab delegate;

	/**
	 * Construct.
	 * 
	 * @param delegate
	 */
	public SecurePanelCachingTab(ISecureTab delegate)
	{
		super();
		this.delegate = delegate;
	}

	/**
	 * 
	 * @see org.apache.wicket.security.extensions.markup.html.tabs.ISecureTab#getPanel()
	 */
	public Class< ? extends Panel> getPanel()
	{
		return delegate.getPanel();
	}

	/**
	 * @see org.apache.wicket.extensions.markup.html.tabs.ITab#getPanel(java.lang.String)
	 */
	public Panel getPanel(String panelId)
	{
		if (cachedPanel != null && cachedPanel.getId().equals(panelId))
			return cachedPanel;
		return cachedPanel = delegate.getPanel(panelId);
	}

	/**
	 * @see org.apache.wicket.extensions.markup.html.tabs.ITab#getTitle()
	 */
	public IModel<String> getTitle()
	{
		return delegate.getTitle();
	}

	/**
	 * Access to the cached panel for subclasses.
	 * 
	 * @return the panel that is already cached or null if the panel is not yet cached.
	 */
	protected final Panel getCachedPanel()
	{
		return cachedPanel;
	}

	public boolean isVisible()
	{
		// @TODO what todo with the new isVisible method ?
		return true;
	}

}
