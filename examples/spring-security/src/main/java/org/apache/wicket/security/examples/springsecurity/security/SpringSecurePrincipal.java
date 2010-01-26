package org.apache.wicket.security.examples.springsecurity.security;

import org.apache.wicket.security.hive.authentication.Subject;
import org.apache.wicket.security.hive.authorization.Principal;

/**
 * Simplest of principal.
 * 
 * @author marrink
 * @author Olger Warnier
 * 
 */
public class SpringSecurePrincipal implements Principal
{
	private static final long serialVersionUID = 1L;

	private String name;

	/**
	 * 
	 * Construct.
	 * 
	 * @param name
	 */
	public SpringSecurePrincipal(String name)
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
		final SpringSecurePrincipal other = (SpringSecurePrincipal) obj;
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
