package org.wicketstuff.springboot.starter.example;

import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.protocol.http.WicketFilter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * An application that supplies its own {@link FilterRegistrationBean} makes the starter's
 * registration back off. Spring injection must keep working regardless, which is why the
 * {@code SpringComponentInjector} is bound to the {@link WebApplication} bean rather than to the
 * filter registration.
 *
 * @author Daniel Bartl
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestRestTemplate
@Import(SpringBeanInjectionWithCustomFilterRegistrationTest.CustomRegistrationConfig.class)
class SpringBeanInjectionWithCustomFilterRegistrationTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	void stillInjectsSpringBeansWhenTheFilterRegistrationIsOverridden() {
		ResponseEntity<String> response = restTemplate.getForEntity("/", String.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).contains("Hello from Spring, Wicket!");
	}

	@TestConfiguration
	static class CustomRegistrationConfig {

		@Bean
		WebApplication webApplication() {
			return new QuickstartWebApplication();
		}

		@Bean
		GreetingService greetingService() {
			return () -> "Hello from Spring, Wicket!";
		}

		@Bean
		FilterRegistrationBean<WicketFilter> customWicketFilterRegistration(WebApplication webApplication) {
			FilterRegistrationBean<WicketFilter> registration = new FilterRegistrationBean<>();
			registration.setFilter(new WicketFilter(webApplication));
			registration.addUrlPatterns("/*");
			registration.setName("custom-wicket-filter");
			registration.addInitParameter(WicketFilter.FILTER_MAPPING_PARAM, "/*");
			registration.addInitParameter("configuration", "DEVELOPMENT");
			return registration;
		}
	}
}
