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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.page.IManageablePage;
import org.apache.wicket.pageStore.AbstractPersistentPageStore;
import org.apache.wicket.pageStore.IPersistedPage;
import org.apache.wicket.pageStore.IPersistentPageStore;
import org.apache.wicket.pageStore.SerializedPage;
import org.apache.wicket.util.lang.Args;
import org.apache.wicket.util.lang.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hazelcast.config.SerializerConfig;
import com.hazelcast.core.DistributedObject;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.StreamSerializer;

/**
 * An IDataStore that saves the pages' bytes in Hazelcast
 */
public class HazelcastDataStore extends AbstractPersistentPageStore implements IPersistentPageStore {
	
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
	public HazelcastDataStore(String applicationName, HazelcastInstance hazelcast)
	{
		super(applicationName);
		
		this.hazelcast = Args.notNull(hazelcast, "hazelcast");
		
		SerializerConfig config = new SerializerConfig().
		        setImplementation(new SerializedPageSerializer()).
		        setTypeClass(SerializedPage.class);
		hazelcast.getConfig().getSerializationConfig().addSerializerConfig(config);
	}

	/**
	 * Pages are always serialized, so versioning is supported.
	 */
	@Override
	public boolean supportsVersioning()
	{
		return true;
	}

	@Override
	protected IManageablePage getPersistedPage(String sessionIdentifier, int id) {
		IMap<Integer, SerializedPage> map = hazelcast.getMap(sessionIdentifier);
		if (map != null)
		{
			SerializedPage page = map.get(id);
			
			if (LOGGER.isDebugEnabled())
			{
				LOGGER.debug("Got {} for session '{}' and page id '{}'", page, sessionIdentifier, id);
			}
			
			if (page != null) {
				return page;
			}
		}
		
		return null;
	}

	@Override
	protected void removePersistedPage(String sessionIdentifier, IManageablePage page) {
		IMap<Integer, SerializedPage> map = hazelcast.getMap(sessionIdentifier);
		if (map != null)
		{
			map.delete(page.getPageId());
			LOGGER.debug("Deleted page for session '{}' and page with id '{}'", sessionIdentifier, page.getPageId());
		}
	}

	@Override
	protected void removeAllPersistedPages(String identifier) {
		IMap<Integer, SerializedPage> map = hazelcast.getMap(identifier);
		if (map != null)
		{
			map.clear();
			LOGGER.debug("Deleted page for session '{}'", identifier);
		}
	}

	@Override
	protected void addPersistedPage(String identifier, IManageablePage page) {
		if (page instanceof SerializedPage == false)
		{
			throw new WicketRuntimeException("CassandraDataStore works with serialized pages only");
		}
		SerializedPage serializedPage = (SerializedPage)page;

		IMap<Integer, SerializedPage> map = hazelcast.getMap(identifier);
		map.put(page.getPageId(), serializedPage);
		LOGGER.debug("Inserted page for session '{}' and page id '{}'", identifier, page.getPageId());
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
	public Set<String> getSessionIdentifiers()
	{
		return hazelcast.getConfig().getMapConfigs().keySet();
	}
	
	@Override
	public Bytes getTotalSize()
	{
		long bytes = 0;
		for (DistributedObject object : hazelcast.getDistributedObjects())
		{
			IMap<Integer, SerializedPage> map = hazelcast.getMap(object.getName());
			
			bytes += map.getLocalMapStats().getHeapCost();
		}
		return Bytes.bytes(bytes);
	}
	
	
	
	@Override
	public List<IPersistedPage> getPersistedPages(String contextIdentifier)
	{
		List<IPersistedPage> pages = new ArrayList<>();
		
		IMap<Integer, SerializedPage> map = hazelcast.getMap(contextIdentifier);
		if (map != null) {
			for (SerializedPage page : map.values()) {
				pages.add(new PersistedPage(page.getPageId(), page.getPageType(), page.getData().length));
			}
		}
		
		return pages;
	}
	
	private static final class SerializedPageSerializer implements StreamSerializer<SerializedPage>
	{

		@Override
		public void write(ObjectDataOutput out, SerializedPage page) throws IOException {
			out.writeInt(page.getPageId());
			out.writeUTF(page.getPageType());
			out.write(page.getData());
		}

		@Override
		public SerializedPage read(ObjectDataInput in) throws IOException {
			return new SerializedPage(in.readInt(), in.readUTF(), in.readByteArray());
		}

		@Override
		public int getTypeId() {
			return 42;
		}

		@Override
		public void destroy() {
		}
	}
}