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
package org.apache.wicket.security.util;

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
	 * {@link org.apache.wicket.security.util.ManyToManyMap#add(java.lang.Object, java.lang.Object)}.
	 */
	public void testAdd()
	{
		ManyToManyMap map = new ManyToManyMap();
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
		Set manys = map.get("A.1");
		assertEquals(2, manys.size());
		assertTrue(manys.contains("A"));
		assertTrue(manys.contains("AB"));
		manys = map.get("A");
		assertEquals(2, manys.size());
		assertTrue(manys.contains("A.1"));
		assertTrue(manys.contains("A.2"));
		manys = map.get("AB");
		assertEquals(4, manys.size());
		assertTrue(manys.contains("A.1"));
		assertTrue(manys.contains("A.2"));
		assertTrue(manys.contains("B.1"));
		assertTrue(manys.contains("B.2"));
		manys = map.get("C");
		assertEquals(1, manys.size());
		assertTrue(manys.contains("C.1"));
	}

	/**
	 * Test method for
	 * {@link org.apache.wicket.security.util.ManyToManyMap#remove(java.lang.Object, java.lang.Object)}.
	 */
	public void testRemove()
	{
		ManyToManyMap map = new ManyToManyMap();
		map.add("A.1", "A");
		map.add("A.2", "A");
		assertEquals(3, map.size());
		assertTrue(map.remove("A.1", "A"));
		assertEquals(2, map.size());
		map.add("A.1", "A");
		assertEquals(3, map.size());
		// also works in reverse order
		assertTrue(map.remove("A", "A.1"));
		assertEquals(2, map.size());
		assertFalse(map.remove("A", "A.1"));

		map = new ManyToManyMap();
		map.add("A.1", "A");
		assertEquals(2, map.size());
		map.remove("A.1", "A");
		assertEquals(0, map.size());
		assertTrue(map.isEmpty());
	}

	/**
	 * Test method for
	 * {@link org.apache.wicket.security.util.ManyToManyMap#removeAllMappings(java.lang.Object)}.
	 */
	public void testRemoveAllMappings()
	{
		ManyToManyMap map = new ManyToManyMap();
		map.add("A.1", "A");
		map.add("A.2", "A");
		Set manys = map.removeAllMappings("A");
		assertEquals(0, map.size());
		assertTrue(manys.contains("A.1"));
		assertTrue(manys.contains("A.2"));

		map.add("A.1", "A");
		map.add("A.2", "A");
		map.add("A.2", "A.1");
		manys = map.removeAllMappings("A.1");
		assertEquals(2, map.size());
		assertTrue(manys.contains("A"));
		assertTrue(manys.contains("A.2"));

	}

	/**
	 * Test method for
	 * {@link org.apache.wicket.security.util.ManyToManyMap#get(java.lang.Object)}.
	 */
	public void testGet()
	{
		ManyToManyMap map = new ManyToManyMap();
		map.add("A.1", "A");
		map.add("A.2", "A");
		Set manys = map.get("B");
		assertNotNull(manys);
		assertTrue(manys.isEmpty());
		manys = map.get("A");
		assertFalse(manys.isEmpty());
		assertEquals(2, manys.size());
	}

	/**
	 * Test method for
	 * {@link org.apache.wicket.security.util.ManyToManyMap#size()}.
	 */
	public void testSize()
	{
		ManyToManyMap map = new ManyToManyMap();
		assertEquals(0, map.size());
		map.add("A.1", "A");
		map.add("A.2", "A");
		assertEquals(3, map.size());
	}

	/**
	 * Test method for
	 * {@link org.apache.wicket.security.util.ManyToManyMap#numberOfmappings(java.lang.Object)}.
	 */
	public void testNumberOfmappings()
	{
		ManyToManyMap map = new ManyToManyMap();
		map.add("A.1", "A");
		map.add("A.2", "A");
		assertEquals(2, map.numberOfmappings("A"));
		assertEquals(1, map.numberOfmappings("A.1"));
		assertEquals(0, map.numberOfmappings("B"));
	}

	/**
	 * Test method for
	 * {@link org.apache.wicket.security.util.ManyToManyMap#contains(java.lang.Object)}.
	 */
	public void testContains()
	{
		ManyToManyMap map = new ManyToManyMap();
		map.add("A.1", "A");
		assertTrue(map.contains("A"));
		assertTrue(map.contains("A.1"));
		assertFalse(map.contains("A.2"));
	}

	/**
	 * Test method for
	 * {@link org.apache.wicket.security.util.ManyToManyMap#isEmpty()}.
	 */
	public void testIsEmpty()
	{
		assertTrue(new ManyToManyMap().isEmpty());
	}

	/**
	 * Test method for
	 * {@link org.apache.wicket.security.util.ManyToManyMap#clear()}.
	 */
	public void testClear()
	{
		ManyToManyMap map = new ManyToManyMap();
		map.add(new Integer(1), new Integer(10));
		assertFalse(map.isEmpty());
		map.clear();
		assertTrue(map.isEmpty());
	}

}
