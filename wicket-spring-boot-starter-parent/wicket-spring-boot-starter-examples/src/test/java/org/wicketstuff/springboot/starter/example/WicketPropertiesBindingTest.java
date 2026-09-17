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
