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
	void linksItsStylesheetAndLogoAsPackageResourcesRatherThanFromTheApplicationRoot() {
		tester.startPage(DefaultHomePage.class);

		String markup = tester.getLastResponseAsString();
		assertThat(markup)
				.contains("wicket/resource/org.wicketstuff.springboot.starter.DefaultHomePage/style")
				.contains("wicket/resource/org.wicketstuff.springboot.starter.DefaultHomePage/logo")
				.doesNotContain("href=\"style.css\"")
				.doesNotContain("src=\"logo.png\"");
	}

	@Test
	void servesTheStylesheetAsAPackageResource() {
		tester.startResourceReference(new PackageResourceReference(DefaultHomePage.class, "style.css"));

		assertThat(tester.getLastResponse().getStatus()).isEqualTo(200);
	}

	@Test
	void servesTheLogoAsAPackageResource() {
		tester.startResourceReference(new PackageResourceReference(DefaultHomePage.class, "logo.png"));

		assertThat(tester.getLastResponse().getStatus()).isEqualTo(200);
	}
}
