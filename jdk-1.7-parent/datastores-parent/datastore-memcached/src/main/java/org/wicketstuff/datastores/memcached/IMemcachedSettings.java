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

import org.apache.wicket.util.time.Duration;

import java.util.List;

/**
 * Settings for MemcachedDataStore
 */
public interface IMemcachedSettings {

	/**
	 * @return a list with the names of all memcached servers
	 */
	List<String> getServerList();

	/**
	 * Parses the hostnames of the Memcached servers
	 *
	 * @param serverNames comma separated list of the hostnames
	 *                    where Memcached servers run
	 * @return this instance, for chaining
	 */
	IMemcachedSettings setServerNames(String serverNames);

	/**
	 * @return the port where Memcached server listens to
	 */
	int getPort();

	/**
	 * Sets the port where Memcached server listens to
	 *
	 * @param port the port where Memcached server listens to
	 * @return this instance, for chaining
	 */
	IMemcachedSettings setPort(int port);

	/**
	 * @return the duration after which a record will be evicted by Memcached. In seconds.
	 */
	int getExpirationTime();

	/**
	 * Sets the duration after which a record will be evicted by Memcached.
	 * In seconds.
	 *
	 * @param expirationTime the duration after which the record will be
	 *                       evicted by Memcached
	 * @return this instance, for chaining
	 */
	IMemcachedSettings setExpirationTime(int expirationTime);

	/**
	 * @return the time to wait when shutting down the connection to
	 *                the Memcached server
	 */
	Duration getShutdownTimeout();

	/**
	 * Sets the time to wait when shutting down the connection to
	 * the Memcached server.
	 *
	 * @param timeout the time to wait when shutting down the connection to
	 *                the Memcached server
	 * @return this instance, for chaining
	 */
	IMemcachedSettings setShutdownTimeout(Duration timeout);
}
