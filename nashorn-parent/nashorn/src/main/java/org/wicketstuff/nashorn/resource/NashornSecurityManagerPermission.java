package org.wicketstuff.nashorn.resource;

import java.security.Permission;

/**
 * Permissions used by the corresponding security manager
 * 
 * @author Tobias Soloschenko
 */
public class NashornSecurityManagerPermission extends Permission
{

	private static final long serialVersionUID = 4812713037565136922L;

	private static final String NAME = "NashornSecurityManagerPermission";

	/**
	 * Creates new permissions
	 */
	public NashornSecurityManagerPermission()
	{
		super(NAME);
	}

	/**
	 * Used to check if a permission implies this one
	 * 
	 * @see #equals(Object)
	 */
	@Override
	public boolean implies(Permission permission)
	{
		return this.equals(permission);
	}

	/**
	 * If the given object are the permissions itself they are the same
	 */
	@Override
	public boolean equals(Object obj)
	{
		return obj instanceof NashornSecurityManagerPermission;
	}

	/**
	 * To make this fast return the hashcode of the name
	 */
	@Override
	public int hashCode()
	{
		return NAME.hashCode();
	}

	/**
	 * No action required
	 */
	@Override
	public String getActions()
	{
		return "check";
	}

}