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

import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.protocol.http.WicketFilter;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingFilterBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

/**
 * Spring Boot autoconfiguration for Apache Wicket.
 * <p>
 * This autoconfiguration is activated when Wicket's core classes are present on the classpath.
 * It registers Wicket's {@link WicketFilter} into the embedded servlet container and binds the
 * registered {@link WebApplication} bean. Additionally, it registers the {@link SpringComponentInjector}
 * to enable Spring bean injection (via {@link org.apache.wicket.spring.injection.annot.SpringBean})
 * inside Wicket pages and components.
 * </p>
 * <p>
 * Set {@code wicket.enabled=false} to switch the whole autoconfiguration off.
 * </p>
 *
 * @author Daniel Bartl
 */
@AutoConfiguration
@ConditionalOnWebApplication(type = Type.SERVLET)
@ConditionalOnClass({WebApplication.class, WicketFilter.class})
@ConditionalOnProperty(prefix = "wicket", name = "enabled", havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties(WicketProperties.class)
public class WicketAutoConfiguration {

	/**
	 * Registers a default {@link WebApplication} bean if the application does not define one.
	 * This registers the {@link DefaultWebApplication} which serves the starter's {@link DefaultHomePage}.
	 *
	 * @return the default WebApplication
	 */
	@Bean
	@ConditionalOnMissingBean(WebApplication.class)
	public WebApplication webApplication() {
		return new DefaultWebApplication();
	}

	/**
	 * Registers a {@link SpringComponentInjector} on the {@link WebApplication} bean so that Wicket
	 * pages and components can inject Spring beans via
	 * {@link org.apache.wicket.spring.injection.annot.SpringBean}.
	 * <p>
	 * The injector is added when the {@link WebApplication} bean is created, not later as a bean of
	 * its own: the servlet container initialises the Wicket filter, and so runs
	 * {@link WebApplication#init()}, before ordinary singletons exist. Being bound to the
	 * {@link WebApplication} rather than to the filter registration also keeps injection working
	 * for an application that supplies its own {@link FilterRegistrationBean}.
	 * </p>
	 * <p>
	 * Backs off when the application defines its own {@link SpringComponentInjector} bean.
	 * </p>
	 *
	 * @return the post-processor registering the injector on each {@link WebApplication} bean
	 */
	@Bean
	@ConditionalOnMissingBean(SpringComponentInjector.class)
	static SpringComponentInjectorRegistrar springComponentInjectorRegistrar() {
		return new SpringComponentInjectorRegistrar();
	}

	/**
	 * Creates and configures the {@link FilterRegistrationBean} for the {@link WicketFilter}.
	 * <p>
	 * Backs off when the application already registers a {@link WicketFilter}, either wrapped in its
	 * own {@link FilterRegistrationBean} or declared as a plain filter bean, so that Wicket is never
	 * mapped twice.
	 * </p>
	 *
	 * @param webApplication the auto-discovered Wicket WebApplication subclass bean
	 * @param properties     the externalized Wicket properties
	 * @return the configured FilterRegistrationBean for WicketFilter
	 */
	@Bean
	@ConditionalOnMissingFilterBean(WicketFilter.class)
	public FilterRegistrationBean<WicketFilter> wicketFilterRegistration(
			WebApplication webApplication,
			WicketProperties properties) {

		return configureWicketFilter(properties, new WicketFilter(webApplication));
	}

	/**
	 * Configures a {@link FilterRegistrationBean} for a given {@link WicketFilter}.
	 * Sets up the filter mapping path, filter name, and configuration type for Wicket.
	 *
	 * @param properties the {@link WicketProperties} containing configuration details such as filter path,
	 *                   filter name, and runtime configuration type
	 * @param filter     the {@link WicketFilter} to be configured and registered
	 * @return a configured {@link FilterRegistrationBean} instance for the provided {@link WicketFilter}
	 */
	private static FilterRegistrationBean<WicketFilter> configureWicketFilter(
			WicketProperties properties, WicketFilter filter) {
		FilterRegistrationBean<WicketFilter> registration = new FilterRegistrationBean<>();
		registration.setFilter(filter);
		registration.addUrlPatterns(properties.getFilterPath());
		registration.setName(properties.getFilterName());

		// Specify configuration parameter mapping for Wicket
		registration.addInitParameter(WicketFilter.FILTER_MAPPING_PARAM, properties.getFilterPath());
		registration.addInitParameter("configuration", properties.getConfiguration().name());
		return registration;
	}
}
