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
package org.wicketstuff.security.actions;

import java.util.HashMap;
import java.util.Map;

/**
 * Actions maintains the collection of {@link ActionFactory}s. There can only be one Actions in each
 * virtual machine. Having a separate instance where all the ActionFactories are registered makes it
 * easier to use them outside wicket because there now is a single (non wicket) point of entry.
 * {@link ActionFactory}s should not be shared.
 * 
 * @author marrink
 */
public class Actions
{
	private static final Actions INSTANCE = new Actions();

	private Map<Object, ActionFactory> factoryCollection;

	/**
	 * Private constructor for this simple class.
	 */
	private Actions()
	{
		factoryCollection = new HashMap<Object, ActionFactory>();
	}

	/**
	 * Returns the ActionFactory stored with the key.
	 * 
	 * @param key
	 *            the key
	 * @return the factory or null if no factory is registered with that key
	 */
	public static ActionFactory getActionFactory(Object key)
	{
		return INSTANCE.factoryCollection.get(key);
	}

	/**
	 * Registers a new ActionFactory for anybody knowing the right key to be retrieved. It is not
	 * possible to overwrite a registration without first unregistering the previous factory.
	 * 
	 * @param key
	 *            the key to store the factory with.
	 * @param factory
	 *            the ActionFactory.
	 * @throws IllegalArgumentException
	 *             if the factory is null if an attempt is made to overwrite the registration.
	 * @see #unregisterActionFactory(Object)
	 */
	public static void registerActionFactory(Object key, ActionFactory factory)
	{
		if (factory == null)
			throw new IllegalArgumentException("Cannot register null ActionFactory.");
		synchronized (INSTANCE.factoryCollection)
		{
			if (INSTANCE.factoryCollection.containsKey(key))
				throw new IllegalArgumentException(
					"Another ActionFactory is already registered with the following key: " + key);
			INSTANCE.factoryCollection.put(key, factory);
		}
	}

	/**
	 * Removes the registration of a single ActionFactory.
	 * 
	 * @param key
	 *            the key the factory is registered with
	 * @return the registered factory or null if none was registered with the key.
	 */
	public static ActionFactory unregisterActionFactory(Object key)
	{
		return INSTANCE.factoryCollection.remove(key);
	}
}
