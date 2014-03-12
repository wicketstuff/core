/*
 * Copyright 2014 Apache Software Foundation.
 *
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
package org.wicketstuff.wicket.mount.core;

import org.apache.wicket.protocol.http.WebApplication;

/**
 * Convenience class to auto mount pages from generated code.
 *
 * @author jsarman
 */
public class AutoMounter
{

	public static boolean mountAll(WebApplication app)
	{
		String mapInfoClassName = app.getClass().getCanonicalName() + "MountInfo";
		try
		{
			MountInfo mountInfo = (MountInfo) Class.forName(mapInfoClassName).newInstance();
			mountInfo.getMountList().mount(app);
			return true;
		} catch (Exception ex)
		{
			return false;
		}
	}
}
