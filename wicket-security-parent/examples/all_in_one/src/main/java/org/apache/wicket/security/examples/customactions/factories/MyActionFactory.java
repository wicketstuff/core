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
package org.apache.wicket.security.examples.customactions.factories;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.security.actions.RegistrationException;
import org.apache.wicket.security.examples.customactions.authorization.Department;
import org.apache.wicket.security.examples.customactions.authorization.Organization;
import org.apache.wicket.security.swarm.actions.SwarmAction;
import org.apache.wicket.security.swarm.actions.SwarmActionFactory;

/**
 * Custom action factory. Registers {@link Department} and {@link Organization} actions.
 * 
 * @author marrink
 */
public class MyActionFactory extends SwarmActionFactory
{

	/**
	 * Constructs a new ActionFactory with in addition to the default actions,
	 * organisation and department actions.
	 * 
	 * @param key
	 *            key to store this factory
	 */
	public MyActionFactory(Object key)
	{
		super(key);
		try
		{
			// note none of the actions registered this way will implement the
			// interface defined here, you will simply get the default action.
			// that's just the way swarm implements wasp
			register(Department.class, "department");
			// registering an action this way will return the actual
			// implementation specified here
			// however the reason we are using a custom implementation here is
			// because we need to inherit the department action not because we
			// want our actions to be a certain subclass.
			register(Organization.class, new DepartmentInheritor(nextPowerOf2(), "organization",
				this));

		}
		catch (RegistrationException e)
		{
			throw new WicketRuntimeException("actionfactory was not setup correctly", e);
		}
	}

	/**
	 * Custom class for all actions implying the department action.
	 * 
	 * @author marrink
	 */
	private static final class DepartmentInheritor extends SwarmAction
	{
		private static final long serialVersionUID = 1L;

		/**
		 * 
		 * Construct.
		 * 
		 * @param actions
		 *            power of two, to be used as base value for the action
		 * @param name
		 *            action name
		 * @param factory
		 *            the factory
		 */
		protected DepartmentInheritor(int actions, String name, SwarmActionFactory factory)
		{
			// bitwise or to inherit department action
			super(actions | factory.getAction(Department.class).actions(), name, factory);
		}
	}
}
