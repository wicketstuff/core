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

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.wicket.util.string.Strings;

import net.spy.memcached.CASValue;
import net.spy.memcached.MemcachedClient;
import net.spy.memcached.internal.OperationFuture;

/**
 * A set maintained in Memcached, see
 * <a href="http://dustin.sallings.org/2011/02/17/memcached-set.html">maintain a set in memcached</a>.
 */
public class MemcachedSet implements Iterable<String> {
	
	private static final String MINUS = "-";
	private static final String PLUS = "+";
	private final MemcachedClient client;
	private final String key;
	private final int expirationTime;
	
	public MemcachedSet(MemcachedClient client, String key, int expirationTime) {
		this.client = client;
		this.key = key;
		this.expirationTime = expirationTime;
	}

	@Override
	public Iterator<String> iterator() {
		return decodeSet((String)client.get(key)).iterator();
	}
	
	public boolean add(String value)
	{
		return update(PLUS, value);
	}
	
	public boolean remove(String value)
	{
		return update(MINUS, value);
	}
	
	private boolean update(String plusOrMinus, String value)
	{
		boolean created = false;
		
		OperationFuture<Boolean> result;
		while (true) {
			result = client.append(key, " " + plusOrMinus + value);
			
			try {
				if (result.get() == true) {
					client.touch(key, expirationTime);
					break;
				}
			} catch (Exception e) {
			}
			
			result = client.add(key, expirationTime, "");
			try {
				if (result.get() == true) {
					created = true;
				}
			} catch (Exception e) {
			}
		}

		return created;
	}
	
	/**
	 * Compact this set - may fail silently.
	 */
	public void compact() {
		compact(page -> true);
	}
	
	/**
	 * Compact this set - may fail silently.
	 * 
	 * @param predicate predicate to keep item in map
	 */
	public void compact(Predicate<String> predicate) {
		CASValue<Object> cas = client.gets(key);
		Set<String> set = decodeSet((String)cas.getValue());
		
		set = set.stream().filter(predicate).collect(Collectors.toSet());

		// ignore failure - the CAS value may be stale and setting it be ignored,
		// but on of the following tries will succeed 
		client.asyncCAS(key, cas.getCas(), encodeSet(set));
	}
	
	static String encodeSet(Set<String> pageIds)
	{
		if (pageIds.isEmpty()) {
			return "";
		} else {
			return PLUS + Strings.join(" +", pageIds.toArray(new String[pageIds.size()]));
		}
	}

	static Set<String> decodeSet(String value)
	{
		Set<String> pageIds = new HashSet<>();
		
		if (value != null) {
			for (String delta : Strings.split(value, ' ')) {
				if (delta.startsWith(PLUS)) {
					pageIds.add(delta.substring(1).trim());
				} else if (delta.startsWith(MINUS)) {
					pageIds.remove(delta.substring(1).trim());
				} 
			}
		}
		
		return pageIds;
	}
}