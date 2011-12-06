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
 *
 * @author Clint Checketts, Florian Braun, Doug Hall, Subramanian Murali
 * @version $Id: $
 */
package org.wicketstuff.webflow;

import org.springframework.webflow.executor.FlowExecutor;
public interface IPageFlowContainer
{
	/**
	 * <p>getFlowExecutor.</p>
	 *
	 * @return a {@link org.springframework.webflow.executor.FlowExecutor} object.
	 */
	public FlowExecutor getFlowExecutor();
	
	/**
	 * Method that returns the Flow Id which needs to be launched when a user hits the page flow application.
	 * This method needs to be overriden by the sub classes to return the appropriate web 'Flow Id'.
	 *
	 * @return String - Flow Id for the Page Flow application.
	 */
	public abstract String getFlowId();
}

