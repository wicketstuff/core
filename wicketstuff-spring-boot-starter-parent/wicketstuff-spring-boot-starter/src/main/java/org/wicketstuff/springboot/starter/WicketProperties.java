/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wicketstuff.springboot.starter;

import org.apache.wicket.RuntimeConfigurationType;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.Ordered;

/**
 * Configuration properties for Apache Wicket integration in Spring Boot.
 * <p>
 * These properties can be customized in application configuration files (e.g., {@code application.properties}
 * or {@code application.yml}) under the {@code wicket} prefix.
 * </p>
 *
 * @author Daniel Bartl
 */
@ConfigurationProperties(prefix = "wicket")
public class WicketProperties {

	/**
	 * URL mapping pattern for the Wicket Filter.
	 * <p>
	 * Determines which incoming requests are processed by Wicket. Default is {@code "/*"}.
	 * </p>
	 */
	private String filterPath = "/*";

	/**
	 * Servlet name of the registered Wicket Filter.
	 * <p>
	 * Helps identify the filter in registration logs and configuration contexts. Default is {@code "wicket-filter"}.
	 * </p>
	 */
	private String filterName = "wicket-filter";

	/**
	 * Wicket execution configuration type.
	 * <p>
	 * Can be set to {@link RuntimeConfigurationType#DEVELOPMENT} or {@link RuntimeConfigurationType#DEPLOYMENT}.
	 * Default is {@link RuntimeConfigurationType#DEVELOPMENT}.
	 * </p>
	 */
	private RuntimeConfigurationType configuration = RuntimeConfigurationType.DEVELOPMENT;

	/**
	 * Order of the Wicket Filter within the servlet filter chain.
	 * <p>
	 * Lower values run earlier. Default is {@link Ordered#LOWEST_PRECEDENCE}, so Wicket runs after
	 * every other filter, e.g. after Spring Security's filter chain.
	 * </p>
	 */
	private int filterOrder = Ordered.LOWEST_PRECEDENCE;

	/**
	 * Gets the URL mapping pattern for the Wicket Filter.
	 *
	 * @return the filter URL mapping path pattern
	 */
	public String getFilterPath() {
		return filterPath;
	}

	/**
	 * Sets the URL mapping pattern for the Wicket Filter.
	 *
	 * @param filterPath the filter URL mapping path pattern to set
	 */
	public void setFilterPath(String filterPath) {
		this.filterPath = filterPath;
	}

	/**
	 * Gets the servlet name of the registered Wicket Filter.
	 *
	 * @return the servlet filter name
	 */
	public String getFilterName() {
		return filterName;
	}

	/**
	 * Sets the servlet name of the registered Wicket Filter.
	 *
	 * @param filterName the servlet filter name to set
	 */
	public void setFilterName(String filterName) {
		this.filterName = filterName;
	}

	/**
	 * Gets the Wicket execution configuration type.
	 *
	 * @return the configuration type
	 */
	public RuntimeConfigurationType getConfiguration() {
		return configuration;
	}

	/**
	 * Sets the Wicket execution configuration type.
	 *
	 * @param configuration the configuration type to set (DEVELOPMENT or DEPLOYMENT)
	 */
	public void setConfiguration(RuntimeConfigurationType configuration) {
		this.configuration = configuration;
	}

	/**
	 * Gets the order of the Wicket Filter within the servlet filter chain.
	 *
	 * @return the filter order
	 */
	public int getFilterOrder() {
		return filterOrder;
	}

	/**
	 * Sets the order of the Wicket Filter within the servlet filter chain.
	 *
	 * @param filterOrder the filter order to set; lower values run earlier
	 */
	public void setFilterOrder(int filterOrder) {
		this.filterOrder = filterOrder;
	}
}
