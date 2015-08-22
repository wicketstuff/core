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
package org.wicketstuff.security.util;

import java.util.Set;

import junit.framework.TestCase;

/**
 * @author marrink
 * 
 */
public class ManyToManyMapTest extends TestCase
{

	/**
	 * @param name
	 */
	public ManyToManyMapTest(String name)
	{
		super(name);
	}

	/**
	 * Test method for
	 * {@link org.wicketstuff.security.util.ManyToManyMap#add(java.lang.Object, java.lang.Object)} .
	 */
	public void testAdd()
	{
		ManyToManyMap<String, String> map = new ManyToManyMap<String, String>();
		map.add("A.1", "A");
		map.add("A.2", "A");
		map.add("A.1", "AB");
		map.add("A.2", "AB");
		map.add("B.1", "B");
		map.add("B.2", "B");
		map.add("B.1", "AB");
		map.add("B.2", "AB");
		map.add("C.1", "C");
		assertEquals(9, map.size());
		Set<String> manys = map.getRight("A.1");
		assertEquals(2, manys.size());
		assertTrue(manys.contains("A"));
		assertTrue(manys.contains("AB"));
		manys = map.getLeft("A");
		assertEquals(2, manys.size());
		assertTrue(manys.contains("A.1"));
		assertTrue(manys.contains("A.2"));
		manys = map.getLeft("AB");
		assertEquals(4, manys.size());
		assertTrue(manys.contains("A.1"));
		assertTrue(manys.contains("A.2"));
		assertTrue(manys.contains("B.1"));
		assertTrue(manys.contains("B.2"));
		manys = map.getLeft("C");
		assertEquals(1, manys.size());
		assertTrue(manys.contains("C.1"));
	}

	/**
	 * Test method for
	 * {@link org.wicketstuff.security.util.ManyToManyMap#remove(java.lang.Object, java.lang.Object)}
	 * .
	 */
	public void testRemove()
	{
		ManyToManyMap<String, String> map = new ManyToManyMap<String, String>();
		map.add("A.1", "A");
		map.add("A.2", "A");
		assertEquals(3, map.size());
		assertTrue(map.remove("A.1", "A"));
		assertEquals(2, map.size());
		map.add("A.1", "A");
		assertEquals(3, map.size());

		map = new ManyToManyMap<String, String>();
		map.add("A.1", "A");
		assertEquals(2, map.size());
		map.remove("A.1", "A");
		assertEquals(0, map.size());
		assertTrue(map.isEmpty());
	}

	/**
	 * Test method for
	 * {@link org.wicketstuff.security.util.ManyToManyMap#removeAllMappingsForLeft(Object)} and
	 * right.
	 */
	public void testRemoveAllMappings()
	{
		ManyToManyMap<String, String> map = new ManyToManyMap<String, String>();
		map.add("A.1", "A");
		map.add("A.2", "A");
		Set<String> manys = map.removeAllMappingsForRight("A");
		assertEquals(0, map.size());
		assertTrue(manys.contains("A.1"));
		assertTrue(manys.contains("A.2"));

		map.add("A.1", "A");
		map.add("A.2", "A");
		map.add("A.2", "B");
		manys = map.removeAllMappingsForLeft("A.1");
		assertEquals(3, map.size());
		assertTrue(manys.contains("A"));
		assertFalse(manys.contains("B"));
	}

	/**
	 * Test method for {@link org.wicketstuff.security.util.ManyToManyMap#getLeft(java.lang.Object)}
	 * .
	 */
	public void testGet()
	{
		ManyToManyMap<String, String> map = new ManyToManyMap<String, String>();
		map.add("A.1", "A");
		map.add("A.2", "A");
		Set<String> manys = map.getLeft("B");
		assertNotNull(manys);
		assertTrue(manys.isEmpty());
		manys = map.getLeft("A");
		assertFalse(manys.isEmpty());
		assertEquals(2, manys.size());
	}

	/**
	 * Test method for {@link org.wicketstuff.security.util.ManyToManyMap#size()}.
	 */
	public void testSize()
	{
		ManyToManyMap<String, String> map = new ManyToManyMap<String, String>();
		assertEquals(0, map.size());
		map.add("A.1", "A");
		map.add("A.2", "A");
		assertEquals(3, map.size());
	}

	/**
	 * Test method for
	 * {@link org.wicketstuff.security.util.ManyToManyMap#numberOfmappingsForLeft(Object)} and
	 * right.
	 */
	public void testNumberOfmappings()
	{
		ManyToManyMap<String, String> map = new ManyToManyMap<String, String>();
		map.add("A.1", "A");
		map.add("A.2", "A");
		assertEquals(2, map.numberOfmappingsForRight("A"));
		assertEquals(1, map.numberOfmappingsForLeft("A.1"));
		assertEquals(0, map.numberOfmappingsForRight("B"));
	}

	/**
	 * Test method for {@link org.wicketstuff.security.util.ManyToManyMap#containsLeft(Object)} and
	 * right.
	 */
	public void testContains()
	{
		ManyToManyMap<String, String> map = new ManyToManyMap<String, String>();
		map.add("A.1", "A");
		assertTrue(map.containsRight("A"));
		assertTrue(map.containsLeft("A.1"));
		assertFalse(map.containsLeft("A.2"));
	}

	/**
	 * Test method for {@link org.wicketstuff.security.util.ManyToManyMap#isEmpty()}.
	 */
	public void testIsEmpty()
	{
		assertTrue(new ManyToManyMap<String, String>().isEmpty());
	}

	/**
	 * Test method for {@link org.wicketstuff.security.util.ManyToManyMap#clear()}.
	 */
	public void testClear()
	{
		ManyToManyMap<Integer, Integer> map = new ManyToManyMap<Integer, Integer>();
		map.add(1, 10);
		assertFalse(map.isEmpty());
		map.clear();
		assertTrue(map.isEmpty());
	}

}
