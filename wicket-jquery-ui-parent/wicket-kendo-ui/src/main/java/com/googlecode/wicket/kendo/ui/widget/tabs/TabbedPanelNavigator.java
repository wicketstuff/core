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
package com.googlecode.wicket.kendo.ui.widget.tabs;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.core.request.handler.IPartialPageRequestHandler;
import org.apache.wicket.event.IEvent;

import com.googlecode.wicket.jquery.core.ajax.HandlerPayload;
import com.googlecode.wicket.kendo.ui.form.button.AjaxButton;
import com.googlecode.wicket.kendo.ui.widget.NavigationPanel;

/**
 * Provides a {@link NavigationPanel} for {@link TabbedPanel}
 *
 * @author Sebastien Briquet - sebfz1
 */
public class TabbedPanelNavigator extends NavigationPanel // NOSONAR
{
	private static final long serialVersionUID = 1L;

	private int max = 0;
	private int index = TabsBehavior.DEFAULT_TAB_INDEX;
	private final TabbedPanel tabbedPanel;

	public TabbedPanelNavigator(String id, TabbedPanel tabbedPanel)
	{
		super(id);
		this.tabbedPanel = tabbedPanel;
	}

	// Properties //

	/**
	 * Gets count of visible tabs
	 *
	 * @return the count
	 */
	private int count()
	{
		return this.tabbedPanel.getLastTabIndex() + 1;
	}

	// Events //

	@Override
	protected void onConfigure()
	{
		super.onConfigure();

		this.max = this.count() - 1;

		this.getBackwardButton().setEnabled(this.index > 0);
		this.getForwardButton().setEnabled(this.index < this.max);
	}

	@Override
	protected void onBackward(AjaxRequestTarget target, AjaxButton button)
	{
		if (this.index > 0)
		{
			this.tabbedPanel.setTabIndex(this.index - 1, target); // fires onSelect
		}
	}

	@Override
	protected void onForward(AjaxRequestTarget target, AjaxButton button)
	{
		if (this.index < this.max)
		{
			this.tabbedPanel.setTabIndex(this.index + 1, target); // fires onSelect
		}
	}

	@Override
	public void onEvent(IEvent<?> event)
	{
		super.onEvent(event);

		// broadcasted by the TabbedPanel
		if (event.getPayload() instanceof RefreshPayload)
		{
			RefreshPayload payload = (RefreshPayload) event.getPayload();

			this.index = payload.getIndex();
			payload.reload(this);
		}
	}

	/* Classes */

	/**
	 * Provides a {@link HandlerPayload} designed to reload the {@link TabbedPanelNavigator}
	 */
	public static class RefreshPayload extends HandlerPayload
	{
		private final int index;

		public RefreshPayload(int index, IPartialPageRequestHandler handler)
		{
			super(handler);

			this.index = index;
		}

		public int getIndex()
		{
			return this.index;
		}
	}
}
