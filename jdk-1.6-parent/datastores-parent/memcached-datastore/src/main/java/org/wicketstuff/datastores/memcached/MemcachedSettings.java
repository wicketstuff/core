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

import java.util.List;

import org.apache.wicket.util.lang.Args;
import org.apache.wicket.util.lang.Generics;
import org.apache.wicket.util.string.Strings;
import org.apache.wicket.util.time.Duration;

/**
 * Default implementation of IMemcachedSettings
 */
public class MemcachedSettings implements IMemcachedSettings {

	private List<String> servers;

	private int port = 11211;

	// default is 30 minutes (in seconds)
	private int expirationTime = 30 * 60;

	private Duration shutdownTimeout = Duration.seconds(10);

	/**
	 * Constructor.
	 *
	 * Sets {@literal localhost} as the only Memcached server.
	 * Use {@linkplain #setServerNames(String)} to change it.
	 */
	public MemcachedSettings() {
		setServerNames("localhost");
	}

	@Override
	public List<String> getServerList() {
		return servers;
	}

	@Override
	public IMemcachedSettings setServerNames(String serverNames) {
		Args.notEmpty(serverNames, "serverNames");

		String[] ss = Strings.split(serverNames, ',');
		this.servers = Generics.newArrayList(ss.length);
		for (String server : ss)
		{
			this.servers.add(server.trim());
		}
		return this;
	}

	@Override
	public int getPort() {
		return port;
	}

	@Override
	public IMemcachedSettings setPort(int port) {
		this.port = port;
		return this;
	}

	@Override
	public int getExpirationTime() {
		return expirationTime;
	}

	@Override
	public IMemcachedSettings setExpirationTime(int expirationTime) {
		this.expirationTime = expirationTime;
		return this;
	}

	@Override
	public Duration getShutdownTimeout() {
		return shutdownTimeout;
	}

	@Override
	public IMemcachedSettings setShutdownTimeout(Duration timeout) {
		this.shutdownTimeout = Args.notNull(timeout, "timeout");
		return this;
	}
}
