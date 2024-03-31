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
package org.wicketstuff.security.swarm.checks;

import org.wicketstuff.security.actions.WaspAction;
import org.wicketstuff.security.checks.AbstractSecurityCheck;
import org.wicketstuff.security.hive.authorization.permissions.DataPermission;
import org.wicketstuff.security.swarm.strategies.AbstractSwarmStrategy;

/**
 * SecurityCheck that uses a {@link DataPermission}. For example <br/>
 * <code>
 * new DataSecurityCheck("something");
 * </code><br/>
 * requires the following permission in your policy file:<br/>
 * <code>permission ${DataPermission} "something", "render";</code><br/>
 * if you want the component(s) having that securitycheck to be visible.
 * 
 * @author marrink
 */
public class DataSecurityCheck extends AbstractSecurityCheck
{
	private static final long serialVersionUID = 1L;

	private final String securityId;

	/**
	 * Creates a check that will verify if the current user has a DataPermission with the specified
	 * name.
	 * 
	 * @param securityId
	 *            the name of the DataPermission in your policy file
	 */
	public DataSecurityCheck(String securityId)
	{
		this.securityId = securityId;
	}

	/**
	 * @see org.wicketstuff.security.checks.ISecurityCheck#isActionAuthorized(org.wicketstuff.security.actions.WaspAction)
	 */
	public boolean isActionAuthorized(WaspAction action)
	{
		DataPermission permission = new DataPermission(getSecurityId(), action);
		return ((AbstractSwarmStrategy)getStrategy()).hasPermission(permission);
	}

	/**
	 * @see org.wicketstuff.security.checks.ISecurityCheck#isAuthenticated()
	 */
	public boolean isAuthenticated()
	{
		return getStrategy().isUserAuthenticated();
	}

	/**
	 * The id / name from the {@link DataPermission}.
	 * 
	 * @return securityId
	 */
	public final String getSecurityId()
	{
		return securityId;
	}

}
