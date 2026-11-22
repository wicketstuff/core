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
package org.wicketstuff.hugerte;

import org.apache.wicket.Application;
import org.apache.wicket.IApplicationListener;
import org.apache.wicket.IInitializer;
import org.apache.wicket.protocol.http.WebApplication;

import de.agilecoders.wicket.webjars.WicketWebjars;

/**
 * Installs wicket-webjars, which serves the HugeRTE scripts, when the application has not done so
 * itself.
 * <p>
 * The check runs after the application's {@code init()}, so an application that calls
 * {@code WicketWebjars.install(this, settings)} keeps its own settings. Registered through
 * {@code META-INF/services/org.apache.wicket.IInitializer}.
 */
public class HugeRteInitializer implements IInitializer
{
	@Override
	public void init(Application application)
	{
		application.getApplicationListeners().add(new IApplicationListener()
		{
			@Override
			public void onAfterInitialized(Application application)
			{
				if (application instanceof WebApplication web && !WicketWebjars.isInstalled(web))
				{
					WicketWebjars.install(web);
				}
			}
		});
	}

	@Override
	public void destroy(Application application)
	{
	}

	@Override
	public String toString()
	{
		return "HugeRTE initializer";
	}
}
