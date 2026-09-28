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

import org.apache.wicket.RuntimeConfigurationType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.wicketstuff.springboot.starter.WicketProperties;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Verifies that the {@code wicket.*} properties set in this example's
 * {@code application.properties} are actually bound and applied to the registered Wicket
 * filter, rather than the starter's built-in defaults.
 *
 * @author Daniel Bartl
 */
@SpringBootTest
class WicketPropertiesBindingTest {

	@Autowired
	private WicketProperties wicketProperties;

	@Autowired
	private FilterRegistrationBean<?> wicketFilterRegistration;

	@Test
	void bindsWicketPropertiesFromApplicationProperties() {
		assertThat(wicketProperties.getFilterPath()).isEqualTo("/*");
		assertThat(wicketProperties.getFilterName()).isEqualTo("wicket-example-filter");
		assertThat(wicketProperties.getConfiguration()).isEqualTo(RuntimeConfigurationType.DEVELOPMENT);
	}

	@Test
	void appliesThePropertiesToTheRegisteredWicketFilter() {
		assertThat(wicketFilterRegistration.getFilterName()).isEqualTo("wicket-example-filter");
		assertThat(wicketFilterRegistration.getUrlPatterns()).containsExactly("/*");
	}
}
