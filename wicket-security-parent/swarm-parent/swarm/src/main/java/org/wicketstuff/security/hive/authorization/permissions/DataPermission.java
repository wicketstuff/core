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

import org.apache.wicket.Component;
import org.wicketstuff.security.actions.WaspAction;
import org.wicketstuff.security.hive.authorization.Permission;
import org.wicketstuff.security.swarm.models.SwarmModel;

/**
 * A permission for data or plain old pojo's. Can have actions like access, render or enable.
 * 
 * @author marrink
 * 
 */
public class DataPermission extends ActionPermission
{
	private static final long serialVersionUID = 5192668688933417376L;

	/**
	 * Creates a new DataPermission for a components model. The model will specify the name for this
	 * permission. Currently we don't check if the component really has the model you specified
	 * here.
	 * 
	 * @param component
	 *            component containing the model
	 * @param model
	 *            the model of the component
	 * @param actions
	 *            a logical and of all the allowed / required actions
	 */
	public DataPermission(Component component, SwarmModel<?> model, WaspAction actions)
	{
		super(model.getSecurityId(component), actions);
	}

	/**
	 * Creates a new DataPermission with the specified name and actions.
	 * 
	 * @param name
	 * @param actions
	 */
	public DataPermission(String name, WaspAction actions)
	{
		super(name, actions);
	}

	/**
	 * @see Permission#implies(Permission)
	 */
	@Override
	public boolean implies(Permission permission)
	{
		return (permission instanceof DataPermission) && super.implies(permission);
	}

	/**
	 * 
	 * @see org.wicketstuff.security.hive.authorization.permissions.ActionPermission#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof DataPermission)
			return super.equals(obj);
		return false;
	}

	/**
	 * 
	 * @see org.wicketstuff.security.hive.authorization.permissions.ActionPermission#hashCode()
	 */
	@Override
	public int hashCode()
	{
		// super implementation already gives out distinct hashcodes per
		// subclass
		return super.hashCode();
	}
}
