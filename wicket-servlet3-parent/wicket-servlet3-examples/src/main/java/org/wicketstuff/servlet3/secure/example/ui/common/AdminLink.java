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

package org.wicketstuff.servlet3.secure.example.ui.common;

import org.apache.wicket.authorization.Action;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeAction;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeActions;
import org.apache.wicket.markup.html.link.StatelessLink;

/**
 * Example Helper class to demonstrate using an AuthorizedAction to only render
 * a link if the authenticated user has proper authorization. Once again wicket-auth-roles
 * gets the credit for this feature!
 *
 * @author jsarman
 */
@AuthorizeActions(actions = {
		@AuthorizeAction(action = Action.RENDER, roles = {"tomcat", "admin"})
})
public abstract class AdminLink extends StatelessLink
{

	public AdminLink(String id)
	{
		super(id);
	}


}
