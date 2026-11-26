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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;
import java.util.UUID;

import org.apache.wicket.page.IManageablePage;
import org.apache.wicket.pageStore.IPageStore;
import org.apache.wicket.pageStore.IPersistedPage;
import org.apache.wicket.pageStore.SerializedPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.wicketstuff.datastores.common.BaseDataStoreTest;

import redis.clients.jedis.RedisClient;

/**
 * Requires a running Redis server.
 */
@Tag("redis")
public class RedisDataStoreTest extends BaseDataStoreTest {

	private static final byte[] DATA = { 1, 2, 3 };

	private RedisDataStore store;
	private RedisClient redis;
	private String session;

	@Override
	protected IPageStore createDataStore() throws Exception {
		IRedisSettings settings = new RedisSettings();
		return new RedisDataStore("test", settings);
	}

	@BeforeEach
	void setUp() {
		IRedisSettings settings = new RedisSettings();
		// own application name: the inherited load() test registers "test" while this store exists
		store = new RedisDataStore("test-missing-pages", settings);
		redis = RedisClient.builder()
				.hostAndPort(settings.getHostname(), settings.getPort())
				.build();
		session = UUID.randomUUID().toString();
	}

	@AfterEach
	void tearDown() {
		store.removeAllPersistedPages(session);
		store.destroy();
		redis.close();
	}

	@Test
	void getPersistedPageReturnsStoredPage() {
		store.addPersistedPage(session, new SerializedPage(1, "type", DATA));

		IManageablePage page = store.getPersistedPage(session, 1);

		SerializedPage serializedPage = (SerializedPage) page;
		assertEquals(1, serializedPage.getPageId());
		assertEquals("type", serializedPage.getPageType());
		assertArrayEquals(DATA, serializedPage.getData());
	}

	@Test
	void getPersistedPageReturnsNullWhenAttributesAreMissing() {
		store.addPersistedPage(session, new SerializedPage(1, "type", DATA));
		redis.del(attributesKey(1));

		assertNull(store.getPersistedPage(session, 1));
	}

	@Test
	void getPersistedPageReturnsNullWhenDataIsMissing() {
		store.addPersistedPage(session, new SerializedPage(1, "type", DATA));
		redis.del(dataKey(1));

		assertNull(store.getPersistedPage(session, 1));
	}

	@Test
	void getPersistedPageSupportsPagesWithoutType() {
		store.addPersistedPage(session, new SerializedPage(1, DATA));

		SerializedPage page = (SerializedPage) store.getPersistedPage(session, 1);

		assertNull(page.getPageType());
		assertArrayEquals(DATA, page.getData());
	}

	@Test
	void getPersistedPagesListsPagesWithoutType() {
		store.addPersistedPage(session, new SerializedPage(1, DATA));

		List<IPersistedPage> pages = store.getPersistedPages(session);

		assertEquals(1, pages.size());
		assertNull(pages.get(0).getPageType());
	}

	@Test
	void getPersistedPagesSkipsIncompleteAttributes() {
		store.addPersistedPage(session, new SerializedPage(1, "type", DATA));
		store.addPersistedPage(session, new SerializedPage(2, "type", DATA));
		// what HMGET sees when the hash expires between KEYS and HMGET
		redis.hdel(attributesKey(2), "id", "size");

		List<IPersistedPage> pages = store.getPersistedPages(session);

		assertEquals(1, pages.size());
		assertEquals(1, pages.get(0).getPageId());
	}

	private String dataKey(int pageId) {
		return "Wicket-Redis|||" + session + "|||" + pageId;
	}

	private String attributesKey(int pageId) {
		return dataKey(pageId) + "|||attributes";
	}
}
