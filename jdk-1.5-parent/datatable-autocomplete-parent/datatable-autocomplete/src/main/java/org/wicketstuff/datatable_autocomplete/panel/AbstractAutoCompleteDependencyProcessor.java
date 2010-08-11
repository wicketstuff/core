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

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.wicket.Component;
import org.apache.wicket.util.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author mocleiri
 * 
 * A base implementation for the autocomplete dependency processor.
 * 
 * Note that extendors define the components to be updated
 */
public abstract class AbstractAutoCompleteDependencyProcessor implements
		AutoCompleteDependencyProcessor {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7293448644441764951L;
	private Duration	duration = null;
	private Map<String, Component>	queryParameterToComponentMap;


	/**
	 * 
	 */
	public AbstractAutoCompleteDependencyProcessor(String[] names, Component[] components) {

		super();
		
		queryParameterToComponentMap = new LinkedHashMap<String, Component>();
		
		for (int i = 0; i < components.length; i++) {
			String parameter = names[i];
			Component component = components[i];
			
			// to guarantee that the markupid will be generated
			component.setOutputMarkupId(true);
			component.setOutputMarkupPlaceholderTag(true);
			
			queryParameterToComponentMap.put(parameter, component);
			
		}
		
	}

	public AbstractAutoCompleteDependencyProcessor(String[] names, Component[] components, Duration duration) {
		this (names, components);
		this.duration = duration;
	}
	
	private static final Logger	log	= LoggerFactory
											.getLogger(AbstractAutoCompleteDependencyProcessor.class);

	
	
	public Map<String, Component> getQueryParameterToComponentMap() {

		return queryParameterToComponentMap;
	}

	
	public Duration getThrottingDuration() {

		return duration ;
	}

	
}
