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

import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.extensions.markup.html.tabs.PanelCachingTab;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.security.actions.WaspAction;
import org.apache.wicket.security.checks.ContainerSecurityCheck;
import org.apache.wicket.security.checks.ISecurityCheck;
import org.apache.wicket.security.components.ISecureComponent;
import org.apache.wicket.security.components.ISecureContainer;
import org.apache.wicket.security.components.SecureComponentHelper;
import org.apache.wicket.security.extensions.markup.html.tabs.ISecureTab;

/**
 * Secure Tab showing a warning panel when the content is forbidden. This is basically a
 * decorator around a regular tab. Like {@link PanelCachingTab} it caches the panel for
 * improved performance. All methods from {@link ISecureComponent} operate on this cached
 * panel which automatically receives the {@link ISecurityCheck} specified in
 * {@link #getSecurityCheckForPanel(Panel)}. Note unlike the {@link ISecureTab} this tab
 * will always show up, even if the contents is forbidden. So there really is no point in
 * this component implementing or wrapping an ISecureTab
 * 
 * @author marrink
 */
public class SecureTab implements ITab, ISecureContainer
{
	private static final long serialVersionUID = 1L;

	private ITab delegate;

	private WrapperPanel cachedWrapper;

	/**
	 * 
	 * Constructs a wrapper for the actual tab.
	 * 
	 * @param delegate
	 *            the tab
	 */
	public SecureTab(ITab delegate)
	{
		this.delegate = delegate;

	}

	/**
	 * A Panel shown when the user is not authorized to view the regular panel.
	 * 
	 * @param id
	 *            wicket component id of this warning panel
	 * @param restrictedPanel
	 *            the panel dictating if the warning panel should be visible by its
	 *            renderAllowed flag
	 * @return a panel telling the user he/she is not authorized.
	 */
	protected Panel getWarningPanel(String id, Panel restrictedPanel)
	{
		return new WarningPanel(id, restrictedPanel);
	}

	/**
	 * A securityCheck for the tab panel. By default it receives a new
	 * {@link ContainerSecurityCheck}.
	 * 
	 * @param panel
	 *            the panel receiving the securitycheck
	 * @return an {@link ISecurityCheck} or null if no check is required
	 */
	protected ISecurityCheck getSecurityCheckForPanel(Panel panel)
	{
		return new ContainerSecurityCheck(panel);
	}

	/**
	 * Retrieves a cached panel or creates a new one if required.
	 * 
	 * @see org.apache.wicket.extensions.markup.html.tabs.ITab#getPanel(java.lang.String)
	 */
	public Panel getPanel(String panelId)
	{
		if (cachedWrapper != null && cachedWrapper.getId().equals(panelId))
			return cachedWrapper;
		cachedWrapper = new WrapperPanel(panelId);
		setSecurityCheck(getSecurityCheckForPanel(cachedWrapper.cachedTabPanel));
		return cachedWrapper;
	}

	public boolean isVisible()
	{
		// @TODO what todo with the new isVisible method ?
		return true;
	}

	/**
	 * Access to the cached panel.
	 * 
	 * @return the panel or null if it isn't cached yet
	 */
	protected Panel getPanel()
	{
		return cachedWrapper;
	}

	/**
	 * Operates on the cached panel.
	 * 
	 * @see org.apache.wicket.security.components.ISecureComponent#getSecurityCheck()
	 */
	public ISecurityCheck getSecurityCheck()
	{
		if (cachedWrapper == null)
			return null;
		if (cachedWrapper.cachedTabPanel instanceof ISecureComponent)
			return ((ISecureComponent) cachedWrapper.cachedTabPanel).getSecurityCheck();
		return SecureComponentHelper.getSecurityCheck(cachedWrapper.cachedTabPanel);
	}

	/**
	 * Operates on the cached panel.
	 * 
	 * @see org.apache.wicket.security.components.ISecureComponent#isActionAuthorized(java.lang.String)
	 */
	public boolean isActionAuthorized(String waspAction)
	{
		if (cachedWrapper == null)
			return true;
		if (cachedWrapper.cachedTabPanel instanceof ISecureComponent)
			return ((ISecureComponent) cachedWrapper.cachedTabPanel).isActionAuthorized(waspAction);
		return SecureComponentHelper.isActionAuthorized(cachedWrapper.cachedTabPanel, waspAction);
	}

	/**
	 * Operates on the cached panel.
	 * 
	 * @see org.apache.wicket.security.components.ISecureComponent#isActionAuthorized(WaspAction)
	 */
	public boolean isActionAuthorized(WaspAction action)
	{
		if (cachedWrapper == null)
			return true;
		if (cachedWrapper.cachedTabPanel instanceof ISecureComponent)
			return ((ISecureComponent) cachedWrapper.cachedTabPanel).isActionAuthorized(action);
		return SecureComponentHelper.isActionAuthorized(cachedWrapper.cachedTabPanel, action);
	}

	/**
	 * Operates on the cached panel.
	 * 
	 * @see org.apache.wicket.security.components.ISecureComponent#isAuthenticated()
	 */
	public boolean isAuthenticated()
	{
		if (cachedWrapper == null)
			return true;
		if (cachedWrapper.cachedTabPanel instanceof ISecureComponent)
			return ((ISecureComponent) cachedWrapper.cachedTabPanel).isAuthenticated();
		return SecureComponentHelper.isAuthenticated(cachedWrapper.cachedTabPanel);
	}

	/**
	 * Operates on the cached panel.
	 * 
	 * @see org.apache.wicket.security.components.ISecureComponent#setSecurityCheck(org.apache.wicket.security.checks.ISecurityCheck)
	 */
	public void setSecurityCheck(ISecurityCheck check)
	{
		if (cachedWrapper == null)
			return;
		if (cachedWrapper.cachedTabPanel instanceof ISecureComponent)
			((ISecureComponent) cachedWrapper.cachedTabPanel).setSecurityCheck(check);
		else
			SecureComponentHelper.setSecurityCheck(cachedWrapper.cachedTabPanel, check);
	}

	/**
	 * @see org.apache.wicket.extensions.markup.html.tabs.ITab#getTitle()
	 */
	public IModel<String> getTitle()
	{
		return delegate.getTitle();
	}

	/**
	 * Panel to wrap the regular tab panel and a warning panel in one component. Only one
	 * of them is actually shown.
	 * 
	 * @author marrink
	 */
	private class WrapperPanel extends Panel
	{
		private static final long serialVersionUID = 1L;

		private Panel cachedTabPanel;

		/**
		 * Construct.
		 * 
		 * @param id
		 */
		public WrapperPanel(String id)
		{
			super(id);
			setRenderBodyOnly(true);
			cachedTabPanel = delegate.getPanel("wrapped_panel");
			add(cachedTabPanel);
			add(getWarningPanel("warning_panel", cachedTabPanel));
		}

	}
}
