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
package org.wicketstuff.hugerte.examples;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;

import org.wicketstuff.hugerte.HugeRteCsp;

/**
 * Example application for the HugeRTE integration. It keeps Wicket's strict Content Security Policy
 * and adds only what HugeRTE needs. wicket-webjars is installed by the module's initializer.
 */
public class ExampleApplication extends WebApplication
{
	@Override
	protected void init()
	{
		super.init();
		HugeRteCsp.allow(getCspSettings().blocking());

		mountPage("simple", SimplePage.class);
		mountPage("full", FullFeaturedPage.class);
		mountPage("ajax", AjaxPage.class);
	}

	@Override
	public Class<? extends Page> getHomePage()
	{
		return SimplePage.class;
	}
}
