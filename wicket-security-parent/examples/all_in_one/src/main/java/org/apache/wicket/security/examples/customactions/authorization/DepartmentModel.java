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
package org.apache.wicket.security.examples.customactions.authorization;

import org.apache.wicket.Component;
import org.apache.wicket.security.actions.WaspAction;
import org.apache.wicket.security.examples.customactions.entities.Department;
import org.apache.wicket.security.swarm.models.SwarmCompoundPropertyModel;

/**
 * A model for departments. It automatically handles security for all components using
 * this model.
 * 
 * @author marrink
 */
public class DepartmentModel extends SwarmCompoundPropertyModel<Department>
{
	private static final long serialVersionUID = 1L;

	/**
	 * Construct.
	 * 
	 * @param object
	 */
	public DepartmentModel(Object object)
	{
		super(object);
	}

	/**
	 * @see org.apache.wicket.security.swarm.models.SwarmModel#getSecurityId(org.apache.wicket.Component)
	 */
	public String getSecurityId(Component component)
	{
		return "department";
	}

	/**
	 * @see org.apache.wicket.security.models.ISecureModel#isAuthorized(org.apache.wicket.Component,
	 *      org.apache.wicket.security.actions.WaspAction)
	 */
	@Override
	public boolean isAuthorized(Component component, WaspAction action)
	{
		// the department entity not to be confused with the department action
		// further down.
		Department department = getObject();
		// for secure departments you need organization rights, else
		// department rights are sufficient
		WaspAction myAction =
			action
				.add(getActionFactory()
					.getAction(
						department.secure
							? Organization.class
							: org.apache.wicket.security.examples.customactions.authorization.Department.class));
		return super.isAuthorized(component, myAction);
	}

}
