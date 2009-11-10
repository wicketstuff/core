/*
 * 
 * ==============================================================================
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.wicketstuff.theme.tester;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.maven.MavenDevResourceStreamLocator;

public class WicketThemeTestApp extends WebApplication
{
	private static final Logger _logger = LoggerFactory.getLogger(WicketThemeTestApp.class);
	
	@Override
	protected void init()
	{
		super.init();
		
		if (DEVELOPMENT.equals(getConfigurationType()))
		{
			_logger.error("Debug Mode");
			getResourceSettings().setResourceStreamLocator(new MavenDevResourceStreamLocator());
		}
		else
		{
			_logger.error("NO Debug Mode");
		}
	}
	
	@Override
	public Class<? extends Page> getHomePage()
	{
		return ThemeTestPage.class;
	}
}
