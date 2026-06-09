package org.wicketstuff.springboot.starter;

import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import org.apache.wicket.RuntimeConfigurationType;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.protocol.http.WicketFilter;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.jspecify.annotations.NonNull;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * Spring Boot auto-configuration for Apache Wicket.
 * <p>
 * This auto-configuration is activated when Wicket's core classes are present on the classpath.
 * It registers Wicket's {@link WicketFilter} into the embedded servlet container and binds the
 * registered {@link WebApplication} bean. Additionally, it registers the {@link SpringComponentInjector}
 * to enable Spring bean injection (via {@link org.apache.wicket.spring.injection.annot.SpringBean})
 * inside Wicket pages and components.
 * </p>
 *
 * @author WicketStuff
 */
@AutoConfiguration
@ConditionalOnClass({WebApplication.class, WicketFilter.class})
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
     * Creates and configures the {@link FilterRegistrationBean} for the {@link WicketFilter}.
     * <p>
     * Instantiates WicketFilter with the provided {@link WebApplication} bean and attaches
     * a {@link SpringComponentInjector} during filter initialization to enable Spring bean injection.
     * </p>
     *
     * @param webApplication     the auto-discovered Wicket WebApplication subclass bean
     * @param properties         the externalized Wicket properties
     * @param applicationContext the Spring application context
     * @return the configured FilterRegistrationBean for WicketFilter
     */
    @Bean
    @ConditionalOnMissingBean(WicketFilter.class)
    public FilterRegistrationBean<WicketFilter> wicketFilterRegistration(
            WebApplication webApplication,
            WicketProperties properties,
            ApplicationContext applicationContext) {

        WicketFilter filter = new WicketFilter(webApplication) {
            @Override
            public void init(boolean isServlet, FilterConfig filterConfig) throws ServletException {
                super.init(isServlet, filterConfig);
                // Register SpringComponentInjector so Wicket pages can inject Spring @Components / @Beans using @SpringBean.
                webApplication.getComponentInstantiationListeners().add(
                        new SpringComponentInjector(webApplication, applicationContext)
                );
            }
        };

        return configureWicketFilter(properties, filter);

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
    private static @NonNull FilterRegistrationBean<WicketFilter> configureWicketFilter(
            WicketProperties properties, WicketFilter filter) {
        FilterRegistrationBean<WicketFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(filter);
        registration.addUrlPatterns(properties.getFilterPath());
        registration.setName(properties.getFilterName());

        // Specify configuration parameter mapping for Wicket
        registration.addInitParameter(WicketFilter.FILTER_MAPPING_PARAM, properties.getFilterPath());

        RuntimeConfigurationType configType = properties.getConfiguration();
        if (configType == null) {
            configType = RuntimeConfigurationType.DEVELOPMENT;
        }
        registration.addInitParameter("configuration", configType.name());
        return registration;
    }
}
