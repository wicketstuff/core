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
package org.wicketstuff.security.models;

import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;
import org.wicketstuff.security.actions.WaspAction;
import org.wicketstuff.security.checks.ISecurityCheck;
import org.wicketstuff.security.strategies.WaspAuthorizationStrategy;

/**
 * ISecureModel is much like ISecurityCheck in that it knows how to authorize or authenticate a
 * user. Usually they just redirect the call to the {@link WaspAuthorizationStrategy}, but it is not
 * unimaginable that models are targeted at specific wasp implementations and take care of there
 * authentication or authorization themself. Note authentication should rarely take place at the
 * model level.
 * 
 * @author marrink
 * @see WaspAuthorizationStrategy
 * @see ISecurityCheck
 */
public interface ISecureModel<T> extends IModel<T>
{
	/**
	 * Checks if the component is authorized for this model.
	 * 
	 * @param component
	 *            the (owning) component of the model, some models might allow null.
	 * @param action
	 * @return true if the component is allowed to access this model, false otherwise
	 * @see WaspAuthorizationStrategy#isModelAuthorized(ISecureModel, Component, WaspAction)
	 */
	public boolean isAuthorized(Component component, WaspAction action);

	/**
	 * Checks if the user is authenticated for this model.
	 * 
	 * @param component
	 *            the (owning) component of the model, some models might allow null.
	 * @return true if the user is authenticated for this model, false otherwise.
	 * @see WaspAuthorizationStrategy#isModelAuthenticated(IModel, Component)
	 */
	public boolean isAuthenticated(Component component);
}
