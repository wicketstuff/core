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
package org.wicketstuff.security;

import java.util.HashSet;
import java.util.Set;

import org.apache.wicket.Application;
import org.wicketstuff.security.actions.AbstractWaspAction;
import org.wicketstuff.security.actions.ActionFactory;
import org.wicketstuff.security.actions.WaspAction;

/**
 * A test implementation for class based actions.
 * 
 * @author marrink
 */
public class StringAction extends AbstractWaspAction
{

	private final Set<String> actions;

	StringAction(String name)
	{
		super(name);
		actions = new HashSet<String>();
		String[] names = name.split(" ");
		for (String name2 : names)
		{
			actions.add(name2.replace(',', ' ').trim());
		}
	}

	/**
	 * 
	 * @see org.wicketstuff.security.actions.WaspAction#add(org.wicketstuff.security.actions.WaspAction)
	 */
	public WaspAction add(WaspAction other)
	{
		return new StringAction(getName() + " " + other.getName());
	}

	/**
	 * 
	 * @see org.wicketstuff.security.actions.WaspAction#implies(org.wicketstuff.security.actions.WaspAction)
	 */
	public boolean implies(WaspAction other)
	{
		StringAction oAction = (StringAction)other;
		return actions.containsAll(oAction.actions);
	}

	/**
	 * 
	 * @see org.wicketstuff.security.actions.WaspAction#remove(org.wicketstuff.security.actions.WaspAction)
	 */
	public WaspAction remove(WaspAction other)
	{
		StringAction oAction = (StringAction)other;
		StringAction newAction = new StringAction(getName());
		newAction.actions.removeAll(oAction.actions);
		return newAction;
	}

	public ActionFactory getActionFactory()
	{
		// not a very good implementation since it binds this action type to
		// wicket
		return ((WaspApplication)Application.get()).getActionFactory();
		// real world implementations should use the Actions class or some other
		// non wicket specific way
	}
}