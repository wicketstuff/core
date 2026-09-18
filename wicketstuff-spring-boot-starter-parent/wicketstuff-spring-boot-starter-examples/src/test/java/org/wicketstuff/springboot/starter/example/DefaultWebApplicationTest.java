package org.wicketstuff.springboot.starter.example;

import org.apache.wicket.protocol.http.WebApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.wicketstuff.springboot.starter.DefaultWebApplication;
import org.wicketstuff.springboot.starter.WicketAutoConfiguration;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Verifies that the starter auto-configures {@link DefaultWebApplication}
 * when no custom {@link WebApplication} bean is present in the application context.
 */
@SpringBootTest(classes = DefaultWebApplicationTest.TestConfig.class)
public class DefaultWebApplicationTest {

	@Configuration
	@ImportAutoConfiguration(WicketAutoConfiguration.class)
	static class TestConfig {
	}

	@Autowired
	private WebApplication webApplication;

	@Test
	public void testDefaultWebApplicationIsLoaded() {
		assertNotNull(webApplication);
		assertTrue(webApplication instanceof DefaultWebApplication);
	}
}
