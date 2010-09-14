/*
 * 
 * ==============================================================================
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
package org.wicketstuff.datatable_autocomplete.panel;

import java.util.Map;

import org.apache.wicket.Component;
import org.apache.wicket.IClusterable;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.request.Request;
import org.apache.wicket.util.time.Duration;


/**
 * 
 * @author mocleiri
 * 
 * 
 */
public interface AutoCompleteDependencyProcessor extends IClusterable {

	/**
	 * 
	 * @return the list of dependencies to push through with the get
	 *         request.
	 * 
	 */
	public Map<String, Component> getQueryParameterToComponentMap();

	public void onAjaxUpdate(Request request,
			AjaxRequestTarget target);
	
	/**
	 * 
	 * @return the duration to throttle the request (null if no throttling should be performed)
	 */
	public Duration getThrottingDuration();

}
