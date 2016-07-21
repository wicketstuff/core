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
package org.wicketstuff.security.hive.config;

import java.io.IOException;
import java.io.ObjectInputStream;

import org.wicketstuff.security.actions.AbstractWaspAction;
import org.wicketstuff.security.actions.Access;
import org.wicketstuff.security.actions.ActionFactory;
import org.wicketstuff.security.actions.Actions;
import org.wicketstuff.security.actions.Render;
import org.wicketstuff.security.actions.WaspAction;

/**
 * {@link WaspAction} class based on int values to speed up inheritance checking. Each action is
 * assigned a power of 2 int value. Bitwise or checks provide the imply logic. These actions are
 * instantiated by an {@link ActionFactory} which provides both the name and the int value for the
 * constructor.
 * 
 * @author marrink
 */
public class TestAction extends AbstractWaspAction
{

	private final int actions;

	/**
	 * Key to get the {@link ActionFactory}.
	 */
	private final Object key;

	/**
	 * The default constructor for actions. Use it if your action does not inherit any other actions
	 * (other then {@link Access}), like {@link Render}.
	 * 
	 * @param action
	 *            a power of 2 which provides the base value for this action
	 * @param name
	 *            the name of the action
	 * @param key
	 *            the key used to register the owning {@link ActionFactory} with the {@link Actions}
	 *            object
	 */
	protected TestAction(int action, String name, Object key)
	{
		super(name);
		this.key = key;
		if (action < 0)
			throw new IllegalArgumentException(action + " must be >= 0");
		actions = action;
	}

	/**
	 * Check if the supplied actions are implied (bitwise or) by this SwarmAction.
	 * 
	 * @param otherActions
	 *            internal value of some other action(s)
	 * @return true if the actions are implied, false otherwise.
	 */
	public final boolean implies(int otherActions)
	{
		return (actions & otherActions) == otherActions;
	}

	/**
	 * Returns the internal representation of this action.
	 * 
	 * @return the internal value
	 */
	public final int actions()
	{
		return actions;
	}

	/**
	 * Any SwarmAction is equal to another if there 'actions' value is the same. In other words the
	 * name of an action is not important.
	 * 
	 * @see Object#equals(java.lang.Object)
	 * @see #actions()
	 */
	@Override
	public final boolean equals(Object obj)
	{
		if (obj instanceof TestAction)
		{
			TestAction other = (TestAction)obj;
			return other.actions() == actions;
		}
		return false;
	}

	/**
	 * @see Object#hashCode()
	 */
	@Override
	public final int hashCode()
	{
		int result = 4679;
		result = 37 * result + TestAction.class.hashCode();
		result = 37 * result + actions;
		return result;
	}

	/**
	 * Check if the supplied action is implied (bitwise or) by this SwarmAction.
	 * 
	 * @param other
	 *            the other action
	 * @return true if the action is implied, false otherwise.
	 */
	public final boolean implies(WaspAction other)
	{
		return other instanceof TestAction && implies(((TestAction)other).actions());
	}

	/**
	 * Creates a new {@link WaspAction} containing both the specified actions and the actions of
	 * this {@link WaspAction}. This method always returns a new SwarmAction.
	 * 
	 * @param otherActions
	 *            the actions to add
	 * @return a new WaspAction containing all the actions
	 */
	public final WaspAction add(int otherActions)
	{
		return newInstance(actions | otherActions);
	}

	/**
	 * Creates a new {@link WaspAction} containing both the specified actions and the actions of
	 * this {@link WaspAction}. This method always returns a new SwarmAction.
	 * 
	 * @param other
	 *            the other action(s)
	 * @return a new WaspAction containing all the actions
	 */
	public final TestAction add(WaspAction other)
	{
		if (other instanceof TestAction)
			return newInstance(actions | ((TestAction)other).actions());
		throw new IllegalArgumentException("other must be a SwarmAction");
	}

	/**
	 * Creates a new {@link WaspAction} with all the actions of this action except those specified.
	 * 
	 * @param otherActions
	 *            the actions to remove
	 * @return a new WaspAction or this action if the specified actions were never part of this
	 *         action.
	 */
	public final TestAction remove(int otherActions)
	{
		if (implies(otherActions))
			return newInstance(actions - otherActions);
		return this;
	}

	/**
	 * Creates a new WaspAction with all the actions of this action except those specified.
	 * 
	 * @param action
	 *            the actions to remove
	 * @return a new WaspAction or this action if the specified actions were never part of this
	 *         action.
	 */
	public final WaspAction remove(WaspAction action)
	{
		if (action instanceof TestAction)
			return remove(((TestAction)action).actions);
		throw new IllegalArgumentException("action must be a SwarmAction");
	}

	/**
	 * Creates a new action. This might actually return the same object depending on the
	 * implementation if the new SwarmAction does not have more actions then the current.
	 * 
	 * @param myActions
	 * @return
	 */
	private TestAction newInstance(int myActions)
	{
		return ((TestActionFactory)getActionFactory()).getAction(myActions);
	}

	/**
	 * 
	 * @see org.wicketstuff.security.actions.WaspAction#getActionFactory()
	 */
	public final ActionFactory getActionFactory()
	{
		return Actions.getActionFactory(key);
	}

	/**
	 * De-Serialization implementation.
	 * 
	 * @param stream
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private final void readObject(ObjectInputStream stream) throws IOException,
		ClassNotFoundException
	{
		stream.defaultReadObject();
	}
}
