/*
 * Copyright 2012 Igor Vaynberg
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this work except in compliance with
 * the License. You may obtain a copy of the License in the LICENSE file, or at:
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package org.wicketstuff.select2;

import org.apache.wicket.protocol.http.WebApplication;

/**
 * Application object
 */
public class WicketApplication extends WebApplication
{
	public static String COUNTRIES_MOUNT_PATH = "countries/";

	@Override
	protected void init() {
		super.init();

		// mount a countries resource
		mountResource(COUNTRIES_MOUNT_PATH, new JsonResourceReference<Country>("countries")
		{
			private static final long serialVersionUID = 1L;

			@Override
			protected ChoiceProvider<Country> getChoiceProvider() {
				return new HomePage.CountriesProvider();
			}
		});
	}

	@Override
	public Class<HomePage> getHomePage()
	{
		return HomePage.class;
	}
}
