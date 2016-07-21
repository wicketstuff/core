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
package org.wicketstuff.security.hive.authorization.permissions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.wicket.Component;
import org.wicketstuff.security.actions.Inherit;
import org.wicketstuff.security.actions.WaspAction;
import org.wicketstuff.security.components.SecureComponentHelper;
import org.wicketstuff.security.hive.authorization.Permission;

/**
 * Permission for certain components. Can have actions like access, render or enable.
 * 
 * @author marrink
 */
public class ComponentPermission extends ActionPermission
{

	private static final long serialVersionUID = 8950870313751454034L;

	private final List<String[]> parents;

	private final String[] path;

	/**
	 * Creates a new ComponentPermission with the specified actions.
	 * 
	 * @param component
	 *            the component
	 * @param action
	 *            the action(s)
	 */
	public ComponentPermission(Component component, WaspAction action)
	{
		super(SecureComponentHelper.alias(component), action);
		path = getName().split(SecureComponentHelper.PATH_SEPARATOR);
		String[] aliasses = SecureComponentHelper.containerAliasses(component);
		if (aliasses != null && aliasses.length > 0)
		{
			parents = new ArrayList<String[]>(aliasses.length);
			for (int i = 0; i < aliasses.length; i++)
				parents.add(aliasses[i].split(SecureComponentHelper.PATH_SEPARATOR));
		}
		else
			parents = Collections.emptyList();
	}

	/**
	 * Creates a new ComponentPermission with the specified actions.
	 * 
	 * @param componentAlias
	 *            an alias as produced by {@link SecureComponentHelper}
	 * @param actions
	 *            the granted action(s)
	 */
	public ComponentPermission(String componentAlias, WaspAction actions)
	{
		super(componentAlias, actions);
		path = getName().split(SecureComponentHelper.PATH_SEPARATOR);
		parents = Collections.emptyList();
	}

	/**
	 * Overrides {@link ActionPermission#implies(Permission)} to also include inheritance between
	 * several levels of parent containers. The same rules still apply
	 * 
	 * @see Permission#implies(Permission)
	 */
	@Override
	public boolean implies(Permission permission)
	{
		if (permission instanceof ComponentPermission)
		{
			ComponentPermission other = (ComponentPermission)permission;
			if (getAction().implies(getAction().getActionFactory().getAction(Inherit.class)))
				return getAction().implies(other.getAction()) &&
					(implies(other.path, path) || impliesHierarchy(other));
			return getAction().implies(other.getAction()) && equals(other.path, path);
		}
		return false;
	}

	private boolean impliesHierarchy(ComponentPermission other)
	{
		if (other == null)
			return false;
		for (int i = 0; i < other.parents.size(); i++)
		{
			if (implies(path, other.parents.get(i)))
				return true;

		}
		return false;
	}

	protected boolean equals(String[] path1, String[] path2)
	{
		if (path1.length != path2.length)
			return false;
		for (int count = 0; count < path1.length; count++)
		{
			if (!path1[count].equals(path2[count]))
				return false;
		}
		return true;
	}

	protected boolean implies(String[] path1, String[] path2)
	{
		int i = 0;
		int j = 0;
		for (; i < path1.length && j < path2.length; j++)
		{
			if (path1[i].equals(path2[j]))
			{
				i++;
			}
			else if (i > 0)
				return false;
		}
		return path1.length == i;
		// TODO wildcards would be nice:
		// e.g org.MyPage:*:SomeComponent * fits just one parts
		// org.MyPage:**:SomeComponent ** fits one or more parts so
		// Panel1:Panel2 would be valid and we could grant permissions for
		// components at an unknown depth
		// ? could be used to fit a single char wildcard
	}
}
