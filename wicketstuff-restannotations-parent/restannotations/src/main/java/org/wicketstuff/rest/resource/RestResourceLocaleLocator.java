/**
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.wicketstuff.rest.resource;

import org.apache.wicket.Session;

import java.util.Locale;

/**
 * A singleton which locates {@link Locale} for {@link AbstractRestResource}
 */
public class RestResourceLocaleLocator
{

	private static RestResourceLocaleLocator instance;

	private LocaleResolver localeResolver;

	public Locale getLocale()
	{
		return localeResolver != null ? localeResolver.getLocale() : Session.get().getLocale();
	}

	/**
	 * Configure the locator singleton with a {@link Locale} resolver
	 * 
	 * @param localeResolver
	 *            {@link LocaleResolver} can be a {@link FunctionalInterface}
	 */
	public void setLocaleResolver(LocaleResolver localeResolver)
	{
		this.localeResolver = localeResolver;
	}

	public static RestResourceLocaleLocator get()
	{
		if (instance == null)
		{
			instance = new RestResourceLocaleLocator();
		}
		return instance;
	}

}
