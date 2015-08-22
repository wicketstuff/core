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
package org.apache.wicket.security.examples.httplogin.basic.pages;

import org.apache.wicket.security.components.SecureWebPage;

/**
 * Home page, you need to login in with basic http authentication to get here. Note that
 * normally with http authentication every page again requests the authentication headers,
 * not here because the application takes over the authentication part after the user has
 * logged in.
 * 
 * @author marrink
 */
public class HomePage extends SecureWebPage
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Construct.
	 */
	public HomePage()
	{
	}
}
