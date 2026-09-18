package org.wicketstuff.springboot.starter.example;

import org.apache.wicket.protocol.http.WebApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Basic Spring Boot context integration test.
 */
@SpringBootTest
public class ExampleApplicationTest {

	@Autowired
	private WebApplication webApplication;

	@Test
	public void contextLoads() {
		assertNotNull(webApplication);
	}
}
