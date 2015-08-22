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
package org.wicketstuff.security.checks;

import java.io.Serializable;

import org.wicketstuff.security.actions.WaspAction;
import org.wicketstuff.security.components.ISecureComponent;
import org.wicketstuff.security.models.ISecureModel;
import org.wicketstuff.security.strategies.WaspAuthorizationStrategy;

/**
 * A securitycheck knows how to authorize or authenticate a user. and will decide if the class,
 * component, model or any combination of those 3 or something entirely different will be checked.
 * Usually a securitycheck is triggered by the
 * {@link WaspAuthorizationStrategy#isActionAuthorized(org.apache.wicket.Component, org.apache.wicket.authorization.Action)}
 * , {@link ISecureComponent#isActionAuthorized(WaspAction)} or by
 * {@link ISecureComponent#isAuthenticated()}. Usually they just redirect the call to the
 * {@link WaspAuthorizationStrategy}, but it is not unimaginable that securitychecks are targeted at
 * specific wasp implementations and take care of there authentication or authorization themself.
 * 
 * @author marrink
 */
public interface ISecurityCheck extends Serializable
{
	/**
	 * Checks if there are sufficient rights to perform the desired action(s). Note that we don't
	 * ask what needs to have these rights, the implementation will decide if it checks the class,
	 * component, model or whatever they like.
	 * 
	 * @param action
	 *            the action(s) like render or enable.
	 * @return true if there are sufficient rights, false otherwise.
	 * @see WaspAuthorizationStrategy#isComponentAuthorized(org.apache.wicket.Component, WaspAction)
	 * @see WaspAuthorizationStrategy#isClassAuthorized(Class, WaspAction)
	 * @see ISecureModel#isAuthorized(org.apache.wicket.Component, WaspAction)
	 */
	public boolean isActionAuthorized(WaspAction action);

	/**
	 * Checks if there is an authenticated user available. If not a page might decide to redirect to
	 * a login page instead. other components won't use this ordinarily.
	 * 
	 * @return true if an authenticated user is available, false otherwise.
	 * @see WaspAuthorizationStrategy#isComponentAuthenticated(org.apache.wicket.Component)
	 * @see WaspAuthorizationStrategy#isClassAuthenticated(Class)
	 * @see ISecureModel#isAuthenticated(org.apache.wicket.Component)
	 */
	public boolean isAuthenticated();
}
