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

import org.apache.wicket.pageStore.IPageStore;
import org.junit.jupiter.api.Tag;
import org.wicketstuff.datastores.common.BaseDataStoreTest;

import net.spy.memcached.MemcachedClient;

/**
 * Performance and stability test for MemcachedDataStore, requires a running Memcached server.
 */
@Tag("memcached")
public class MemcachedDataStoreTest extends BaseDataStoreTest {

	@Override
	protected IPageStore createDataStore() throws Exception {
		IMemcachedSettings settings = new MemcachedSettings();
		// settings.setServerNames("");
		MemcachedClient client = MemcachedDataStore.createClient(settings);
		return new MemcachedDataStore("test", client, settings);
	}
}