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
package org.wicketstuff.jamon.component;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;


public class PropertyModelObjectComparatorTest
{
	@Test
	public void shouldReturnZeroIfObjectsAreEqual()
	{
		PropertyModelObjectComparator ascComparator = new PropertyModelObjectComparator(true,
			"name");
		assertEquals(0,
			ascComparator.compare(new PropertyModelObject("ben"), new PropertyModelObject("ben")));

		PropertyModelObjectComparator descComparator = new PropertyModelObjectComparator(false,
			"name");
		assertEquals(0,
			descComparator.compare(new PropertyModelObject("ben"), new PropertyModelObject("ben")));
	}

	@Test
	public void shouldReturnNegativeIfObject1IsConsideredLessThanObject2AndSortOrderIsAscending()
	{
		PropertyModelObjectComparator ascComparator = new PropertyModelObjectComparator(true,
			"name");
		assertEquals(-1, ascComparator.compare(new PropertyModelObject("ben1"),
			new PropertyModelObject("ben2")));
	}

	@Test
	public void shouldReturnPositiveIfObject1IsConsideredLessThanObject2AndSortOrderIsDescending()
	{
		PropertyModelObjectComparator ascComparator = new PropertyModelObjectComparator(false,
			"name");
		assertEquals(1, ascComparator.compare(new PropertyModelObject("ben1"),
			new PropertyModelObject("ben2")));
	}

	@Test
	public void shouldReturnPositiveIfObject1IsConsideredMoreThanObject2AndSortOrderIsAscending()
	{
		PropertyModelObjectComparator ascComparator = new PropertyModelObjectComparator(true,
			"name");
		assertEquals(1, ascComparator.compare(new PropertyModelObject("ben2"),
			new PropertyModelObject("ben1")));
	}

	@Test
	public void shouldReturnNegativeIfObject1IsConsideredMoreThanObject2AndSortOrderIsDescending()
	{
		PropertyModelObjectComparator ascComparator = new PropertyModelObjectComparator(false,
			"name");
		assertEquals(-1, ascComparator.compare(new PropertyModelObject("ben2"),
			new PropertyModelObject("ben1")));
	}
}
