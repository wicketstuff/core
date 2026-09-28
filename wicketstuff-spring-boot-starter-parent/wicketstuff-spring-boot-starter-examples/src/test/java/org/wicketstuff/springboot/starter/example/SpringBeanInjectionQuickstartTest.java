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
 *
 * @author Daniel Bartl
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
