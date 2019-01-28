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

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.jupiter.api.Test;

/**
 * Test for {@link MemcachedSet}.
 */
public class MemcachedSetTest {

	@Test
	public void encode() {
		assertEquals("", MemcachedSet.encodeSet(new HashSet<>()));
		assertEquals("+1 +2 +3", MemcachedSet.encodeSet(new HashSet<>(Arrays.asList("1", "2", "3"))));
	}

	@Test
	public void decode() {
		assertEquals(new HashSet<>(), MemcachedSet.decodeSet(null));
		assertEquals(new HashSet<>(), MemcachedSet.decodeSet(""));
		assertEquals(new HashSet<>(Arrays.asList("1", "2", "3")), MemcachedSet.decodeSet("+1 +2 +3"));
		assertEquals(new HashSet<>(Arrays.asList("2", "3")), MemcachedSet.decodeSet("+1 +2 +3 -1 -4"));
	}
}