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

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;

/**
 * Panel showing a text to warn the user he/she is not authorized to view its contents.
 * 
 * @author marrink
 */
public class WarningPanel extends Panel
{

	private static final long serialVersionUID = 1L;

	private final Panel restrictedPanel;

	/**
	 * Constructs a new WarningPanel that will be visible when the restricted panel is not
	 * allowed to be rendered. Invisible otherwise.
	 * 
	 * @param id
	 * @param restrictedPanel
	 *            the panel to watch for prohibited rendering.
	 */
	public WarningPanel(String id, Panel restrictedPanel)
	{
		super(id);
		this.restrictedPanel = restrictedPanel;
		add(new Label("message", getString("panel.unauthorized")));
	}

	/**
	 * @see org.apache.wicket.Component#isVisible()
	 */
	@Override
	public boolean isVisible()
	{
		return !restrictedPanel.isRenderAllowed();
	}

}
