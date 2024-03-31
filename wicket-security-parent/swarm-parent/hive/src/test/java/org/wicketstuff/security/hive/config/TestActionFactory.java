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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.security.actions.Access;
import org.wicketstuff.security.actions.ActionFactory;
import org.wicketstuff.security.actions.Actions;
import org.wicketstuff.security.actions.AllActions;
import org.wicketstuff.security.actions.Enable;
import org.wicketstuff.security.actions.Inherit;
import org.wicketstuff.security.actions.RegistrationException;
import org.wicketstuff.security.actions.Render;
import org.wicketstuff.security.actions.WaspAction;

/**
 * Default implementation of an action factory. It handles access, inherit, render and enable
 * actions. Because actions are immutable and in order to improve performance, generated actions are
 * cached.
 * 
 * @author marrink
 */
public class TestActionFactory implements ActionFactory
{
	private static final Logger log = LoggerFactory.getLogger(TestActionFactory.class);

	/**
	 * Maximum power of 2 that can be used to assign to an action.
	 */
	protected static final int maxAssingableAction = (int)Math.pow(2, 30);

	/**
	 * maps int's to Strings.
	 */
	private Map<Integer, String> stringValues = new HashMap<Integer, String>(10);

	/**
	 * cache that maps int's to actions and Strings to actions
	 */
	private Map<String, TestAction> cachedStringActions = new HashMap<String, TestAction>();

	private Map<Integer, TestAction> cachedIntActions = new HashMap<Integer, TestAction>();

	/**
	 * Maps int's to actions. and classes to actions.
	 */
	private Map<Class<? extends WaspAction>, TestAction> registeredClassActions = new HashMap<Class<? extends WaspAction>, TestAction>();

	private Map<Integer, TestAction> registeredIntActions = new HashMap<Integer, TestAction>();

	private int power = -1;

	private int maxAction = 0;

	private final Object factoryKey;

	/**
	 * Registers the default actions: access, inherit, render and enable.
	 * 
	 * @param key
	 *            using this key the factory registers itself to the {@link Actions} object.
	 */
	public TestActionFactory(Object key)
	{
		super();
		factoryKey = key;
		Actions.registerActionFactory(key, this);
		try
		{
			register(Access.class, "access");
			register(Inherit.class, "inherit");
			register(Render.class, "render");
			register(Enable.class, new ImpliesOtherAction(nextPowerOf2(), "enable", key, this,
				Render.class));
		}
		catch (RegistrationException e)
		{
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * 
	 * @see org.wicketstuff.security.actions.ActionFactory#getAction(org.apache.wicket.authorization.Action)
	 */
	// public WaspAction getAction(Action action)
	// {
	// if (action != null)
	// try
	// {
	// return getAction(action.getName());
	// }
	// catch (IllegalArgumentException e)
	// {
	// // according to the spec we return null if the action does not
	// // exist
	// }
	// return null;
	// }
	/**
	 * @param actions
	 *            empty string means Access
	 * @see ActionFactory#getAction(String)
	 */
	public WaspAction getAction(String actions)
	{
		String saveActions = convertWicket2Wasp(actions);
		TestAction sa = getCachedAction(saveActions);
		if (sa == null)
		{
			int actionValues = parseActions(saveActions);
			// rebuild action name
			String nameValues = buildActionString(actionValues);
			sa = new TestAction(actionValues, nameValues, getFactoryKey());
			cacheAction(saveActions, sa);
		}
		return sa;
	}

	/**
	 * Caches an action under its string form.
	 * 
	 * @param name
	 * @param action
	 */
	protected synchronized final void cacheAction(String name, TestAction action)
	{
		cachedStringActions.put(name, action);
	}

	/**
	 * Returns a cached action.
	 * 
	 * @param name
	 * @return the cached action or null.
	 */
	protected synchronized final TestAction getCachedAction(String name)
	{
		return cachedStringActions.get(name);
	}

	/**
	 * Returns an action based on its int value.
	 * 
	 * @param actions
	 * @return the action
	 * @throws IllegalArgumentException
	 *             if no action can be formed based on the input
	 */
	public TestAction getAction(int actions)
	{
		TestAction ja = getCachedAction(actions);
		if (ja == null)
		{
			if (actions > maxAction)
				throw new IllegalArgumentException("Max value for actions = " + maxAction +
					", you used " + actions);
			if (actions < 0)
				throw new IllegalArgumentException("Min value for actions = 0, you used " + actions);
			ja = new TestAction(actions, buildActionString(actions), getFactoryKey());
			cacheAction(Integer.valueOf(actions), ja);
		}
		return ja;

	}

	/**
	 * Caches an action under its int form.
	 * 
	 * @param actions
	 * @param ja
	 */
	protected synchronized final void cacheAction(Integer actions, TestAction ja)
	{
		cachedIntActions.put(actions, ja);
	}

	/**
	 * Returns a cached action.
	 * 
	 * @param actions
	 * @return the cached action or null.
	 */
	protected synchronized final TestAction getCachedAction(int actions)
	{
		return cachedIntActions.get(actions);
	}

	/**
	 * Returns the registered string value of the given action.
	 * 
	 * @param action
	 *            the internal value of the action
	 * @return the registered string or null if no string value was registered for this action.
	 */
	protected final String valueOf(Integer action)
	{
		return stringValues.get(action);
	}

	/**
	 * Builds a logically ordered comma separated string of all the actions this permission has.
	 * Based on the logical and of the supplied actions. Subclasses should always return the same
	 * string (action order) for the same action.
	 * 
	 * @param actions
	 *            the internal action value
	 * @return string containing all the actions.
	 * 
	 */
	protected String buildActionString(int actions)
	{
		StringBuffer buff = new StringBuffer(power > 0 ? 10 * power : 10);
		// estimate 10 chars per name
		for (int i = -1; i < power; i++)
		{
			appendActionString(buff, actions, registeredIntActions.get(i).actions());
		}
		if (buff.length() > 0) // should always be the case
			buff.delete(buff.length() - 2, buff.length());
		return buff.toString();
	}

	/**
	 * Appends the string value of the action only if the actions imply the waspAction
	 * 
	 * @param buff
	 *            where the string will be appended to.
	 * @param actions
	 *            the available actions
	 * @param waspAction
	 *            the action it should imply in order to append the string
	 */
	protected final void appendActionString(StringBuffer buff, int actions, int waspAction)
	{
		if (implies(actions, waspAction))
			buff.append(valueOf(Integer.valueOf(waspAction))).append(", ");
	}

	/**
	 * Check if the action is available in the actions. This performs a bitwise check.
	 * 
	 * @param actions
	 *            the actions that might contain action
	 * @param action
	 *            the action we check for in actions
	 * @return true if actions contains action
	 */
	protected final boolean implies(int actions, int action)
	{
		return (actions & action) == action;
	}

	/**
	 * Parses a comma separated String containing actions. Access is the default and will also be
	 * substituted for any empty or null String. using a string like 'render, render' is pointless
	 * but does not brake anything. Order of the actions is also not important.
	 * 
	 * @param actions
	 * @return a logical and of the actions.
	 * @throws IllegalArgumentException
	 *             if (one of) the action(s) is not recognized.
	 */
	protected int parseActions(String actions)
	{
		int sum = 0; // no actions or empty actions equal access
		if (actions != null)
		{
			String[] actionz = actions.split(",");
			String action = null;
			Set<Integer> keys = stringValues.keySet();
			for (String element : actionz)
			{
				action = element.trim();
				if (action.equals(""))
					break; // Access
				if (action.equals("all"))
					return getAction(AllActions.class).actions();
				boolean found = false;
				Iterator<Integer> it = keys.iterator();
				Integer key;
				while (it.hasNext() && !found)
				{
					key = it.next();
					if (action.equalsIgnoreCase(valueOf(key)))
					{
						sum = sum | key.intValue();
						found = true;
					}
				}
				if (!found)
					throw new IllegalArgumentException("Invalid action: " + action + " in: " +
						actions);
			}
		}
		return sum;
	}

	/**
	 * 
	 * @see org.wicketstuff.security.actions.ActionFactory#getAction(java.lang.Class)
	 */
	public synchronized TestAction getAction(Class<? extends WaspAction> waspActionClass)
	{
		if (AllActions.class.isAssignableFrom(waspActionClass))
		{
			TestAction all = registeredClassActions.get(Access.class);
			for (Class<? extends WaspAction> action : registeredClassActions.keySet())
			{
				all = all.add(registeredClassActions.get(action));
			}
			return all;
		}
		TestAction action = registeredClassActions.get(waspActionClass);
		if (action == null)
			throw new IllegalArgumentException("" + waspActionClass + " is not registered");
		return action;

	}

	/**
	 * 
	 * @see org.wicketstuff.security.actions.ActionFactory#register(java.lang.Class,
	 *      java.lang.String)
	 */
	public synchronized TestAction register(Class<? extends WaspAction> waspActionClass, String name)
		throws RegistrationException
	{
		if (AllActions.class.isAssignableFrom(waspActionClass))
			throw new RegistrationException("Can not register 'all' actions");
		TestAction temp = registeredClassActions.get(waspActionClass);
		if (temp != null)
			return temp;
		if (WaspAction.class.isAssignableFrom(waspActionClass))
		{
			if (power > 30)
				throw new RegistrationException("Can not register more then 32 different actions.");
			// 32 since we start at 0 :)
			int action = nextPowerOf2();
			return register(waspActionClass, new TestAction(action, name, getFactoryKey()));
		}
		throw new RegistrationException(waspActionClass + " is not a " + WaspAction.class.getName());
	}

	/**
	 * Returns the number of registered classes. By default there are 4 classes registered.
	 * 
	 * @return the number of registered classes.
	 */
	public final int getNumberOfRegisteredClasses()
	{
		return power + 1;
	}

	/**
	 * The next action value. Note that you can only register classes while this is <
	 * {@link Integer#MAX_VALUE}
	 * 
	 * @return the int value of 2^(numberOfRegisteredClasses()-1)
	 */
	protected final int nextPowerOf2()
	{
		return (int)Math.pow(2, power);
	}

	/**
	 * Renames build in wicket actions to there wasp counterpart. more specifically it converts the
	 * string to lowercase.
	 * 
	 * @param name
	 *            the name of the action
	 * @return the converted name of the action.
	 */
	protected final String convertWicket2Wasp(String name)
	{
		if (name == null)
			return "";
		return name.toLowerCase();
	}

	/**
	 * Registers a new action. Use this in combination with
	 * {@link TestAction#TestAction(int, String, ActionFactory)}. Example:<br>
	 * 
	 * <pre>
	 * <code>
	 * register(Enable.class, new ImpliesReadAction(nextPowerOf2(), &quot;enable&quot;, this));
	 * class ImpliesReadAction extends SwarmAction
	 * {
	 * 	public ImpliesReadAction(int actions, String name, ActionFactory factory)
	 * 	{
	 * 		super(actions
	 * 				| ((SwarmAction)factory.getAction(org.wicketstuff.security.actions.Render.class))
	 * 						.actions(), name);
	 * 	}
	 * }
	 * </code>
	 * </pre>
	 * 
	 * Note all actions registered in this way must use nextPowerOf2() and then immediately register
	 * the action to preserve consistency.
	 * 
	 * @param waspActionClass
	 *            the class under which to register the action
	 * @param action
	 *            the actual implementation (note that it does not need to implement the supplied
	 *            waspActionClass)
	 * @return the action
	 * @throws RegistrationException
	 *             if the action can not be registered.
	 * @see #nextPowerOf2()
	 * @see TestAction#TestAction(int, String, ActionFactory)
	 * 
	 */
	protected final synchronized TestAction register(Class<? extends WaspAction> waspActionClass,
		TestAction action) throws RegistrationException
	{
		// sanity checks
		if (AllActions.class.isAssignableFrom(waspActionClass))
			throw new RegistrationException("Can not register 'all' actions");
		if (power > 30)
			throw new RegistrationException("Can not register more then 32 different actions.");
		int assignedPowerOf2 = nextPowerOf2();
		if (assignedPowerOf2 > maxAssingableAction)
			throw new RegistrationException(
				"Unable to register an action with a base value greater then " +
					maxAssingableAction);
		if (assignedPowerOf2 < 0)
			throw new RegistrationException(assignedPowerOf2 + " is not a positive value");
		// includes any implied actions
		Integer powerOf2 = Integer.valueOf(action.actions());
		if (!implies(powerOf2.intValue(), assignedPowerOf2))
			throw new RegistrationException("Unable to register action '" + action.getName() +
				"' with value " + powerOf2 + " expected " + assignedPowerOf2 + " or more.");
		// end of checks

		stringValues.put(powerOf2, action.getName());
		registeredIntActions.put(Integer.valueOf(power), action);
		registeredClassActions.put(waspActionClass, action);
		maxAction += assignedPowerOf2;
		power++;
		return action;
	}

	/**
	 * 
	 * @see org.wicketstuff.security.actions.ActionFactory#getRegisteredActions()
	 */
	public List<WaspAction> getRegisteredActions()
	{
		List<WaspAction> actions = new ArrayList<WaspAction>(getNumberOfRegisteredClasses());
		for (Class<? extends WaspAction> action : registeredClassActions.keySet())
		{
			actions.add(getAction(action));
		}
		return actions;
	}

	/**
	 * Clears registration and cached values. After you destroy this factory you must not use it
	 * again.
	 * 
	 * @see org.wicketstuff.security.actions.ActionFactory#destroy()
	 */
	public void destroy()
	{
		power = 31; // prevents new registrations
		maxAction = 0; // prevents lookups
		registeredClassActions.clear();
		registeredIntActions.clear();
		cachedStringActions.clear();
		cachedIntActions.clear();
		stringValues.clear();
		registeredClassActions = null;
		registeredIntActions = null;
		cachedStringActions = null;
		cachedIntActions = null;
		stringValues = null;
		Actions.unregisterActionFactory(getFactoryKey());
	}

	/**
	 * Any class that implies another action.
	 * 
	 * @author marrink
	 */
	protected static class ImpliesOtherAction extends TestAction
	{
		/**
		 * @param actions
		 *            the base action value
		 * @param name
		 *            name of the new action
		 * @param key
		 *            key to get the registered ActionFactory
		 * @param factory
		 *            factory where this class will be registered
		 * @param otherAction
		 *            a single action class to imply, not null
		 */
		public ImpliesOtherAction(int actions, String name, Object key, TestActionFactory factory,
			Class<? extends WaspAction> otherAction)
		{
			super(actions | factory.getAction(otherAction).actions(), name, key);
		}

		/**
		 * @param actions
		 *            the base action value
		 * @param name
		 *            name of the new action
		 * @param key
		 *            key to get the registered ActionFactory
		 * @param factory
		 *            factory where this class will be registered
		 * @param otherActions
		 *            any number of action classes to imply
		 */
		public ImpliesOtherAction(int actions, String name, Object key, ActionFactory factory,
			Class<? extends WaspAction>[] otherActions)
		{
			super(actions | bitwiseOr(factory, otherActions), name, key);
		}

		/**
		 * Creates a bitwise or of all the actions supplied. Note that all these classes must
		 * already be registered.
		 * 
		 * @param factory
		 * @param otherActions
		 *            any number of action classes to imply
		 * @return
		 */
		private static final int bitwiseOr(ActionFactory factory,
			Class<? extends WaspAction>[] otherActions)
		{
			int result = 0;
			if (otherActions != null)
			{
				Class<? extends WaspAction> action;
				for (Class<? extends WaspAction> otherAction : otherActions)
				{
					action = otherAction;
					result = result | ((TestAction)factory.getAction(action)).actions();
				}
			}
			return result;
		}
	}

	/**
	 * Gets key.
	 * 
	 * @return key
	 */
	protected final Object getFactoryKey()
	{
		return factoryKey;
	}
}
