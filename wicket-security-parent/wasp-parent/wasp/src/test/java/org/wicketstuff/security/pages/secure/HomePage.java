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
package org.wicketstuff.security.pages.secure;

import org.apache.wicket.markup.html.basic.Label;
import org.wicketstuff.security.checks.InverseSecurityCheck;
import org.wicketstuff.security.components.SecureComponentHelper;
import org.wicketstuff.security.components.markup.html.links.SecurePageLink;
import org.wicketstuff.security.pages.SecureTestPage;

/**
 * @author marrink
 * 
 */
public class HomePage extends SecureTestPage
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public HomePage()
	{
		add(new Label("welcome", "Welcome Only logged in users can see this page"));
		SecurePageLink<Void> securePageLink = new SecurePageLink<Void>("link", PageA.class);
		add(securePageLink);
		add(SecureComponentHelper.setSecurityCheck(new Label("sorry",
			"you are not allowed to go to Page A"),
			new InverseSecurityCheck(securePageLink.getSecurityCheck())));
	}

}
