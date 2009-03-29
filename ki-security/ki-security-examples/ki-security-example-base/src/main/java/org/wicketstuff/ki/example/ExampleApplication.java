/*
 * Copyright 2008 Les Hazlewood
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wicketstuff.ki.example;

import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;
import org.wicketstuff.ki.example.pages.IndexPage;
import org.wicketstuff.ki.example.pages.LoginPage;
import org.wicketstuff.ki.example.pages.RequireAdminRolePage;
import org.wicketstuff.ki.example.pages.RequireAuthPage;
import org.wicketstuff.ki.example.pages.RequireLoginPage;
import org.wicketstuff.ki.example.pages.RequireViewPermissionPage;
import org.wicketstuff.ki.example.pages.UnauthorizedPage;
import org.wicketstuff.ki.page.LogoutPage;
import org.wicketstuff.ki.strategy.KiAuthorizationStrategy;
import org.wicketstuff.ki.strategy.KiUnauthorizedComponentListener;

/**
 * 
 */
public abstract class ExampleApplication extends WebApplication
{

	@Override
	protected void init()
	{
		getMarkupSettings().setStripWicketTags(true);

		getSecuritySettings().setAuthorizationStrategy(new KiAuthorizationStrategy());

		getSecuritySettings().setUnauthorizedComponentInstantiationListener(
			new KiUnauthorizedComponentListener(LoginPage.class, UnauthorizedPage.class));

		mountBookmarkablePage("account/login", LoginPage.class);
		mountBookmarkablePage("account/logout", LogoutPage.class);

		mountBookmarkablePage("user", RequireLoginPage.class);
		mountBookmarkablePage("admin", RequireAdminRolePage.class);
    mountBookmarkablePage("view", RequireViewPermissionPage.class);
    mountBookmarkablePage("auth", RequireAuthPage.class);
	}

  public abstract Component getExampleInfoPanel( String id );
  
	@Override
	public Class<? extends Page> getHomePage()
	{
		return IndexPage.class;
	}

//	@Override
//	// You'll need to do this only if using JSecurity enterprise/clustered Sessions:
//	protected ISessionStore newSessionStore()
//	{
//		return new SecondLevelCacheSessionStore(this, new SessionPageStore(100));
//	}
}
