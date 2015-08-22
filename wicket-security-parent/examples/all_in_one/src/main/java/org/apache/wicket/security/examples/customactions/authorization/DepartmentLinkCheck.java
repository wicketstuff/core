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

import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.security.actions.WaspAction;
import org.apache.wicket.security.checks.LinkSecurityCheck;
import org.apache.wicket.security.examples.customactions.entities.Department;

/**
 * A check for {@link Department}s (not to be confused with the
 * {@link org.apache.wicket.security.examples.customactions.authorization.Department}
 * action which is used to do that. there are two type of departments, secure (which you
 * can only see/edit when you have organization rights and unsecure departments where
 * department rights are sufficient.
 * 
 * @author marrink
 */
public class DepartmentLinkCheck extends LinkSecurityCheck
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private boolean secureDepartment;

	/**
	 * Construct.
	 * 
	 * @param component
	 * @param clickTarget
	 * @param department
	 *            the department to check
	 */
	public DepartmentLinkCheck(AbstractLink component, Class< ? > clickTarget, Department department)
	{
		super(component, clickTarget);
		secureDepartment = department.secure;
	}

	/**
	 * Construct.
	 * 
	 * @param component
	 * @param clickTarget
	 * @param department
	 *            the department to check
	 * @param checkSecureModelIfExists
	 */
	public DepartmentLinkCheck(AbstractLink component, Class< ? > clickTarget,
			Department department, boolean checkSecureModelIfExists)
	{
		super(component, clickTarget, checkSecureModelIfExists);
		secureDepartment = department.secure;
	}

	/**
	 * @see org.apache.wicket.security.checks.LinkSecurityCheck#isActionAuthorized(org.apache.wicket.security.actions.WaspAction)
	 */
	@Override
	public boolean isActionAuthorized(WaspAction action)
	{
		// for secure departments you need organization rights, else department
		// rights are sufficient
		WaspAction myAction =
			action
				.add(getActionFactory()
					.getAction(
						secureDepartment
							? Organization.class
							: org.apache.wicket.security.examples.customactions.authorization.Department.class));
		return super.isActionAuthorized(myAction);
	}

}
