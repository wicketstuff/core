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
package org.wicketstuff.security.hive.authorization;

import org.wicketstuff.security.hive.authentication.Subject;

/**
 * Principal used for permissions granted to everyone, regardless of an authenticated subject. This
 * is the principal used when you specify a grant statement without principal in the policy file.
 * You should not need to manually add this principal to your {@link Subject}.
 * 
 * @author marrink
 * 
 */
public final class EverybodyPrincipal implements Principal
{
	private static final long serialVersionUID = 1L;

	/**
	 * creates a new Principal named "everybody";
	 */
	public EverybodyPrincipal()
	{
	}

	/**
	 * Returns the name everybody.
	 * 
	 * @return the name of this principal
	 * @see java.security.Principal#getName()
	 */
	public String getName()
	{
		return "everybody";
	}

	/**
	 * Always returns true.
	 * 
	 * @see org.wicketstuff.security.hive.authorization.Principal#implies(org.wicketstuff.security.hive.authentication.Subject)
	 */
	public boolean implies(Subject subject)
	{
		return true;
	}

	/**
	 * This principal equals every instance of the class.
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (obj == this)
			return true;
		if (obj == null)
			return false;
		return obj.getClass() == this.getClass();

	}

	/**
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		return getClass().hashCode();
	}

	/**
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return getName();
	}

}
