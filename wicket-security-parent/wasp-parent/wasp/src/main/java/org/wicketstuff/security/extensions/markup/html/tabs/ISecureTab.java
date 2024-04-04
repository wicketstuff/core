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
package org.wicketstuff.security.extensions.markup.html.tabs;

import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.markup.html.panel.Panel;

/**
 * {@link ITab} which uses the class of the panel to check for sufficient rights. This component is
 * to be used with a {@link SecureTabbedPanel} since it does not do anything by itself.
 * 
 * @author marrink
 */
public interface ISecureTab extends ITab
{
	/**
	 * Quick access to the class of the panel returned in {@link ITab#getPanel(String)}.
	 * 
	 * @return the class
	 */
	public Class<? extends Panel> getPanel();
}
