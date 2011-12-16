/**
 * Copyright (C) 2008 Jeremy Thomerson <jeremy@thomersonfamily.com>
 *
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
package org.wicketstuff.jslibraries;

import junit.framework.TestCase;

public class VersionTest extends TestCase
{

	public void testEquals()
	{
		assertTrue(new Version(1, 1).equals(new Version(1, 1)));
		assertTrue(new Version(2).equals(new Version(2)));
		assertFalse(new Version(1, 1).equals(new Version(1)));
		assertFalse(new Version(1).equals(new Version(1, 1)));
	}

	public void testMatches() throws Exception
	{
		assertTrue(new Version(1).matches(new Version()));
		assertTrue(new Version(1, 1).matches(new Version(1)));
		assertTrue(new Version(1, 1).matches(new Version(1, 1)));
		assertTrue(new Version(1, 1, 1).matches(new Version(1, 1)));
		assertTrue(new Version(1, 1, 1, 1).matches(new Version(1, 1)));
		assertFalse(new Version(1, 1).matches(new Version(1, 1, 1)));
		assertFalse(new Version(1, 1).matches(new Version(2)));
		assertFalse(new Version(2).matches(new Version(1, 1)));
	}
}
