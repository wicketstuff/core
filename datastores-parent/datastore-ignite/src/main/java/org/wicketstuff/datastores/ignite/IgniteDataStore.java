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
package org.wicketstuff.datastores.ignite;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.ignite.DataRegionMetrics;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteException;
import org.apache.ignite.binary.BinaryObjectException;
import org.apache.ignite.binary.BinaryReader;
import org.apache.ignite.binary.BinaryWriter;
import org.apache.ignite.binary.Binarylizable;
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

/**
 * An IDataStore implementation that saves serialized pages in Apache Ignite
 * <p>
 * Ignite is not compatible with Java 9 - the following JVM parameters must be used:
 * <pre>
 * --add-exports=java.base/jdk.internal.misc=ALL-UNNAMED
 * --add-exports=java.base/sun.nio.ch=ALL-UNNAMED 
 * </pre>
 * 
 * @see https://stackoverflow.com/questions/50639471/using-ignite-on-jdk-9
 * 
 * @author Alexey Prudnikov
 */
public class IgniteDataStore extends AbstractPersistentPageStore  implements IPersistentPageStore {
	private static final Logger LOGGER = LoggerFactory.getLogger(IgniteDataStore.class);

	/**
	 * Apache Ignite instance
	 */
	private final Ignite ignite;

	/**
	 * Constructor
	 *
	 * @param ignite The Apache Ignite instance
	 */
	public IgniteDataStore(String applicatioName, Ignite ignite) {
		super(applicatioName);

		this.ignite = Args.notNull(ignite, "ignite");
	}

	/**
	 * Returns (and creates if needed) named cache from Ignite instance
	 *
	 * @param cacheName Cache name
	 */
	private IgniteCache<Integer, BinarylizableWrapper> getIgniteCache(String cacheName, boolean create) {
		if (create) {
			return ignite.getOrCreateCache(cacheName);
		} else {
			return ignite.cache(cacheName);
		}
	}

	@Override
	protected IManageablePage getPersistedPage(String sessionIdentifier, int id) {
		IgniteCache<Integer, BinarylizableWrapper> cache = getIgniteCache(sessionIdentifier, false);
		if (cache != null) {
			BinarylizableWrapper wrapper = cache.get(id);

			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Got {} for session '{}' and page id '{}'", wrapper, sessionIdentifier, id);
			}

			if (wrapper != null) {
				return wrapper.page;
			}
		}

		return null;
	}

	@Override
	protected void removePersistedPage(String sessionIdentifier, IManageablePage page) {
		IgniteCache<Integer, BinarylizableWrapper> cache = getIgniteCache(sessionIdentifier, false);
		if (cache != null) {
			cache.remove(page.getPageId());
			LOGGER.debug("Deleted page for session '{}' and page with id '{}'", sessionIdentifier,
					page.getPageId());
		}
	}

	@Override
	protected void removeAllPersistedPages(String identifier) {
		IgniteCache<Integer, BinarylizableWrapper> cache = getIgniteCache(identifier, false);
		if (cache != null) {
			cache.clear();
			LOGGER.debug("Deleted page for session '{}'", identifier);
		}
	}

	@Override
	protected void addPersistedPage(String identifier, IManageablePage page) {
		if (page instanceof SerializedPage == false) {
			throw new WicketRuntimeException("CassandraDataStore works with serialized pages only");
		}
		SerializedPage serializedPage = (SerializedPage) page;

		IgniteCache<Integer, BinarylizableWrapper> cache = getIgniteCache(identifier, true);
		cache.put(page.getPageId(), new BinarylizableWrapper(serializedPage));
		LOGGER.debug("Inserted page for session '{}' and page id '{}'", identifier, page.getPageId());
	}

	@Override
	public void destroy() {
		if (ignite != null) {
			try {
				ignite.close();
			} catch (IgniteException e) {
				LOGGER.error("Can't close ignite instance", e);
			}
		}
	}

	@Override
	public boolean supportsVersioning() {
		return true;
	}

	@Override
	public Set<String> getSessionIdentifiers() {
		return new HashSet<>(ignite.cacheNames());
	}

	@Override
	public List<IPersistedPage> getPersistedPages(String contextIdentifier) {
		List<IPersistedPage> pages = new ArrayList<>();

		IgniteCache<Integer, BinarylizableWrapper> cache = getIgniteCache(contextIdentifier, false);
		if (cache != null) {
			cache.forEach(entry -> {
				SerializedPage serializedPage = entry.getValue().page;
				pages.add(new PersistedPage(serializedPage.getPageId(), serializedPage.getPageType(),
						serializedPage.getData().length));
			});
		}

		return pages;
	}

	@Override
	public Bytes getTotalSize() {
		long bytes = 0;

		for (DataRegionMetrics metrics : ignite.dataRegionMetrics()) {
			bytes += metrics.getTotalAllocatedSize();
		}
		
		return Bytes.bytes(bytes);
	}

	/**
	 * A wrapper around a {@link SerializedPage} that implements Ignite's
	 * {@link Binarylizable} for performance.
	 * 
	 * @author sven
	 */
	private static final class BinarylizableWrapper implements Binarylizable {

		private static final String DATA = "data";
		private static final String TYPE = "type";
		private static final String ID = "id";
		public SerializedPage page;

		public BinarylizableWrapper(SerializedPage page) {
			this.page = page;
		}

		@Override
		public String toString() {
			return page.toString();
		}

		@Override
		public void writeBinary(BinaryWriter writer) throws BinaryObjectException {
			writer.writeInt(ID, page.getPageId());
			writer.writeString(TYPE, page.getPageType());
			writer.writeByteArray(DATA, page.getData());
		}

		@Override
		public void readBinary(BinaryReader reader) throws BinaryObjectException {
			page = new SerializedPage(reader.readInt(ID), reader.readString(TYPE), reader.readByteArray(DATA));
		}
	}
}
