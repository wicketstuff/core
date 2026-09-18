package org.wicketstuff.springboot.starter;

import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.protocol.http.WicketFilter;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type;
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
 * <p>
 * Set {@code wicket.enabled=false} to switch the whole auto-configuration off.
 * </p>
 *
 * @author WicketStuff
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
     * Registers the {@link SpringComponentInjector} against the {@link WebApplication} bean so that
     * Wicket pages and components can inject Spring beans via
     * {@link org.apache.wicket.spring.injection.annot.SpringBean}.
     * <p>
     * This is deliberately bound to the {@link WebApplication} rather than to the filter
     * registration: an application that supplies its own {@link FilterRegistrationBean} still gets
     * working Spring injection.
     * </p>
     *
     * @param webApplication     the auto-discovered Wicket WebApplication subclass bean
     * @param applicationContext the Spring application context
     * @return the injector registered as a component instantiation listener
     */
    @Bean
    @ConditionalOnMissingBean
    public SpringComponentInjector springComponentInjector(
            WebApplication webApplication,
            ApplicationContext applicationContext) {

        SpringComponentInjector injector = new SpringComponentInjector(webApplication, applicationContext);
        webApplication.getComponentInstantiationListeners().add(injector);
        return injector;
    }

    /**
     * Creates and configures the {@link FilterRegistrationBean} for the {@link WicketFilter}.
     *
     * @param webApplication the auto-discovered Wicket WebApplication subclass bean
     * @param properties     the externalized Wicket properties
     * @return the configured FilterRegistrationBean for WicketFilter
     */
    @Bean
    @ConditionalOnMissingBean
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
