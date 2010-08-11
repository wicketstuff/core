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

package org.wicketstuff.maven;

import org.apache.wicket.util.file.File;
import org.apache.wicket.util.resource.FileResourceStream;
import org.apache.wicket.util.resource.IResourceStream;
import org.apache.wicket.util.resource.locator.ResourceStreamLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Get resources from maven project directory.
 * 
 * Usage:
 *
 * 	<code>
 * 	class MyApp extends WebApplication
 * 	{
 * 		@Override
 * 		protected void init()
 * 		{
 * 			...
 * 			if (DEVELOPMENT.equals(getConfigurationType()))
 * 			{
 * 				getResourceSettings().setResourceStreamLocator(new MavenDevResourceStreamLocator());
 * 			}
 * 			...
 * 		}
 * 	}
 * </code>
 * 
 * @author mosmann
 *
 */
public class MavenDevResourceStreamLocator extends ResourceStreamLocator
{
	private static final Logger _logger = LoggerFactory.getLogger(MavenDevResourceStreamLocator.class);
	
	final static String MAVEN_RESOURCE_PATH="src/main/resources/";
	final static String MAVEN_JAVASOURCE_PATH="src/main/java/";
	
	public IResourceStream locate(final Class<?> clazz, final String path)
	{
		IResourceStream located=getFileSysResourceStream(MAVEN_RESOURCE_PATH,path);
		if (located==null)
		{
			located=getFileSysResourceStream(MAVEN_JAVASOURCE_PATH,path);
		}
		if (located != null)
		{
			_logger.info("Locate: {} in {} -> {}",new Object[] {clazz,path,located});
			return located;
		}
		located=super.locate(clazz, path);
		_logger.info("Locate: {} in {} -> {}(Fallback)",new Object[] {clazz,path,located});		
		return located;
	}

	private static IResourceStream getFileSysResourceStream(String prefix, String path)
	{
		File f=new File(prefix+path);
		if ((f.exists()) && (f.isFile()))
		{
			return new FileResourceStream(f);
		}
		else
		{
			_logger.debug("Could not get File: {}",f.getAbsolutePath());
		}
		return null;
	}
}
