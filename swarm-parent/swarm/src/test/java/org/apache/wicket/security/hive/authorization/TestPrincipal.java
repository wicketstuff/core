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
package org.apache.wicket.security.hive.authorization;

import org.apache.wicket.security.hive.authentication.Subject;

/**
 * principal for testing purposes.
 * 
 * @author marrink
 * 
 */
public class TestPrincipal implements Principal
{
	private static final long serialVersionUID = 1L;
	private String name;

	/**
	 * 
	 * Construct.
	 * 
	 * @param name
	 */
	public TestPrincipal(String name)
	{
		super();
		this.name = name;
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
		return false;
	}

	/**
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		return getClass().getName() + ": " + getName();
	}

	/**
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode()
	{
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	/**
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final TestPrincipal other = (TestPrincipal)obj;
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
