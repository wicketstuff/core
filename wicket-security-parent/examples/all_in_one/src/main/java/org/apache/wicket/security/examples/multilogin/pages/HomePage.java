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
package org.apache.wicket.security.examples.multilogin.pages;

import org.apache.wicket.security.examples.multilogin.components.navigation.ButtonContainer;

/**
 * The home page, this one is secured with a login.
 * 
 * @author marrink
 * 
 */
public class HomePage extends SecurePage
{

	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public HomePage()
	{
		add(new ButtonContainer("buttoncontainer", ButtonContainer.BUTTON_HOME));
	}
}
