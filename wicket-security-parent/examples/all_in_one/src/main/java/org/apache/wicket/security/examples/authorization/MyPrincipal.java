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
package org.apache.wicket.security.examples.authorization;

import org.apache.wicket.security.hive.authentication.Subject;
import org.apache.wicket.security.hive.authorization.Principal;

/**
 * Simplest of principal.
 * 
 * @author marrink
 * 
 */
public class MyPrincipal implements Principal
{
	private static final long serialVersionUID = 1L;

	private String name;

	/**
	 * 
	 * Construct.
	 * 
	 * @param name
	 */
	public MyPrincipal(String name)
	{
		super();
		this.name = name;
		if (name == null)
			throw new IllegalArgumentException("Name must be specified");
	}

	/**
	 * @see org.apache.wicket.security.hive.authorization.Principal#getName()
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @see org.apache.wicket.security.hive.authorization.Principal#implies(Subject)
	 */
	public boolean implies(Subject subject)
	{
		// no inheritance structure in these principals.
		return false;
	}

	/**
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return getClass().getName() + ": " + getName();
	}

	/**
	 * generated hash based on class and name.
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((name == null) ? 0 : name.hashCode());
		result = PRIME * result + getClass().hashCode();
		return result;
	}

	/**
	 * generated equals based on class and name.
	 * 
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
		final MyPrincipal other = (MyPrincipal) obj;
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
