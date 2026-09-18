package org.wicketstuff.springboot.starter;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Daniel Bartl
 */
class DefaultWebApplicationTest {

	private WicketTester tester;

	@BeforeEach
	void setUp() {
		tester = new WicketTester(new DefaultWebApplication());
	}

	@AfterEach
	void tearDown() {
		tester.destroy();
	}

	@Test
	void homePageIsTheDefaultHomePage() {
		assertThat(tester.getApplication().getHomePage()).isEqualTo(DefaultHomePage.class);
	}

	@Test
	void rendersDefaultHomePageWithTheWicketVersionLabel() {
		tester.startPage(DefaultHomePage.class);

		tester.assertRenderedPage(DefaultHomePage.class);
		tester.assertComponent("version", Label.class);
	}

	@Test
	void servesTheMountedCssResource() {
		tester.startResourceReference(new PackageResourceReference(DefaultHomePage.class, "style.css"));

		assertThat(tester.getLastResponse().getStatus()).isEqualTo(200);
	}

	@Test
	void servesTheMountedPngResource() {
		tester.startResourceReference(new PackageResourceReference(DefaultHomePage.class, "logo.png"));

		assertThat(tester.getLastResponse().getStatus()).isEqualTo(200);
	}
}
