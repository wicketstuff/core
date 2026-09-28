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

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Guards the version overrides in {@code wicketstuff-spring-boot-starter-parent}.
 * <p>
 * wicketstuff-core pins several libraries that Spring Boot also manages, and those inherited pins
 * win over the imported {@code spring-boot-dependencies} BOM. The parent pom therefore overrides
 * them with the versions Spring Boot ships, so the starter is built and tested against what its
 * users actually run. This test fails as soon as an override drifts from the BOM, e.g. after
 * Spring Boot is bumped or a dependency update touches one of the overrides.
 * </p>
 * <p>
 * The overrides reach this test as {@code aligned.<bom property>} system properties set by
 * Surefire, keyed by the name of the matching property in the Spring Boot BOM.
 * </p>
 *
 * @author Daniel Bartl
 */
class BuildAlignedWithSpringBootTest {

	private static final String PREFIX = "aligned.";

	@Test
	void versionOverridesMatchTheSpringBootBom() throws Exception {
		String bom = Files.readString(Path.of(System.getProperty("springBootBom")));

		Map<String, String> expected = new TreeMap<>();
		Map<String, String> actual = new TreeMap<>();
		System.getProperties().stringPropertyNames().stream()
				.filter(name -> name.startsWith(PREFIX))
				.forEach(name -> {
					String bomProperty = name.substring(PREFIX.length());
					expected.put(bomProperty, bomProperty(bom, bomProperty));
					actual.put(bomProperty, System.getProperty(name));
				});

		assertThat(actual)
				.as("version overrides in wicketstuff-spring-boot-starter-parent/pom.xml, keyed by the "
						+ "spring-boot-dependencies property they must equal")
				.isNotEmpty()
				.isEqualTo(expected);
	}

	private static String bomProperty(String bom, String name) {
		Matcher matcher = Pattern.compile("<" + Pattern.quote(name) + ">([^<]+)</").matcher(bom);
		return matcher.find() ? matcher.group(1) : "<missing from the Spring Boot BOM>";
	}
}
