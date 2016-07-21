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
package org.apache.wicket.security.examples.customactions.entities;

import java.io.Serializable;

/**
 * The department entity. it has a boolean secure which is used by the security checks to
 * decide if
 * {@link org.apache.wicket.security.examples.customactions.authorization.Department} or
 * {@link org.apache.wicket.security.examples.customactions.authorization.Organization}
 * rights are required.
 * 
 * @author marrink
 */
public class Department implements Serializable
{
	private static final long serialVersionUID = 1L;

	/**
	 * the organization of this department.
	 */
	public Organization organization;

	/**
	 * The name.
	 */
	public String name;

	/**
	 * A description.
	 */
	public String description;

	/**
	 * indicates if this department is only visible to users with organization clearance.
	 */
	public final boolean secure;

	/**
	 * Construct.
	 */
	public Department()
	{
		secure = false;
	}

	/**
	 * Construct.
	 * 
	 * @param organization
	 * @param name
	 * @param description
	 * @param secure
	 * 
	 */
	public Department(Organization organization, String name, String description, boolean secure)
	{
		super();
		this.organization = organization;
		this.name = name;
		this.description = description;
		this.secure = secure;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((name == null) ? 0 : name.hashCode());
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
		final Department other = (Department) obj;
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
