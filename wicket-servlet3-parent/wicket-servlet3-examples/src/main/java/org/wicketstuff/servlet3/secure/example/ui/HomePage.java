/*
 * Copyright 2014 WicketStuff.
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
package org.wicketstuff.servlet3.secure.example.ui;

import org.apache.wicket.markup.html.link.StatelessLink;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.wicketstuff.servlet3.secure.example.ui.admin.AdminPage;
import org.wicketstuff.servlet3.secure.example.ui.pkgExample.Page3;
import org.wicketstuff.servlet3.secure.example.ui.user.Page2;
import org.wicketstuff.wicket.mount.core.annotation.MountPath;

/**
 * Homepage is just links to the secure pages.  The Homepage exposes
 * the AdminPage as a link so if you login with unauthorized role
 * you will get the unauthorized page.   Typically you would not
 * display a link without proper auth but this is to demo the ability
 * to prevent a developer error(expose a link to unauthorized page) from
 * exposing a security hole. wicket-auth-roles deserves the credit for that!
 *
 * @author jsarman
 */
@MountPath("index.html")
public class HomePage extends BasePage
{

	public HomePage()
	{
		this(new PageParameters());
	}

	public HomePage(PageParameters parameters)
	{
		add(new StatelessLink("next")
		{

			@Override
			public void onClick()
			{
				setResponsePage(Page2.class); //Bookmarkable response
			}
		});
		add(new StatelessLink("next2")
		{

			@Override
			public void onClick()
			{
				setResponsePage(new AdminPage()); //non-bookmarkable response
			}
		});
		add(new StatelessLink("next3")
		{

			@Override
			public void onClick()
			{
				setResponsePage(new Page3()); //page3 authorization set at package. Also non-bookmarkable
			}
		});

	}

}
