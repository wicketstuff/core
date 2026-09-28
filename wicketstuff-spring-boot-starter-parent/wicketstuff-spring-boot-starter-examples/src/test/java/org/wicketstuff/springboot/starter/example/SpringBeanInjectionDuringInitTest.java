package org.wicketstuff.springboot.starter.example;

import org.apache.wicket.injection.Injector;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Spring Boot initialises the embedded container's filters, and with them the Wicket application,
 * while the application context is still refreshing. Spring injection must already be in place at
 * that point, so that {@link WebApplication#init()} can rely on {@code @SpringBean}, for example to
 * inject a non-component object through {@link Injector#get()}.
 *
 * @author Daniel Bartl
 */
@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
		properties = "wicket.filter-name=injection-during-init-filter")
@Import(SpringBeanInjectionDuringInitTest.InitInjectionConfig.class)
class SpringBeanInjectionDuringInitTest {

	@Autowired
	private InitInjectingWebApplication application;

	@Test
	void springBeansCanBeInjectedFromTheApplicationsInit() {
		assertThat(application.greetingSeenDuringInit).isEqualTo("Hello from Spring, Wicket!");
	}

	@TestConfiguration
	static class InitInjectionConfig {

		@Bean
		InitInjectingWebApplication webApplication() {
			return new InitInjectingWebApplication();
		}

		@Bean
		GreetingService greetingService() {
			return () -> "Hello from Spring, Wicket!";
		}
	}

	static class InitInjectingWebApplication extends QuickstartWebApplication {

		private String greetingSeenDuringInit;

		@Override
		protected void init() {
			super.init();

			GreetingHolder holder = new GreetingHolder();
			Injector.get().inject(holder);
			greetingSeenDuringInit = holder.greetingService.greet();
		}
	}

	static class GreetingHolder {

		@SpringBean
		private GreetingService greetingService;
	}
}
