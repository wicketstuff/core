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
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import net.spy.memcached.ConnectionObserver;
import net.spy.memcached.MemcachedClient;

import org.apache.wicket.pageStore.IDataStore;
import org.apache.wicket.util.lang.Args;
import org.apache.wicket.util.lang.Checks;
import org.apache.wicket.util.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * {@link org.apache.wicket.pageStore.IDataStore} that stores the pages' bytes in Memcached
 *
 * A useful read about the way Memcached works can be found
 * <a href="http://returnfoo.com/2012/02/memcached-memory-allocation-and-optimization-2/">here</a>.
 */
public class MemcachedDataStore implements IDataStore
{
	private static final Logger LOG = LoggerFactory.getLogger(MemcachedDataStore.class);

	/**
	 * The connection to the Memcached server
	 */
	private final MemcachedClient client;

	/**
	 * The configuration for the client
	 */
	private final IMemcachedSettings settings;

	private final ISessionResourcesManager sessionResourcesManager = new SessionResourcesManager();

	/**
	 * Constructor.
	 *
	 * Creates a MemcachedClient from the provided settings
	 *
	 * @param settings The configuration for the client
	 * @throws java.io.IOException when cannot connect to any of the provided
	 *         in IMemcachedSettings Memcached servers
	 */
	public MemcachedDataStore(IMemcachedSettings settings) throws IOException
	{
		this(createClient(settings), settings);
	}

	/**
	 * Constructor.
	 *
	 * @param client   The connection to Memcached
	 * @param settings The configuration for the client
	 */
	public MemcachedDataStore(MemcachedClient client, IMemcachedSettings settings) {
		this.client = Args.notNull(client, "client");
		this.settings = Args.notNull(settings, "settings");

		client.addObserver(new ConnectionObserver() {
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
	public byte[] getData(String sessionId, int pageId) {
		String key = sessionResourcesManager.getKey(sessionId, pageId);
		byte[] bytes = (byte[]) client.get(key);

		if (bytes == null) {
			sessionResourcesManager.removeKey(sessionId, key);
		}

		LOG.debug("Got {} for session '{}' and page id '{}'",
				bytes != null ? "data" : "'null'", sessionId, pageId);
		return bytes;
	}

	@Override
	public void removeData(final String sessionId, final int pageId) {
		final String key = sessionResourcesManager.getKey(sessionId, pageId);
		client.delete(key);

		sessionResourcesManager.removeKey(sessionId, key);

		LOG.debug("Removed the data for session '{}' and page id '{}'", sessionId, pageId);
	}

	@Override
	public void removeData(String sessionId) {
		Set<String> keys =sessionResourcesManager.removeData(sessionId);
		if (keys != null) {
			for (String key : keys) {
				client.delete(key);
			}
			LOG.debug("Removed the data for session '{}'", sessionId);
		}
	}

	@Override
	public void storeData(final String sessionId, final int pageId, byte[] data) {
		final String key = sessionResourcesManager.getKey(sessionId, pageId);
		sessionResourcesManager.storeData(sessionId, pageId, data, key);
		
		Duration expirationTime = settings.getExpirationTime();

		client.set(key, (int) expirationTime.seconds(), data);
		
		LOG.debug("Stored data for session '{}' and page id '{}'", sessionId, pageId);
	}

	@Override
	public void destroy() {
		sessionResourcesManager.destroy();
		if (client != null) {
			Duration timeout = settings.getShutdownTimeout();
			LOG.info("Shutting down gracefully for {}", timeout);
			client.shutdown(timeout.getMilliseconds(), TimeUnit.MILLISECONDS);
		}
	}

	@Override
	public boolean isReplicated() {
		return true;
	}

	@Override
	public boolean canBeAsynchronous() {
		// no need to be asynchronous
		// MemcachedClient is asynchronous itself
		return false;
	}
}
