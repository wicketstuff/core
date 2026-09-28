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
