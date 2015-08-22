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
 * A very simple principal. It does not imply any subject on its own. This is only provided as a
 * convenience to get started. Developers are likely to build there own principal and decorate it
 * with jpa annotations to store / retrieve it from a database where they are coupled to users.
 * 
 * @author marrink
 */
public class SimplePrincipal implements Principal
{
	private static final long serialVersionUID = 1L;

	private final String name;

	/**
	 * Constructor.
	 * 
	 * @param name
	 *            the name of this principal
	 */
	public SimplePrincipal(String name)
	{
		if (name == null)
			throw new IllegalArgumentException("Name MUST be specified");
		this.name = name;
	}

	/**
	 * @see org.wicketstuff.security.hive.authorization.Principal#getName()
	 */
	public final String getName()
	{
		return name;
	}

	/**
	 * By default this principal does not imply any additional subjects.
	 * 
	 * @see org.wicketstuff.security.hive.authorization.Principal#implies(org.wicketstuff.security.hive.authentication.Subject)
	 */
	public boolean implies(Subject subject)
	{
		return false;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final SimplePrincipal other = (SimplePrincipal)obj;
		if (name == null)
		{
			if (other.name != null)
				return false;
		}
		else if (!name.equals(other.name))
			return false;
		return true;
	}

}
