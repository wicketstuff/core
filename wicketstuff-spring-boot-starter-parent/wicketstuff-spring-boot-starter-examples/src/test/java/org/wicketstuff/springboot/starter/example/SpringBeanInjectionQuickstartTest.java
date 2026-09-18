package org.wicketstuff.springboot.starter.example;

import org.apache.wicket.protocol.http.WebApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * End-to-end proof of the README's Quickstart pattern: registering a custom
 * {@link WebApplication} bean makes the starter's {@code @ConditionalOnMissingBean} default
 * back off, and {@code @SpringBean} injects a Spring-managed service into the page that gets
 * rendered through a real embedded servlet container.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestRestTemplate
@Import(SpringBeanInjectionQuickstartTest.QuickstartConfig.class)
class SpringBeanInjectionQuickstartTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	void rendersCustomHomePageWithTheSpringInjectedGreeting() {
		String body = restTemplate.getForObject("/", String.class);

		assertThat(body).contains("Hello from Spring, Wicket!");
	}

	@TestConfiguration
	static class QuickstartConfig {

		@Bean
		WebApplication webApplication() {
			return new QuickstartWebApplication();
		}

		@Bean
		GreetingService greetingService() {
			return () -> "Hello from Spring, Wicket!";
		}
	}
}
