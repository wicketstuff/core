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
package org.wicketstuff.security.components;

import java.io.Serializable;

import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.authorization.IAuthorizationStrategy;
import org.wicketstuff.security.actions.WaspAction;
import org.wicketstuff.security.checks.ISecurityCheck;

/**
 * Some methods for secure components to easier get a hold of the securitycheck. Components not
 * implementing this interface can still get or set a securitycheck on them with the
 * {@link SecureComponentHelper}. implementations should as default use these methods to wrap the
 * calls to {@link SecureComponentHelper}, although classes like {@link IAuthorizationStrategy}
 * should not depend on this behavior.
 * 
 * @author marrink
 */
public interface ISecureComponent extends Serializable
{
	/**
	 * Sets (or removes in the case of null) the security check on this component.
	 * 
	 * @param check
	 */
	public void setSecurityCheck(ISecurityCheck check);

	/**
	 * Gets the security check attached to this component.
	 * 
	 * @return the check or null if there is no check
	 */
	public ISecurityCheck getSecurityCheck();

	/**
	 * Wrapper method for the isActionAuthorized method on component. Subclasses can use the default
	 * implementation in SecureComponentHelper.
	 * 
	 * @param waspAction
	 * @return true if the action is allowed, false otherwise.
	 * @see Component#isActionAuthorized(org.apache.wicket.authorization.Action)
	 * @see SecureComponentHelper#isActionAuthorized(Component, String)
	 */
	public boolean isActionAuthorized(String waspAction);

	/**
	 * Wrapper method for the isActionAuthorized method on component. Subclasses can use the default
	 * implementation in SecureComponentHelper.
	 * 
	 * @param action
	 * @return true if the action is allowed, false otherwise.
	 * @see Component#isActionAuthorized(org.apache.wicket.authorization.Action)
	 * @see SecureComponentHelper#isActionAuthorized(Component, WaspAction)
	 */
	public boolean isActionAuthorized(WaspAction action);

	/**
	 * authenticates the user for this component. Note authentication should usually only be done by
	 * {@link Page}s.
	 * 
	 * @return true if the user is authenticated, false otherwise
	 */
	public boolean isAuthenticated();
}
