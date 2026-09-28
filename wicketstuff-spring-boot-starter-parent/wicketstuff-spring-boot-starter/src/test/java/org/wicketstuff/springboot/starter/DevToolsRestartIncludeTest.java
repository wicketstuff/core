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

import java.io.File;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Guards {@code META-INF/spring-devtools.properties}, which moves Wicket into Spring Boot
 * DevTools' restart classloader. DevTools matches each {@code restart.include.*} pattern against
 * the URL of every classpath entry; a jar that references Wicket types but stays in the base
 * classloader breaks page deserialization, so every Wicket and wicketstuff jar must match and
 * nothing else should.
 *
 * @author Daniel Bartl
 */
class DevToolsRestartIncludeTest {

	@Test
	void restartIncludePatternsMatchExactlyTheWicketJarsOnTheClasspath() throws Exception {
		List<Pattern> patterns = restartIncludePatterns();
		List<Path> jars = Arrays.stream(System.getProperty("java.class.path").split(File.pathSeparator))
				.map(Path::of)
				.filter(path -> path.getFileName().toString().endsWith(".jar"))
				.toList();

		List<String> wicketJars = jars.stream()
				.filter(jar -> jar.toString().contains("/org/apache/wicket/"))
				.map(DevToolsRestartIncludeTest::url)
				.toList();
		List<String> otherJars = jars.stream()
				.filter(jar -> !jar.toString().contains("/org/apache/wicket/")
						&& !jar.toString().contains("/org/wicketstuff/"))
				.map(DevToolsRestartIncludeTest::url)
				.toList();

		assertThat(wicketJars).as("Wicket jars on the test classpath").isNotEmpty()
				.allSatisfy(url -> assertThat(matchesAny(patterns, url)).as(url).isTrue());
		assertThat(otherJars)
				.allSatisfy(url -> assertThat(matchesAny(patterns, url)).as(url).isFalse());
		assertThat(matchesAny(patterns, url(Path.of("/repo/org/wicketstuff/wicketstuff-spring-boot-starter/"
				+ "10.12.0/wicketstuff-spring-boot-starter-10.12.0.jar"))))
				.as("the starter itself").isTrue();
	}

	private static List<Pattern> restartIncludePatterns() throws Exception {
		Properties properties = new Properties();
		try (InputStream in = DevToolsRestartIncludeTest.class
				.getResourceAsStream("/META-INF/spring-devtools.properties")) {
			assertThat(in).as("META-INF/spring-devtools.properties").isNotNull();
			properties.load(in);
		}
		return properties.stringPropertyNames().stream()
				.filter(name -> name.startsWith("restart.include."))
				.map(name -> Pattern.compile(properties.getProperty(name)))
				.toList();
	}

	private static boolean matchesAny(List<Pattern> patterns, String url) {
		return patterns.stream().anyMatch(pattern -> pattern.matcher(url).find());
	}

	private static String url(Path jar) {
		return jar.toUri().toString();
	}
}
