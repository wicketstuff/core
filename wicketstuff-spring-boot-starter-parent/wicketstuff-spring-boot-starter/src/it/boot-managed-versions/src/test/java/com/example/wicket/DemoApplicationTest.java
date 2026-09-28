package com.example.wicket;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Boots the application in a real servlet container, with every library at the version Spring
 * Boot manages, and exercises the starter end to end: the user's WebApplication is picked up,
 * {@code @SpringBean} injection works in pages, and Spring MVC still answers behind the Wicket
 * filter.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DemoApplicationTest {

	@LocalServerPort
	private int port;

	@Test
	void rendersAWicketPageWithAnInjectedSpringBean() throws Exception {
		HttpResponse<String> response = get("/");

		assertThat(response.statusCode()).isEqualTo(200);
		assertThat(response.body()).contains("Hello from a Spring bean");
	}

	@Test
	void springMvcAnswersBehindTheWicketFilter() throws Exception {
		HttpResponse<String> response = get("/api/ping");

		assertThat(response.statusCode()).isEqualTo(200);
		assertThat(response.body()).isEqualTo("pong");
	}

	private HttpResponse<String> get(String path) throws Exception {
		HttpClient client = HttpClient.newBuilder().followRedirects(HttpClient.Redirect.NORMAL).build();
		return client.send(HttpRequest.newBuilder(URI.create("http://localhost:" + port + path)).build(),
				HttpResponse.BodyHandlers.ofString());
	}
}
