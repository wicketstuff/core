package org.wicketstuff.springboot.starter.example;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * The starter depends on {@code spring-boot-starter-web}, so every application also has Spring
 * MVC's {@code DispatcherServlet} while Wicket's filter is mapped at {@code /*}. This pins down
 * that the two coexist: Wicket serves its pages and passes anything it does not handle further
 * down the filter chain, so {@code @RestController} endpoints stay reachable.
 *
 * @author Daniel Bartl
 */
@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
		properties = "wicket.filter-name=mvc-coexistence-filter")
@AutoConfigureTestRestTemplate
@Import(WicketAndSpringMvcCoexistenceTest.GreetingController.class)
class WicketAndSpringMvcCoexistenceTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	void springMvcEndpointsRemainReachableBehindTheWicketFilter() {
		ResponseEntity<String> response = restTemplate.getForEntity("/api/greeting", String.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isEqualTo("hello from spring mvc");
	}

	@Test
	void wicketStillServesItsOwnPages() {
		ResponseEntity<String> response = restTemplate.getForEntity("/", String.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).contains("Wicket Spring Boot Starter");
	}

	@RestController
	static class GreetingController {

		@GetMapping("/api/greeting")
		String greeting() {
			return "hello from spring mvc";
		}
	}
}
