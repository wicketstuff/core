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
package org.wicketstuff.servlet3.secure.example.ui.admin;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.wicketstuff.servlet3.secure.example.ui.BasePage;
import org.wicketstuff.wicket.mount.core.annotation.MountPath;

/**
 * Notice here we set an explicit mount path using @MountPath.
 * If you set an explicit mountpath on a page requiring authorization, make sure the
 * path matches a path in the security-constraints. Even if it doesn't wicket would catch it
 * if either user is not authenticated or user is authenticated but doesn't contain the required
 * role, but verifying the mountpath is part of the security-constraint is a double check in cases
 * where someone figures out a hack to the wicket-auth-roles.
 *
 * @author jsarman
 */
@MountPath("secure/admin/admin.html")
@AuthorizeInstantiation({"tomcat", "admin"})
public class AdminPage extends BasePage
{

	public AdminPage()
	{
		this(null);

	}

	public AdminPage(PageParameters parameters)
	{
		super(parameters);
		//Sensitive code goes here :)
	}

}
