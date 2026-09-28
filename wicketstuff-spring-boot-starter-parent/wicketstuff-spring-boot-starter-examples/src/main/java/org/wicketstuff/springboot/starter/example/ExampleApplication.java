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

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Runnable example for the starter.
 * <p>
 * There is deliberately nothing here beyond the Spring Boot entry point: no {@code WebApplication},
 * no page, no Wicket configuration. That is the point of the example — run this class and the
 * starter alone gives you a working Wicket application on
 * <a href="http://localhost:8080">http://localhost:8080</a>, serving its built-in placeholder page.
 * </p>
 * <p>
 * The patterns you would actually write in your own application are demonstrated by the
 * integration tests in {@code src/test/java}, where they are also verified on every build. See this
 * module's README for a map of which test shows what.
 * </p>
 *
 * @author Daniel Bartl
 */
@SpringBootApplication
public class ExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExampleApplication.class, args);
	}
}
