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
package org.wicketstuff.webflow;

import org.springframework.context.ApplicationContext;
import org.wicketstuff.webflow.app.PageFlowWebApplication;

/**
 * <p>SimpleWebFlowApplication class.</p>
 *
 * @author Clint Checketts, Florian Braun, Doug Hall
 * @version $Id: $
 */
public class SimpleWebFlowApplication extends PageFlowWebApplication{

	
	/**
	 * <p>Constructor for SimpleWebFlowApplication.</p>
	 */
	public SimpleWebFlowApplication() {
		super();
	}

	/**
	 * <p>Constructor for SimpleWebFlowApplication.</p>
	 *
	 * @param applicationContext2 a {@link org.springframework.context.ApplicationContext} object.
	 */
	public SimpleWebFlowApplication(ApplicationContext applicationContext2) {
		super(applicationContext2);
	}

	/**
	 * <p>getFlowId.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getFlowId() {
		return "ShortSimpleFlow";
	}

}
