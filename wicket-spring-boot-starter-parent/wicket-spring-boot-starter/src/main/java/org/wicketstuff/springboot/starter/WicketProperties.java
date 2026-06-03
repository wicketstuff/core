package org.wicketstuff.springboot.starter;

import org.apache.wicket.RuntimeConfigurationType;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration properties for Apache Wicket integration in Spring Boot.
 * <p>
 * These properties can be customized in application configuration files (e.g., {@code application.properties}
 * or {@code application.yml}) under the {@code wicket} prefix.
 * </p>
 *
 * @author WicketStuff
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
	 * If not explicitly defined, it defaults to {@code DEVELOPMENT}.
	 * </p>
	 */
	private RuntimeConfigurationType configuration;

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
	 * @return the configuration type, or {@code null} if not configured
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
}
