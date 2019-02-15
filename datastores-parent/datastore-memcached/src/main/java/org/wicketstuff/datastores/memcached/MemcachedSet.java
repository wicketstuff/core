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
import java.util.concurrent.ExecutionException;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.wicket.util.string.Strings;

import net.spy.memcached.CASValue;
import net.spy.memcached.MemcachedClient;

/**
 * A set maintained in Memcached, see
 * <a href="http://dustin.sallings.org/2011/02/17/memcached-set.html">maintain a set in memcached</a>.
 * <p>
 * Elements removed from this set are marked for removal only, they are not actually removed until {@link #compact()}
 * is invoked.   
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
	
	/**
	 * Add a value to this set.
	 *
	 * @param value value to add
	 * @return whether the set was created
	 */
	public boolean add(String value)
	{
		return modify(PLUS + value);
	}

	/**
	 * Remove a value from this set.
	 *
	 * @param value value to remove
	 * @return whether the set was created
	 */
	public boolean remove(String value)
	{
		return modify(MINUS + value);
	}

	private boolean modify(String modification)
	{
		if (modification.indexOf(' ') != -1) {
			throw new IllegalArgumentException(String.format("value '%s' may not contain a space", modification.substring(1)));
		}
		
		boolean created = false;

		// repeat until successful
		while (true) {
			try {
				// try appending to an existing key
				if (client.append(key, " " + modification).get() == true) {
					// prolong expiration time, since append does not
					client.touch(key, expirationTime);

					// success
					break;
				}

				// try adding a new key
				if (client.add(key, expirationTime, modification).get() == true) {
					// key is new
					created = true;

					// success
					break;
				}
			} catch (InterruptedException | ExecutionException retry) {
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

		// the CAS value may be stale and setting it ignored,
		// but on following invocations it will succeed eventually 
		client.asyncCAS(key, cas.getCas(), encodeSet(set));
	}
	
	/**
	 * Encode a set, e.g.
	 * <pre>
	 * [A, B, C, D] -> "+A +B +C +D"
	 * </pre>
	 *
	 * @param value encoded set
	 * @return decoded set
	 */
	static String encodeSet(Set<String> pageIds)
	{
		if (pageIds.isEmpty()) {
			return "";
		} else {
			return PLUS + Strings.join(" +", pageIds.toArray(new String[pageIds.size()]));
		}
	}

	/**
	 * Decode a set, e.g.
	 * <pre>
	 * "+A +A +B -B +B -C +C -C" -> [A, B]
	 * </pre>
	 *
	 * @param value encoded set
	 * @return decoded set
	 */
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