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

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * Mirrors the custom {@code HomePage} from the README's Quickstart section: a Wicket page
 * that has a Spring-managed service injected via {@code @SpringBean}.
 *
 * @author Daniel Bartl
 */
public class QuickstartHomePage extends WebPage {
	private static final long serialVersionUID = 1L;

	@SpringBean
	private GreetingService greetingService;

	public QuickstartHomePage() {
		add(new Label("message", greetingService.greet()));
	}
}
