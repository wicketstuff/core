package org.wicketstuff.springboot.starter;

import org.apache.wicket.Page;
import org.apache.wicket.RuntimeConfigurationType;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.protocol.http.WicketFilter;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.boot.test.context.runner.WebApplicationContextRunner;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.assertj.core.api.Assertions.assertThat;

class WicketAutoConfigurationTest {

	private final WebApplicationContextRunner servletContextRunner = new WebApplicationContextRunner()
			.withConfiguration(AutoConfigurations.of(WicketAutoConfiguration.class));

	@Test
	void registersDefaultWebApplicationAndFilterWhenNoneProvided() {
		servletContextRunner.run(context -> {
			assertThat(context).hasSingleBean(WebApplication.class);
			assertThat(context.getBean(WebApplication.class)).isInstanceOf(DefaultWebApplication.class);

			assertThat(context).hasSingleBean(FilterRegistrationBean.class);
			FilterRegistrationBean<?> registration = context.getBean(FilterRegistrationBean.class);
			assertThat(registration.getFilter()).isInstanceOf(WicketFilter.class);
			assertThat(registration.getUrlPatterns()).containsExactly("/*");
			assertThat(registration.getFilterName()).isEqualTo("wicket-filter");
			assertThat(registration.getInitParameters())
					.containsEntry("configuration", RuntimeConfigurationType.DEVELOPMENT.name());
		});
	}

	@Test
	void bindsCustomWicketPropertiesOntoTheFilterRegistration() {
		servletContextRunner
				.withPropertyValues(
						"wicket.filter-path=/app/*",
						"wicket.filter-name=custom-wicket-filter",
						"wicket.configuration=DEPLOYMENT")
				.run(context -> {
					FilterRegistrationBean<?> registration = context.getBean(FilterRegistrationBean.class);
					assertThat(registration.getUrlPatterns()).containsExactly("/app/*");
					assertThat(registration.getFilterName()).isEqualTo("custom-wicket-filter");
					assertThat(registration.getInitParameters())
							.containsEntry("configuration", RuntimeConfigurationType.DEPLOYMENT.name());
				});
	}

	@Test
	void backsOffWhenACustomWebApplicationBeanIsPresent() {
		servletContextRunner.withUserConfiguration(CustomWebApplicationConfig.class).run(context -> {
			assertThat(context).hasSingleBean(WebApplication.class);
			assertThat(context.getBean(WebApplication.class)).isInstanceOf(CustomWebApplication.class);
		});
	}

	@Test
	void backsOffWhenACustomWicketFilterRegistrationIsPresent() {
		servletContextRunner.withUserConfiguration(CustomFilterRegistrationConfig.class).run(context -> {
			assertThat(context).hasSingleBean(FilterRegistrationBean.class);

			FilterRegistrationBean<?> registration = context.getBean(FilterRegistrationBean.class);
			assertThat(registration.getFilterName()).isEqualTo("custom-wicket-filter");
			assertThat(registration.getUrlPatterns()).containsExactly("/custom/*");
		});
	}

	@Test
	void doesNotActivateOutsideServletWebApplications() {
		new ApplicationContextRunner()
				.withConfiguration(AutoConfigurations.of(WicketAutoConfiguration.class))
				.run(context -> assertThat(context).doesNotHaveBean(WebApplication.class));
	}

	@Configuration
	static class CustomWebApplicationConfig {

		@Bean
		WebApplication webApplication() {
			return new CustomWebApplication();
		}
	}

	@Configuration
	static class CustomFilterRegistrationConfig {

		@Bean
		FilterRegistrationBean<WicketFilter> customWicketFilterRegistration() {
			FilterRegistrationBean<WicketFilter> registration = new FilterRegistrationBean<>();
			registration.setFilter(new WicketFilter(new CustomWebApplication()));
			registration.addUrlPatterns("/custom/*");
			registration.setName("custom-wicket-filter");
			return registration;
		}
	}

	static class CustomWebApplication extends WebApplication {

		@Override
		public Class<? extends Page> getHomePage() {
			return DefaultHomePage.class;
		}
	}
}
