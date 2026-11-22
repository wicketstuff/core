/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License") +  you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.wicketstuff.select2.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * @author lexx
 */
public class ValueSplitterTest
{

	@Test
	public void testSplitWithSingleValue() throws Exception
	{
		String[] strings = ValueSplitter.split("A");
		assertEquals(1, strings.length);
		assertEquals("A", strings[0]);
	}

	@Test
	public void testSplitWithRegularCSV() throws Exception
	{
		String[] strings = ValueSplitter.split("A,B,C");
		assertEquals(3, strings.length);
	}

	@Test
	public void testSplitWithSingleJsonId() throws Exception
	{
		String jsonId = "{\"someKey\":\"someValue\"}";
		String[] strings = ValueSplitter.split(jsonId);
		assertEquals(1, strings.length);
		assertEquals(jsonId, strings[0]);
	}

	@Test
	public void testSplitWithSingleJsonIdThatHasNestedObjects() throws Exception
	{
		String jsonId = "{\"email\":{\"emailAddress\":\"test@test.com\"},\"isContact\":true}";
		String[] strings = ValueSplitter.split(jsonId);
		assertEquals(1, strings.length);
		assertEquals(jsonId, strings[0]);
	}

	@Test
	public void testSplitWithMultipleJsonIds() throws Exception
	{
		String jsonId = "{\"someKey\":\"someValue\"},{\"someKey\":\"otherValue\"}";
		String[] strings = ValueSplitter.split(jsonId);
		assertEquals(2, strings.length);
	}

	@Test
	public void testSplitWithMultipleJsonIdsAndNestedJsonObjects() throws Exception
	{
		String[] strings = ValueSplitter.split("{\"email\":{\"emailAddress\":\"nouser@test.com\"},\"isContact\":true},{\"email\":{\"emailAddress\":\"otheruser@test.com\"},\"isContact\":contact}");
		assertEquals(2, strings.length);
	}
}