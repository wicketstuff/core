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

import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.markup.html.basic.Label;
import org.wicketstuff.security.actions.Render;
import org.wicketstuff.security.actions.WaspAction;
import org.wicketstuff.security.checks.ClassSecurityCheck;
import org.wicketstuff.security.checks.ISecurityCheck;
import org.wicketstuff.security.components.SecureWebPage;

/**
 * Shows how to overide the default instantiation check. Although we only show instantiation checks
 * based on classes you could ofcource use something different like strings.
 * 
 * @author marrink
 */
public class PageC extends SecureWebPage // or SecureTestPage, really does
// not matter
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiation check that requires render rights instead of the default access rights.
	 */
	static final ISecurityCheck alternate = new ClassSecurityCheck(PageC.class)
	{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * @see org.wicketstuff.security.checks.ClassSecurityCheck#isActionAuthorized(org.wicketstuff.security.actions.WaspAction)
		 */
		@Override
		public boolean isActionAuthorized(WaspAction action)
		{
			if (isAuthenticated())
				return getStrategy().isClassAuthorized(getClazz(),
					action.add(getActionFactory().getAction(Render.class)));
			throw new RestartResponseAtInterceptPageException(getLoginPage());
		}

	};

	/**
	 * 
	 */
	public PageC()
	{
		add(new Label("welcome", "Demonstrates alternate instantiation check"));
	}

}
