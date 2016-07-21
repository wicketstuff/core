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
package org.wicketstuff.datastores.hazelcast;

import org.apache.wicket.pageStore.IDataStore;
import org.apache.wicket.util.lang.Args;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

/**
 * An IDataStore that saves the pages' bytes in Hazelcast
 */
public class HazelcastDataStore implements IDataStore
{
	private static final Logger LOGGER = LoggerFactory.getLogger(HazelcastDataStore.class);

	/**
	 * The connection to the server
	 */
	private final HazelcastInstance hazelcast;

	/**
	 * Constructor.
	 *
	 * @param hazelcast     The hazelcast instance
	 */
	public HazelcastDataStore(HazelcastInstance hazelcast)
	{
		this.hazelcast = Args.notNull(hazelcast, "hazelcast");
	}

	@Override
	public byte[] getData(String sessionId, int pageId)
	{
		byte[] bytes = null;
		IMap<Integer, byte[]> map = hazelcast.getMap(sessionId);
		if (map != null) {
			bytes = map.get(pageId);
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Got {} for session '{}' and page id '{}'",
				new Object[] {bytes != null ? "data" : "'null'", sessionId, pageId});
		}
		return bytes;
	}

	@Override
	public void removeData(String sessionId, int pageId)
	{
		IMap<Integer, byte[]> map = hazelcast.getMap(sessionId);
		if (map != null) {
			byte[] removed = map.remove(pageId);

			if (removed != null) {
				LOGGER.debug("Deleted data for session '{}' and page with id '{}'", sessionId, pageId);
			}
		}
	}

	@Override
	public void removeData(String sessionId)
	{
		IMap<Integer, byte[]> map = hazelcast.getMap(sessionId);
		map.clear();
		LOGGER.debug("Deleted data for session '{}'", sessionId);
	}

	@Override
	public void storeData(String sessionId, int pageId, byte[] data)
	{
		IMap<Integer, byte[]> map = hazelcast.getMap(sessionId);
		map.put(pageId, data);
		LOGGER.debug("Inserted data for session '{}' and page id '{}'", sessionId, pageId);
	}

	@Override
	public void destroy()
	{
		if (hazelcast != null)
		{
			hazelcast.shutdown();
		}
	}

	@Override
	public boolean isReplicated()
	{
		return true;
	}

	@Override
	public boolean canBeAsynchronous()
	{
		return true;
	}
}
