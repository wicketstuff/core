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

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;

/**
 * Mirrors the custom {@code WebApplication} from the README's Quickstart section: a
 * hand-written subclass that the starter's {@code @ConditionalOnMissingBean} default backs
 * off for, once it is registered as a Spring bean.
 *
 * @author Daniel Bartl
 */
class QuickstartWebApplication extends WebApplication {

	@Override
	public Class<? extends Page> getHomePage() {
		return QuickstartHomePage.class;
	}
}
