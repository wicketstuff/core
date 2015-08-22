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
package org.apache.wicket.security.login.http;

import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import junit.framework.TestCase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author marrink
 */
public class HttpDigestHeaderTest extends TestCase
{
	private static final Logger log = LoggerFactory.getLogger(HttpDigestHeaderTest.class);

	/**
	 * Construct.
	 * 
	 * @param name
	 */
	public HttpDigestHeaderTest(String name)
	{
		super(name);
	}

	/**
	 * @see junit.framework.TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
	}

	/**
	 * @see junit.framework.TestCase#tearDown()
	 */
	@Override
	protected void tearDown() throws Exception
	{
		super.tearDown();
	}

	/**
	 * Test regex pattern used in parsing request header.
	 */
	@SuppressWarnings("null")
	public void testRegExPattern()
	{
		Pattern headerPattern = null;
		try
		{
			Field field = HttpDigestLoginPage.class.getDeclaredField("HEADER_FIELDS");
			field.setAccessible(true);
			headerPattern = (Pattern) field.get(null);
		}
		catch (IllegalArgumentException e)
		{
			fail(e.getMessage());
		}
		catch (SecurityException e)
		{
			fail(e.getMessage());
		}
		catch (IllegalAccessException e)
		{
			fail(e.getMessage());
		}
		catch (NoSuchFieldException e)
		{
			fail(e.getMessage());
		}
		assertNotNull(headerPattern);
		assertFalse(headerPattern.matcher("").matches());
		String header =
			"username=\"Mufasa\",realm=\"testrealm@host.com\","
				+ "nonce=\"dcd98b7102dd2f0e8b11d0f600bfb0c093\",uri=\"/dir/index.html\","
				+ "qop=auth,nc=00000001,cnonce=\"0a4f113b\","
				+ "response=\"6629fae49393a05397450978507c4ef1\","
				+ "opaque=\"5ccc069c403ebaf9f0171e9517f40e41\"";
		Matcher matcher = headerPattern.matcher(header);
		assertTrue(matcher.matches());
		matcher.reset();
		for (int i = 0; i < 9; i++)
		{
			assertTrue(matcher.find());
			log.error("" + i + ": " + matcher.group());
		}
	}
}
