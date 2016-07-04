/**
 * Copyright (C)
 * 	2008 Jeremy Thomerson <jeremy@thomersonfamily.com>
 * 	2012 Michael Mosmann <michael@mosmann.de>
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
package org.wicketstuff.pageserializer.fast;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.serialize.ISerializer;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.wicketstuff.pageserializer.fast.pages.NotSerializablePage;
import org.wicketstuff.pageserializer.fast.pages.SamplePage;

/**
 * Simple test using the WicketTester
 */
public class FastSerializerTest
{
	private WicketTester tester;

	@Before
	public void setUp()
	{
		tester = new WicketTester(new WicketApplication());
	}

	@After
	public void tearDown()
	{
		tester.destroy();
	}

	@Test
	public void homepageRendersSuccessfully()
	{
		// start and render the test page
		HomePage page = tester.startPage(HomePage.class);

		// assert rendered page class
		tester.assertRenderedPage(HomePage.class);

		ISerializer pageSerializer = getAndCheckSerializer();

		byte[] data = pageSerializer.serialize(page);
		Assert.assertNotNull("The produced data should not be null!", data);

		// data length can fluctuate based on the object field values
		Assert.assertEquals("The produced data length is not correct!", 608, data.length);

		Object object = pageSerializer.deserialize(data);
		Assert.assertTrue(
				"The deserialized page must be of type HomePage. Type: " + object.getClass(),
				object instanceof HomePage);

	}

	@Test
	public void samplepageRendersSuccessfully()
	{
		// start and render the test page
		SamplePage page = tester.startPage(SamplePage.class,
				new PageParameters().add("Test", "asString"));

		// assert rendered page class
		tester.assertRenderedPage(SamplePage.class);

		ISerializer pageSerializer = getAndCheckSerializer();

		byte[] data = pageSerializer.serialize(page);
		Assert.assertNotNull("The produced data should not be null!", data);

		Object object = pageSerializer.deserialize(data);
		Assert.assertTrue(
				"The deserialized page must be of type HomePage. Type: " + object.getClass(),
				object instanceof SamplePage);

		SamplePage samplePage = (SamplePage)object;

		tester.startPage(samplePage);

		// assert rendered page class
		tester.assertRenderedPage(SamplePage.class);
	}

	@Test(expected = FastWicketSerialException.class)
	public void notSerializableComponentThrowsException()
	{

		NotSerializablePage page = tester.startPage(NotSerializablePage.class,
				new PageParameters().add("Test", "asString"));

		// assert rendered page class
		tester.assertRenderedPage(NotSerializablePage.class);

		ISerializer pageSerializer = getAndCheckSerializer();

		pageSerializer.serialize(page);
	}

	private ISerializer getAndCheckSerializer()
	{
		ISerializer pageSerializer = tester.getApplication().getFrameworkSettings().getSerializer();
		Assert.assertTrue(
				"The configured IObjectSerializer is not instance of FastSerializer! Type: "
						+ pageSerializer.getClass(), pageSerializer instanceof FastWicketSerializer);
		return pageSerializer;
	}
}
