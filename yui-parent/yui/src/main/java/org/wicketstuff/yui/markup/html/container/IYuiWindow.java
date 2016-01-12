/**
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.wicketstuff.yui.markup.html.container;

import org.apache.wicket.Component;
import org.apache.wicket.IClusterable;

/**
 * Interface used to represent a single window.
 * 
 * @see YuiWindows
 * @author Erik van Oosten
 */
public interface IYuiWindow extends IClusterable {

	/**
	 * @return additional options to pass to the panel creation or null for none
	 * @see http://developer.yahoo.com/yui/container/panel/#config
	 */
	String getAdditionalOpts();

	/**
	 * @return the initial dimension of the window
	 */
	YuiWindowDimension getDimension();

	/**
	 * Creates the footer component.
	 * 
	 * @param id component id of new footer panel
	 * @return a component that will be placed in the footer of the window, must
	 *         have the given component id.
	 */
	Component newFooter(String id);

	/**
	 * Creates the body component.
	 * 
	 * @param id component id of new body panel
	 * @return a component that will be placed in the body of the window, must
	 *         have the given component id.
	 */
	Component newBody(String id);

	/**
	 * Creates the header component.
	 * 
	 * @param id component id of new footer panel
	 * @return a component that will be placed in the header of the window, must
	 *         have the given component id.
	 */
	Component newHeader(String id);

}
