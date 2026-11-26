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
package org.wicketstuff.datastores.redis;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.UUID;

import org.apache.wicket.pageStore.IPageStore;
import org.apache.wicket.pageStore.SerializedPage;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.wicketstuff.datastores.common.BaseDataStoreTest;

import redis.clients.jedis.DefaultJedisClientConfig;
import redis.clients.jedis.RedisClient;

/**
 * Requires a running Redis server.
 */
@Tag("redis")
public class RedisDataStoreTest extends BaseDataStoreTest {

	private static final byte[] DATA = { 1, 2, 3 };

	private final IRedisSettings settings = new RedisSettings();

	@Override
	protected IPageStore createDataStore() throws Exception {
		return new RedisDataStore("test", settings);
	}

	@Test
	void usesProvidedClient() {
		String session = UUID.randomUUID().toString();
		String dataKey = "Wicket-Redis|||" + session + "|||1";
		RedisDataStore store = new RedisDataStore("test-provided-client", client(1), settings);
		try (RedisClient database0 = client(0); RedisClient database1 = client(1)) {
			store.addPersistedPage(session, new SerializedPage(1, "type", DATA));

			assertTrue(database1.exists(dataKey));
			assertFalse(database0.exists(dataKey));
			SerializedPage page = (SerializedPage) store.getPersistedPage(session, 1);
			assertArrayEquals(DATA, page.getData());
		} finally {
			store.removeAllPersistedPages(session);
			store.destroy();
		}
	}

	@Test
	void destroyClosesProvidedClient() {
		RedisClient redis = client(0);
		RedisDataStore store = new RedisDataStore("test-close-client", redis, settings);

		store.destroy();

		assertThrows(RuntimeException.class, redis::ping);
	}

	@Test
	void storeCanBeRecreatedAfterDestroy() {
		new RedisDataStore("test-recreate", settings).destroy();

		new RedisDataStore("test-recreate", settings).destroy();
	}

	private RedisClient client(int database) {
		return RedisClient.builder()
				.hostAndPort(settings.getHostname(), settings.getPort())
				.clientConfig(DefaultJedisClientConfig.builder().database(database).build())
				.build();
	}
}
