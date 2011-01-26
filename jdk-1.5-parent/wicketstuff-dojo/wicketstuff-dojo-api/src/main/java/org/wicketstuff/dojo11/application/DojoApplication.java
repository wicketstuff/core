/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wicketstuff.dojo11.application;

import org.apache.wicket.Application;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;

/**
 * @author stf
 */
public abstract class DojoApplication extends WebApplication implements IDojoApplication
{
	private IDojoSettings _dojoSettings;

	/**
	 * @return IDojoApplication or null if Applicatoin.get() doesn't implement it
	 */
	public static IDojoApplication getDojoApplication() {
		Application app = Application.get();
		return (app instanceof IDojoApplication) ? (IDojoApplication) app : null;
	}
	
	protected void internalInit()
	{
		super.internalInit();
		_dojoSettings = newDojoSettings();
	}
	
	/**
	 * configured dojo settings
	 * @return this
	 */
	protected IDojoSettings newDojoSettings()
	{
		return new DojoSettings(this).configure();
	}

	/**
	 * @see org.wicketstuff.dojo11.application.IDojoApplication#getDojoSettings()
	 */
	public IDojoSettings getDojoSettings()
	{
		return _dojoSettings;
	}

	/**
	 * @see org.wicketstuff.dojo11.application.IDojoApplication#getLayer(org.apache.wicket.markup.html.WebPage)
	 */
	public String getLayer(WebPage page)
	{
		return null;
	}
	
	
}
