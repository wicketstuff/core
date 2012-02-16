/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Contributed by United Services Automotive Association (USAA)
 */

/**
 *****************************************************************************
 * File: IFlowContainerPanel.java
 *****************************************************************************
 *
 * @author Clint Checketts, Florian Braun, Doug Hall, Subramanian Murali
 * @version $Id: $
 */
package org.wicketstuff.webflow;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.panel.Panel;
public interface IFlowContainerPanel
{
	/**
	 * <p>onPanelFlowChange.</p>
	 *
	 * @param target a {@link org.apache.wicket.ajax.AjaxRequestTarget} object.
	 */
	public void onPanelFlowChange(AjaxRequestTarget target);
	
	
	/**
	 * <p>getContentPanel.</p>
	 *
	 * @return a {@link org.apache.wicket.markup.html.panel.Panel} object.
	 */
	public Panel getContentPanel();

	/**
	 * <p>setContentPanel.</p>
	 *
	 * @param newContentPanel a {@link org.apache.wicket.markup.html.panel.Panel} object.
	 */
	public void setContentPanel(Panel newContentPanel);
	
	
}

