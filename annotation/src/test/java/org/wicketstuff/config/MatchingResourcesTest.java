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
package org.wicketstuff.config;

import java.net.URL;

import junit.framework.TestCase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;

/**
 * @author Doug Donohoe
 */
public class MatchingResourcesTest extends TestCase
{
	Logger logger = LoggerFactory.getLogger(MatchingResourcesTest.class);

	public void testFindResources()
	{
		MatchingResources mr = new MatchingResources(
			"classpath*:org/wicketstuff/config/MatchingResources.class");
		URL[] match = mr.getAllMatchesURL();
		assertTrue(match.length == 1);

		logger.info("URL: " + match[0]);
		assertTrue(mr.getURL(null) == null);

		Resource[] none = new MatchingResources(
			"classpath*:org/wicketstuff/config/NoSuchFile.class").getAllMatches();
		assertTrue(none.length == 0);
	}

	public void testFindResource()
	{
		Resource thiz = new MatchingResources(
			"classpath*:org/wicketstuff/config/MatchingResourcesTest.class").getSingleRequiredResource();
		assertNotNull(thiz);

		// test required
		try
		{
			new MatchingResources("classpath*:org/wicketstuff/config/NoSuchFile.class").getSingleRequiredResource();
			fail(); // should not get here
		}
		catch (RuntimeException e)
		{
			assertTrue(true);
		}

		// test multiple matches
		try
		{
			new MatchingResources("classpath*:org/wicketstuff/config/*.class").getSingleResource();
			fail(); // should not get here
		}
		catch (RuntimeException e)
		{
			assertTrue(true);
		}

	}

	public void testToString()
	{
		MatchingResources mr = new MatchingResources("classpath*:org/wicketstuff/config/*.class");
		assertTrue(mr.getAllMatches().length > 0);

		logger.info("URLs:\n" + mr);
	}
}
