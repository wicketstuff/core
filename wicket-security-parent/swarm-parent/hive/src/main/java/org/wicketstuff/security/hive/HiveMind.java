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
package org.wicketstuff.security.hive;

import java.util.HashMap;
import java.util.Map;

import org.wicketstuff.security.hive.config.HiveFactory;

/**
 * HiveMind maintains the collection of {@link Hive}s. There can only be one HiveMind in each
 * virtual machine. As the HiveMind is supposed to be created and configured early during the
 * lifetime of an application none of its methods are synchronized.
 * 
 * @author marrink
 */
public class HiveMind
{
	// TODO JAAS check for rights on all methods
	private static final HiveMind INSTANCE = new HiveMind();

	private Map<Object, Hive> hiveCollection;

	/**
	 * Private constructor for this simple class.
	 */
	private HiveMind()
	{
		hiveCollection = new HashMap<Object, Hive>();
	}

	/**
	 * Returns the Hive stored with the key.
	 * 
	 * @param queen
	 *            the key
	 * @return the Hive or null if no Hive is registered with that key
	 */
	public static Hive getHive(Object queen)
	{
		return INSTANCE.hiveCollection.get(queen);
	}

	/**
	 * Registers a new Hive for anybody knowing the right key to be retrieved. It is not possible to
	 * overwrite a registration without first unregistering the previous Hive.
	 * 
	 * @param queen
	 *            the key to store the Hive with.
	 * @param factory
	 *            the factory that will produce the Hive.
	 * @throws IllegalArgumentException
	 *             if the factory is null
	 * @throws IllegalArgumentException
	 *             if an attempt is made to overwrite the registration.
	 * @throws RuntimeException
	 *             if the factory does not produce a Hive.
	 * @see #unregisterHive(Object)
	 */
	public static void registerHive(Object queen, HiveFactory factory)
	{
		if (factory == null)
			throw new IllegalArgumentException("Cannot register Hive without a factory.");
		synchronized (INSTANCE.hiveCollection)
		{
			if (INSTANCE.hiveCollection.containsKey(queen))
				throw new IllegalArgumentException(
					"Another Hive is already registered with the following key: " + queen);
			Hive hive = factory.createHive();
			if (hive == null)
				throw new RuntimeException("Factory did not produce a Hive.");
			INSTANCE.hiveCollection.put(queen, hive);
		}
	}

	/**
	 * Removes the registration of a single Hive.
	 * 
	 * @param queen
	 *            the key the Hive is registered with
	 * @return the registered Hive or null if none was registered with the key.
	 */
	public static Hive unregisterHive(Object queen)
	{
		return INSTANCE.hiveCollection.remove(queen);
	}
}
