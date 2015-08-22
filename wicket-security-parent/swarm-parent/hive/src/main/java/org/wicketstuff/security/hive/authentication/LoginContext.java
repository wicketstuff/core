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
package org.wicketstuff.security.hive.authentication;

import org.wicketstuff.security.authentication.LoginException;

/**
 * A LoginContext is little more than a factory to create a {@link Subject} and can be discarded
 * afterwards. Usually it contains some credentials such as username and password. Note that
 * generally it is no a good idea to store those type of credentials in the session, so if you plan
 * on keeping this context in the session be sure to clear them before you return a Subject in
 * {@link #login()}. Some applications will require you to login with two or more different
 * LoginContexts before a user is fully authenticated. For that purpose a sortOrder is available in
 * the context. which is used in descending order to pass authentication requests to the subjects
 * until one of them authenticates. Sort orders are &gt;=0 and are not required to have an interval
 * of 1. For example 0, 5,6 are all perfectly legal sort orders for one user. Duplicates are also
 * allowed, in that case they are queried in reverse order of login. The context also contains a
 * flag to indicate if an additional login is allowed. Note that both the sort order and the
 * additional login flag must be constant. Also note that all LoginContexts of the same class and
 * with the same sort order are equal, thus for logoff you do not need to keep a reference to the
 * context but can simply use a new instance.
 * 
 * @author marrink
 * @see #preventsAdditionalLogins()
 */
public abstract class LoginContext
{
	private final int sortOrder;

	private final boolean additionalLoginsPrevented;

	/**
	 * Constructs a context for single login applications. At sortorder 0 and preventing additional
	 * logins.
	 */
	public LoginContext()
	{
		this(0, false);
	}

	/**
	 * Constructs a new context at the specified sort order. Additional logins are prevented. This
	 * constructor is usually used in mult-login scenario's for the context with the the highest
	 * sort order.
	 * 
	 * @param sortOrder
	 *            a number of 0 or higher.
	 */
	public LoginContext(int sortOrder)
	{
		this(sortOrder, false);
	}

	/**
	 * Constructs a new context with sort order 0 and a customizable flag for preventing additional
	 * logins. This constructor is mostly used in multi-login scenario's.
	 * 
	 * @param allowAdditionalLogings
	 *            indicates if this context allows multiple subjects for one user and thus allows
	 *            the user to gain more permissions on the fly.
	 */
	public LoginContext(boolean allowAdditionalLogings)
	{
		this(0, allowAdditionalLogings);
	}

	/**
	 * Constructs a new context with customizable sort order and flag for preventing additional
	 * logins. This constructor is mostly used in multi-login scenario's.
	 * 
	 * @param sortOrder
	 * @param allowAdditionalLogins
	 */
	public LoginContext(int sortOrder, boolean allowAdditionalLogins)
	{
		if (sortOrder < 0)
			throw new IllegalArgumentException("0 is the lowest sort order allowed, not " +
				sortOrder);
		this.sortOrder = sortOrder;
		additionalLoginsPrevented = !allowAdditionalLogins;
	}

	/**
	 * Perform a login. If the login fails in any way a {@link LoginException} must be thrown rather
	 * then returning null. You should clear all sensitive data stored in this context before
	 * returning the subject or throwing an exception.
	 * 
	 * @return a {@link Subject}, never null.
	 * @throws LoginException
	 *             if an exception occurs or if the subject could not login for some other reason
	 */
	public abstract Subject login() throws LoginException;

	/**
	 * Indicates the sort order of this context. the higher the value the more you are authorized /
	 * authenticated for.
	 * 
	 * @return the level
	 */
	protected final int getSortOrder()
	{
		return sortOrder;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public final int hashCode()
	{
		final int PRIME = 31;
		int result = 1;
		// classname to get consistent hash over different jvm instances.
		result = PRIME * result + getClass().getName().hashCode();
		result = PRIME * result + sortOrder;
		return result;
	}

	/**
	 * A loginContext is equal to a LoginContext of the same class (not subclass) and level.
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public final boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!getClass().getName().equals(obj.getClass().getName()))
			return false;
		final LoginContext other = (LoginContext)obj;
		return sortOrder == other.sortOrder;
	}

	/**
	 * Signals that no additional context should be allowed to login. The return value must be
	 * constant from one invocation to another for this instance. This flag is checked once by the
	 * container immediately after {@link #login()}. Note in a multi login environment you will want
	 * your logincontext with the highest possible sort order to prevent additional logins. In a
	 * single login environment your logincontext should always prevent additional logins.
	 * 
	 * @return true if you do not want additional logins for this session, false otherwise.
	 */
	public boolean preventsAdditionalLogins()
	{
		return additionalLoginsPrevented;
	}

	/**
	 * Callback to take some action after a subject has been logged off. Note that the LoginContext
	 * receiving this notification is not necessarily the same instance that created the subject.
	 * This is because Swarm does not store LoginContexts as they may contain sensitive data. By
	 * default this method does nothing.
	 * 
	 * @param subject
	 *            the user that has just been logged off
	 */
	public void notifyLogoff(Subject subject)
	{

	}
}
