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
package org.apache.wicket.security.swarm.actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.wicket.authorization.Action;
import org.apache.wicket.security.actions.Access;
import org.apache.wicket.security.actions.ActionFactory;
import org.apache.wicket.security.actions.Actions;
import org.apache.wicket.security.actions.AllActions;
import org.apache.wicket.security.actions.Enable;
import org.apache.wicket.security.actions.Inherit;
import org.apache.wicket.security.actions.RegistrationException;
import org.apache.wicket.security.actions.Render;
import org.apache.wicket.security.actions.WaspAction;
import org.apache.wicket.security.actions.WaspActionFactory;
import org.apache.wicket.util.string.AppendingStringBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Default implementation of an action factory. It handles access, inherit,
 * render and enable actions. Because actions are immutable and in order to
 * improve performance, generated actions are cached.
 * 
 * @author marrink
 */
public class SwarmActionFactory implements WaspActionFactory
{
	private static final Logger log = LoggerFactory.getLogger(SwarmActionFactory.class);
	/**
	 * Maximum power of 2 that can be used to assign to an action.
	 */
	protected static final int maxAssingableAction = (int)Math.pow(2, 30);

	/**
	 * maps int's to Strings.
	 */
	private Map stringValues = new HashMap(10);

	/**
	 * cache that maps int's to actions and Strings to actions
	 */
	private Map cachedActions = new HashMap();
	/**
	 * Maps int's to actions. and classes to actions.
	 */
	private Map registeredActions = new HashMap();

	private int power = -1;
	private int maxAction = 0;
	private final Object factoryKey;

	/**
	 * Registers the default actions: access, inherit, render and enable.
	 * 
	 * @param key
	 *            using this key the factory registers itself to the
	 *            {@link Actions} object.
	 */
	public SwarmActionFactory(Object key)
	{
		super();
		this.factoryKey = key;
		Actions.registerActionFactory(key, this);
		try
		{
			register(Access.class, "access");
			register(Inherit.class, "inherit");
			register(Render.class, "render");
			register(Enable.class, new ImpliesOtherAction("enable", this, Render.class));
		}
		catch (RegistrationException e)
		{
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * 
	 * @see org.apache.wicket.security.actions.WaspActionFactory#getAction(org.apache.wicket.authorization.Action)
	 */
	public WaspAction getAction(Action action)
	{
		if (action != null)
			try
			{
				return getAction(action.getName());
			}
			catch (IllegalArgumentException e)
			{
				// according to the spec we return null if the action does not
				// exist
			}
		return null;
	}

	/**
	 * @param actions
	 *            empty string means Access
	 * @see ActionFactory#getAction(String)
	 */
	public WaspAction getAction(String actions)
	{
		String saveActions = convertWicket2Wasp(actions);
		SwarmAction sa = getCachedAction(saveActions);
		if (sa == null)
		{
			int actionValues = parseActions(saveActions);
			// rebuild action name
			String nameValues = buildActionString(actionValues);
			sa = new SwarmAction(actionValues, nameValues, getFactoryKey());
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
	protected synchronized final void cacheAction(String name, SwarmAction action)
	{
		cachedActions.put(name, action);
	}

	/**
	 * Returns a cached action.
	 * 
	 * @param name
	 * @return the cached action or null.
	 */
	protected synchronized final SwarmAction getCachedAction(String name)
	{
		return (SwarmAction)cachedActions.get(name);
	}

	/**
	 * Returns an action based on its int value.
	 * 
	 * @param actions
	 * @return the action
	 * @throws IllegalArgumentException
	 *             if no action can be formed based on the input
	 */
	public SwarmAction getAction(int actions)
	{
		SwarmAction ja = getCachedAction(actions);
		if (ja == null)
		{
			if (actions > maxAction)
				throw new IllegalArgumentException("Max value for actions = " + maxAction
						+ ", you used " + actions);
			if (actions < 0)
				throw new IllegalArgumentException("Min value for actions = 0, you used " + actions);
			ja = new SwarmAction(actions, buildActionString(actions), getFactoryKey());
			cacheAction(new Integer(actions), ja);
		}
		return ja;

	}

	/**
	 * Caches an action under its int form.
	 * 
	 * @param actions
	 * @param ja
	 */
	protected synchronized final void cacheAction(Integer actions, SwarmAction ja)
	{
		cachedActions.put(actions, ja);
	}

	/**
	 * Returns a cached action.
	 * 
	 * @param actions
	 * @return the cached action or null.
	 */
	protected synchronized final SwarmAction getCachedAction(int actions)
	{
		return (SwarmAction)cachedActions.get(new Integer(actions));
	}

	/**
	 * Returns the registered string value of the given action.
	 * 
	 * @param action
	 *            the internal value of the action
	 * @return the registered string or null if no string value was registered
	 *         for this action.
	 */
	protected final String valueOf(Integer action)
	{
		return (String)stringValues.get(action);
	}

	/**
	 * Builds a logically ordered comma separated string of all the actions this
	 * permission has. Based on the logical and of the supplied actions.
	 * Subclasses should always return the same string (action order) for the
	 * same action.
	 * 
	 * @param actions
	 *            the internal action value
	 * @return string containing all the actions.
	 * 
	 */
	protected String buildActionString(int actions)
	{
		AppendingStringBuffer buff = new AppendingStringBuffer(power > 0 ? 10 * power : 10);
		// estimate 10 chars per name
		for (int i = -1; i < power; i++)
		{
			appendActionString(buff, actions, ((SwarmAction)registeredActions.get(new Integer(i)))
					.actions());
		}
		if (buff.length() > 0) // should always be the case
			buff.delete(buff.length() - 2, buff.length());
		return buff.toString();
	}

	/**
	 * Appends the string value of the action only if the actions imply the
	 * waspAction
	 * 
	 * @param buff
	 *            where the string will be appended to.
	 * @param actions
	 *            the available actions
	 * @param waspAction
	 *            the action it should imply in order to append the string
	 */
	protected final void appendActionString(AppendingStringBuffer buff, int actions, int waspAction)
	{
		if (implies(actions, waspAction))
			buff.append(valueOf(new Integer(waspAction))).append(", ");
	}

	/**
	 * Check if the action is available in the actions. This performs a bitwise
	 * check.
	 * 
	 * @param actions
	 *            the actions that might contain action
	 * @param action
	 *            the action we check for in actions
	 * @return true if actions contains action
	 */
	protected final boolean implies(int actions, int action)
	{
		return ((actions & action) == action);
	}

	/**
	 * Parses a comma separated String containing actions. Access is the default
	 * and will also be substituted for any empty or null String. using a string
	 * like 'render, render' is pointless but does not brake anything. Order of
	 * the actions is also not important.
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
			Set keys = stringValues.keySet();
			for (int i = 0; i < actionz.length; i++)
			{
				action = actionz[i].trim();
				if (action.equals(""))
					break; // Access
				if (action.equals("all"))
					return ((SwarmAction)getAction(AllActions.class)).actions();
				boolean found = false;
				Iterator it = keys.iterator();
				Integer key;
				while (it.hasNext() && !found)
				{
					key = (Integer)it.next();
					if (action.equalsIgnoreCase(valueOf(key)))
					{
						sum = sum | key.intValue();
						found = true;
					}
				}
				if (!found)
					throw new IllegalArgumentException("Invalid action: " + action + " in: "
							+ actions);
			}
		}
		return sum;
	}

	/**
	 * 
	 * @see org.apache.wicket.security.actions.ActionFactory#getAction(java.lang.Class)
	 */
	public synchronized WaspAction getAction(Class waspActionClass)
	{
		if (AllActions.class.isAssignableFrom(waspActionClass))
		{
			WaspAction all = (WaspAction)registeredActions.get(Access.class);
			Iterator it = registeredActions.keySet().iterator();
			Object action = null;
			while (it.hasNext())
			{
				action = it.next();
				if (action instanceof Class)
					all = all.add((WaspAction)registeredActions.get(action));
			}
			return all;
		}
		WaspAction action = (WaspAction)registeredActions.get(waspActionClass);
		if (action == null)
			throw new IllegalArgumentException("" + waspActionClass + " is not registered");
		return action;

	}

	/**
	 * 
	 * @see org.apache.wicket.security.actions.ActionFactory#register(java.lang.Class,
	 *      java.lang.String)
	 */
	public synchronized WaspAction register(Class waspActionClass, String name)
			throws RegistrationException
	{
		if (AllActions.class.isAssignableFrom(waspActionClass))
			throw new RegistrationException("Can not register 'all' actions");
		WaspAction temp = (WaspAction)registeredActions.get(waspActionClass);
		if (temp != null)
			return temp;
		if (WaspAction.class.isAssignableFrom(waspActionClass))
		{
			if (power > 30)
				throw new RegistrationException("Can not register more then 32 different actions.");
			// 32 since we start at 0 :)
			int action = nextPowerOf2();
			return register(waspActionClass, new SwarmAction(action, name, getFactoryKey()));
		}
		throw new RegistrationException(waspActionClass + " is not a " + WaspAction.class.getName());
	}

	/**
	 * Returns the number of registered classes. By default there are 4 classes
	 * registered.
	 * 
	 * @return the number of registered classes.
	 */
	public final int getNumberOfRegisteredClasses()
	{
		return power + 1;
	}

	/**
	 * The next action value. Note that you can only register classes while this
	 * is < {@link Integer#MAX_VALUE}
	 * 
	 * @return the int value of 2^(numberOfRegisteredClasses()-1)
	 */
	protected final int nextPowerOf2()
	{
		return (int)Math.pow(2, power);
	}

	/**
	 * Renames build in wicket actions to there wasp counterpart. more
	 * specifically it converts the string to lowercase.
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
	 * {@link SwarmAction#SwarmAction(int, String, ActionFactory)}. Example:<br>
	 * 
	 * <pre><code>
	 * register(Enable.class, new ImpliesReadAction(nextPowerOf2(), &quot;enable&quot;, this));
	 * class ImpliesReadAction extends SwarmAction
	 * {
	 * 	public ImpliesReadAction(int actions, String name, ActionFactory factory)
	 * 	{
	 * 		super(actions
	 * 				| ((SwarmAction)factory.getAction(org.apache.wicket.security.actions.Render.class))
	 * 						.actions(), name);
	 * 	}
	 * }
	 * </code>
	 * </pre>
	 * 
	 * Note all actions registered in this way must use nextPowerOf2() and then
	 * immediately register the action to preserve consistency.
	 * 
	 * @param waspActionClass
	 *            the class under which to register the action
	 * @param action
	 *            the actual implementation (note that it does not need to
	 *            implement the supplied waspActionClass)
	 * @return the action
	 * @throws RegistrationException
	 *             if the action can not be registered.
	 * @see #nextPowerOf2()
	 * @see SwarmAction#SwarmAction(int, String, ActionFactory)
	 * 
	 */
	protected final synchronized SwarmAction register(Class waspActionClass, SwarmAction action)
			throws RegistrationException
	{
		// sanity checks
		if (AllActions.class.isAssignableFrom(waspActionClass))
			throw new RegistrationException("Can not register 'all' actions");
		if (power > 30)
			throw new RegistrationException("Can not register more then 32 different actions.");
		int assignedPowerOf2 = nextPowerOf2();
		if (assignedPowerOf2 > maxAssingableAction)
			throw new RegistrationException(
					"Unable to register an action with a base value greater then "
							+ maxAssingableAction);
		if (assignedPowerOf2 < 0)
			throw new RegistrationException(assignedPowerOf2 + " is not a positive value");
		// includes any implied actions
		Integer powerOf2 = new Integer(action.actions());
		if (!implies(powerOf2.intValue(), assignedPowerOf2))
			throw new RegistrationException("Unable to register action '" + action.getName()
					+ "' with value " + powerOf2 + " expected " + assignedPowerOf2 + " or more.");
		// end of checks

		stringValues.put(powerOf2, action.getName());
		registeredActions.put(new Integer(power), action);
		registeredActions.put(waspActionClass, action);
		maxAction += assignedPowerOf2;
		power++;
		return action;
	}

	/**
	 * 
	 * @see org.apache.wicket.security.actions.ActionFactory#getRegisteredActions()
	 */
	public List getRegisteredActions()
	{
		List actions = new ArrayList(getNumberOfRegisteredClasses());
		Iterator it = registeredActions.keySet().iterator();
		Object action = null;
		while (it.hasNext())
		{
			action = it.next();
			if (action instanceof Class)
				actions.add(getAction((Class)action));
		}
		return actions;
	}

	/**
	 * Clears registration and cached values. After you destroy this factory you
	 * must not use it again.
	 * 
	 * @see org.apache.wicket.security.actions.ActionFactory#destroy()
	 */
	public void destroy()
	{
		power = 31; // prevents new registrations
		maxAction = 0; // prevents lookups
		// protected against multiple destroy calls
		if (registeredActions != null)
			registeredActions.clear();
		if (cachedActions != null)
			cachedActions.clear();
		if (stringValues != null)
			stringValues.clear();
		registeredActions = null;
		cachedActions = null;
		stringValues = null;
		Actions.unregisterActionFactory(getFactoryKey());
	}

	/**
	 * Any class that implies another action.
	 * 
	 * @author marrink
	 */
	protected static class ImpliesOtherAction extends SwarmAction
	{
		private static final long serialVersionUID = 1L;

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
		 * @deprecated replaced by
		 *             {@link #ImpliesOtherAction(String, SwarmActionFactory,Class)}
		 */
		public ImpliesOtherAction(int actions, String name, Object key, ActionFactory factory,
				Class otherAction)
		{
			super(actions | ((SwarmAction)factory.getAction(otherAction)).actions(), name, key);
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
		 * @deprecated replaced by
		 *             {@link #ImpliesOtherAction(String, SwarmActionFactory,Class[])}
		 */
		public ImpliesOtherAction(int actions, String name, Object key, ActionFactory factory,
				Class[] otherActions)
		{
			super(actions | bitwiseOr(factory, otherActions), name, key);
		}

		/**
		 * the base action value
		 * 
		 * @param name
		 *            name of the new action
		 * @param factory
		 *            factory where this class will be registered
		 * @param otherAction
		 *            a single action class to imply, not null
		 */
		public ImpliesOtherAction(String name, SwarmActionFactory factory, Class otherAction)
		{
			super(factory.nextPowerOf2() | ((SwarmAction)factory.getAction(otherAction)).actions(),
					name, factory.getFactoryKey());
		}

		/**
		 * the base action value
		 * 
		 * @param name
		 *            name of the new action
		 * @param factory
		 *            factory where this class will be registered
		 * @param otherActions
		 *            any number of action classes to imply
		 */
		public ImpliesOtherAction(String name, SwarmActionFactory factory, Class[] otherActions)
		{
			super(factory.nextPowerOf2() | bitwiseOr(factory, otherActions), name, factory
					.getFactoryKey());
		}

		/**
		 * Creates a bitwise or of all the actions supplied. Note that all these
		 * classes must already be registered.
		 * 
		 * @param factory
		 * @param otherActions
		 *            any number of action classes to imply
		 * @return
		 */
		private static final int bitwiseOr(ActionFactory factory, Class[] otherActions)
		{
			int result = 0;
			if (otherActions != null)
			{
				Class action;
				for (int i = 0; i < otherActions.length; i++)
				{
					action = otherActions[i];
					result = result | ((SwarmAction)factory.getAction(action)).actions();
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
