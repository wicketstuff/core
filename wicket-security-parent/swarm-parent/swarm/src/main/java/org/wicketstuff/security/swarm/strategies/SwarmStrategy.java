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
package org.wicketstuff.security.swarm.strategies;

import java.util.Collections;
import java.util.Set;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.IResource;
import org.wicketstuff.security.actions.WaspAction;
import org.wicketstuff.security.authentication.LoginException;
import org.wicketstuff.security.components.ISecureComponent;
import org.wicketstuff.security.components.ISecurePage;
import org.wicketstuff.security.hive.Hive;
import org.wicketstuff.security.hive.HiveMind;
import org.wicketstuff.security.hive.authentication.LoginContainer;
import org.wicketstuff.security.hive.authentication.LoginContext;
import org.wicketstuff.security.hive.authentication.Subject;
import org.wicketstuff.security.hive.authorization.Permission;
import org.wicketstuff.security.hive.authorization.Principal;
import org.wicketstuff.security.log.IAuthorizationMessageSource;
import org.wicketstuff.security.strategies.ClassAuthorizationStrategy;
import org.wicketstuff.security.strategies.SecurityException;

/**
 * Implementation of a {@link ClassAuthorizationStrategy}. It allows for both simple logins as well
 * as multi level logins.
 * 
 * @author marrink
 */
public class SwarmStrategy extends AbstractSwarmStrategy
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Key to the hive.
	 */
	private Object hiveQueen;

	/**
	 * Constructs a new strategy linked to the specified hive.
	 * 
	 * @param hiveQueen
	 *            A key to retrieve the {@link Hive}
	 */
	public SwarmStrategy(Object hiveQueen)
	{
		this(ISecurePage.class, hiveQueen);
	}

	/**
	 * Constructs a new strategy linked to the specified hive.
	 * 
	 * @param secureClass
	 *            instances of this class will be required to have access authorization.
	 * @param hiveQueen
	 *            A key to retrieve the {@link Hive}
	 */
	public SwarmStrategy(Class<? extends ISecureComponent> secureClass, Object hiveQueen)
	{
		super(secureClass);
		this.hiveQueen = hiveQueen;
		loginContainer = new LoginContainer();
	}

	/**
	 * Returns the hive.
	 * 
	 * @return the hive.
	 * @throws SecurityException
	 *             if no hive is registered.
	 */
	protected final Hive getHive()
	{
		Hive hive = HiveMind.getHive(hiveQueen);
		if (hive == null)
			throw new SecurityException("No hive registered for " + hiveQueen);
		return hive;
	}

	/**
	 * Performs the actual permission check at the {@link Hive}.
	 * 
	 * @param permission
	 *            the permission to verify
	 * @param subject
	 *            optional subject to test against the permission
	 * @return true if the subject has or implies the permission, false otherwise
	 * @throws SecurityException
	 *             if the permission is null
	 */
	@Override
	public boolean hasPermission(Permission permission, Subject subject)
	{
		if (permission == null)
			throw new SecurityException("permission is not allowed to be null");
		if (getHive().hasPermission(subject, permission))
			return true;
		logPermissionDenied(permission, subject);
		return false;

	}

	/**
	 * Logs (if logging is enabled) which permission was denied for a subject. This method does not
	 * log directly but prepares an {@link IAuthorizationMessageSource} for later retrieval. The
	 * following variables are stored: "permission","actions", "subject" and "principals" where
	 * principals is a collection of principals that contain the permission and actions is a
	 * {@link String} representing all the {@link WaspAction}s required. Note that the subject
	 * variable might be null.
	 * 
	 * @param permission
	 *            permission that was denied.
	 * @param subject
	 *            optional subject
	 * @see #logMessages()
	 * @see #getMessageSource()
	 */
	protected void logPermissionDenied(Permission permission, Subject subject)
	{
		IAuthorizationMessageSource source = getMessageSource(logMessages());
		if (source == null)
			return;
		// note that even if logErrorMessages returns false we still populate
		// the source with variables if there is a source
		source.addVariable("permission", permission);
		source.addVariable("actions", permission.getActions());
		source.addVariable("subject", subject);
		Set<Principal> principals = getHive().getPrincipals(permission);
		if (!principals.isEmpty())
			source.addVariable("principals", principals);
		else
			source.addVariable("principals", Collections.EMPTY_SET);
	}

	/**
	 * Logs a user in. Note that the context must be an instance of {@link LoginContext}.
	 * 
	 * @see org.wicketstuff.security.strategies.WaspAuthorizationStrategy#login(java.lang.Object)
	 */
	@Override
	public void login(Object context) throws LoginException
	{
		if (context instanceof LoginContext)
		{
			loginContainer.login((LoginContext)context);
		}
		else
			throw new SecurityException("Unable to process login with context: " + context);
	}

	/**
	 * Loggs a user off. Note that the context must be an instance of {@link LoginContext} and must
	 * be the same (or equal) to the logincontext used to log in.
	 * 
	 * @see org.wicketstuff.security.strategies.WaspAuthorizationStrategy#logoff(Object)
	 */
	@Override
	public boolean logoff(Object context)
	{
		if (context instanceof LoginContext)
		{
			return loginContainer.logoff((LoginContext)context);
		}
		throw new SecurityException("Unable to process logoff with context: " + context);
	}

	/**
	 * The {@link LoginContainer} keeps track of all Subjects for this session..
	 * 
	 * @return loginContainer
	 */
	protected final LoginContainer getLoginContainer()
	{
		return loginContainer;
	}

	/**
	 * 
	 * @see org.wicketstuff.security.strategies.WaspAuthorizationStrategy#isUserAuthenticated()
	 */
	@Override
	public boolean isUserAuthenticated()
	{
		return getSubject() != null;
	}

    @Override
    public boolean isResourceAuthorized(IResource resource, PageParameters parameters) {
        return true;
    }
}
