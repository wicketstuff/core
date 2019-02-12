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
package org.wicketstuff.datastores.memcached;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.page.IManageablePage;
import org.apache.wicket.pageStore.AbstractPersistentPageStore;
import org.apache.wicket.pageStore.IPersistedPage;
import org.apache.wicket.pageStore.IPersistentPageStore;
import org.apache.wicket.pageStore.SerializedPage;
import org.apache.wicket.util.lang.Args;
import org.apache.wicket.util.lang.Bytes;
import org.apache.wicket.util.lang.Checks;
import org.apache.wicket.util.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.spy.memcached.ConnectionObserver;
import net.spy.memcached.MemcachedClient;

/**
 * Page store that stores the pages' bytes in Memcached server.
 *
 * A useful read about the way Memcached works can be found
 * <a href="http://returnfoo.com/2012/02/memcached-memory-allocation-and-optimization-2/">here</a>.
 */
public class MemcachedDataStore extends AbstractPersistentPageStore implements IPersistentPageStore {

	private static final Logger LOG = LoggerFactory.getLogger(MemcachedDataStore.class);

	/**
	 * A suffix for the keys to avoid duplication of entries
	 * in Memcached entered by another process and to make
	 * it easier to find out who put the data at the server
	 */
	private static final String KEY_SUFFIX = "Wicket-Memcached";

	/**
	 * A separator used for the key construction
	 */
	private static final String SEPARATOR = "|||";
	
	private static final String SESSIONS = "sessions";

	private static final String SESSION_PAGES = "pages";

	private static final String PAGE_TYPE = "type";

	private static final String PAGE_SIZE = "size";

	private static final String PAGE_DATA = "data";

	/**
	 * The connection to the Memcached server
	 */
	private final MemcachedClient client;

	/**
	 * The configuration for the client
	 */
	private final IMemcachedSettings settings;

	/**
	 * Constructor.
	 *
	 * Creates a MemcachedClient from the provided settings
	 *
	 * @param settings The configuration for the client
	 * @throws java.io.IOException when cannot connect to any of the provided
	 *         in IMemcachedSettings Memcached servers
	 */
	public MemcachedDataStore(String applicationName, IMemcachedSettings settings) throws IOException {
		this(applicationName, createClient(settings), settings);
	}

	/**
	 * Constructor.
	 *
	 * @param client   The connection to Memcached
	 * @param settings The configuration for the client
	 */
	public MemcachedDataStore(String applicationName, MemcachedClient client, IMemcachedSettings settings) {
		super(applicationName);
		
		this.client = Args.notNull(client, "client");
		this.settings = Args.notNull(settings, "settings");

		client.addObserver(new ConnectionObserver()
		{
			@Override
			public void connectionEstablished(SocketAddress sa, int reconnectCount)
			{
				LOG.info("Established connection to: {}, reconnect count: {}", sa, reconnectCount);
			}

			@Override
			public void connectionLost(SocketAddress sa)
			{
				LOG.warn("Lost connection to: {}", sa);
			}
		});

	}

	/**
	 * Creates MemcachedClient with the provided hostname and port
	 * in the settings
	 *
	 * @param settings  The configuration for the client
	 * @return A MemcachedClient
	 * @throws java.io.IOException when cannot connect to any of the provided Memcached servers
	 */
	public static MemcachedClient createClient(IMemcachedSettings settings) throws IOException {
		Args.notNull(settings, "settings");

		int port = settings.getPort();

		Checks.withinRangeShort(10000, 65535, port, "port");

		List<String> hostnames = settings.getServerList();
		int memcachedPort = settings.getPort();
		InetSocketAddress[] addresses = new InetSocketAddress[hostnames.size()];
		for (int i = 0; i < hostnames.size(); i++) {
			String hostname = hostnames.get(i);
			addresses[i] = new InetSocketAddress(hostname, memcachedPort);
		}

		return new MemcachedClient(addresses);
	}

	@Override
	public boolean supportsVersioning() {
		return true;
	}
	
	@Override
	protected IManageablePage getPersistedPage(String sessionIdentifier, int id) {
		byte[] data = (byte[]) client.get(makeKey(sessionIdentifier, id, PAGE_DATA));
		
		if (data != null) {
			String type = (String) client.get(makeKey(sessionIdentifier, id, PAGE_TYPE));
			
			if (LOG.isDebugEnabled()) {
				LOG.debug("Got page for session '{}' and page id '{}'", sessionIdentifier, id);
			}
			
			return new SerializedPage(id, type, data);
		}
		
		return null;
	}

	@Override
	protected void removePersistedPage(String sessionIdentifier, IManageablePage page) {
		new MemcachedSet(client, makeKey(sessionIdentifier, SESSION_PAGES), settings.getExpirationTime()).remove(String.valueOf(page.getPageId()));

		client.delete(makeKey(sessionIdentifier, page.getPageId(), PAGE_DATA));
		client.delete(makeKey(sessionIdentifier, page.getPageId(), PAGE_TYPE));
		client.delete(makeKey(sessionIdentifier, page.getPageId(), PAGE_SIZE));

		LOG.debug("Removed the data for session '{}' and page id '{}'", sessionIdentifier, page.getPageId());
	}

	@Override
	protected void removeAllPersistedPages(String sessionIdentifier) {
		MemcachedSet pages = new MemcachedSet(client, makeKey(sessionIdentifier, SESSION_PAGES), settings.getExpirationTime());

		for (String id : pages) {
			client.delete(makeKey(sessionIdentifier, id, PAGE_DATA));
			client.delete(makeKey(sessionIdentifier, id, PAGE_TYPE));
			client.delete(makeKey(sessionIdentifier, id, PAGE_SIZE));
		}
		
		client.delete(makeKey(sessionIdentifier, SESSION_PAGES));

		new MemcachedSet(client, makeKey(SESSIONS), settings.getExpirationTime()).remove(sessionIdentifier);
	}

	@Override
	protected void addPersistedPage(String sessionIdentifier, IManageablePage page) {
		if (page instanceof SerializedPage == false)
		{
			throw new WicketRuntimeException("MemcachedDataStore works with serialized pages only");
		}
		SerializedPage serializedPage = (SerializedPage)page;
		
		MemcachedSet pages = new MemcachedSet(client, makeKey(sessionIdentifier, SESSION_PAGES), settings.getExpirationTime());
		if (pages.add(String.valueOf(page.getPageId()))) {
			MemcachedSet sessions = new MemcachedSet(client, makeKey(SESSIONS), settings.getExpirationTime());
			sessions.add(sessionIdentifier);
			sessions.compact();
		}

		int expirationTime = settings.getExpirationTime();
		client.set(makeKey(sessionIdentifier, serializedPage.getPageId(), PAGE_DATA), expirationTime, serializedPage.getData());
		client.set(makeKey(sessionIdentifier, serializedPage.getPageId(), PAGE_SIZE), expirationTime, serializedPage.getData().length);
		if (serializedPage.getPageType() != null) {
			client.set(makeKey(sessionIdentifier, serializedPage.getPageId(), PAGE_TYPE), expirationTime, serializedPage.getPageType());
		}

		// now try to compact
		pages.compact(pageId -> client.get(makeKey(sessionIdentifier, pageId, PAGE_SIZE)) != null);

		LOG.debug("Stored data for session '{}' and page id '{}'", sessionIdentifier, page.getPageId());
	}

	@Override
	public void destroy() {
		if (client != null) {
			Duration timeout = settings.getShutdownTimeout();
			LOG.info("Shutting down gracefully for {}", timeout);
			client.shutdown(timeout.getMilliseconds(), TimeUnit.MILLISECONDS);
		}
	}

	/**
	 * Creates a key that is used for the lookup in Memcached.
	 *
	 * @param sessionId The id of the http session.
	 * @param pageId    The id of the stored page
	 * @return A key that is used for the lookup in Memcached
	 */
	private String makeKey(Object... segments) {
		StringBuilder key = new StringBuilder();
		
		for (Object segment : segments) {
			if (key.length() > 0) {
				key.append(SEPARATOR);
			}
			key.append(segment);
		}
		
		key.append(SEPARATOR);
		key.append(KEY_SUFFIX);
				
		return key.toString();
	}
	
	@Override
	public Set<String> getSessionIdentifiers() {
		return MemcachedSet.decodeSet((String)client.get(makeKey(SESSIONS)));
	}
	
	@Override
	public List<IPersistedPage> getPersistedPages(String sessionIdentifier) {
		List<IPersistedPage> pages = new ArrayList<>();
		
		for (String id : MemcachedSet.decodeSet((String)client.get(makeKey(sessionIdentifier, SESSION_PAGES)))) {
			Integer pageSize = (Integer)client.get(makeKey(sessionIdentifier, id, PAGE_SIZE));
			if (pageSize != null) {
				String pageType = (String)client.get(makeKey(sessionIdentifier, id, PAGE_TYPE));
				
				pages.add(new PersistedPage(Integer.parseInt(id), pageType, pageSize));
			}
		}
		
		return pages;
	}

	/**
	 * Not supported.
	 */
	@Override
	public Bytes getTotalSize() {
		return null;
	}
}