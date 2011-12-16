package org.wicketstuff.pageserializer.kryo;

import org.apache.wicket.serialize.ISerializer;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Simple test using the WicketTester
 */
public class KryoSerializerTest
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

		ISerializer pageSerializer = tester.getApplication().getFrameworkSettings().getSerializer();
		Assert.assertTrue(
			"The configured IObjectSerializer is not instance of KryoSerializer! Type: " +
				pageSerializer.getClass(), pageSerializer instanceof KryoSerializer);

		byte[] data = pageSerializer.serialize(page);
		Assert.assertNotNull("The produced data should not be null!", data);

		// data length can fluctuate based on the object field values
		Assert.assertTrue("The produced data length is not correct!", data.length > 300);

		Object object = pageSerializer.deserialize(data);
		Assert.assertTrue(
			"The deserialized page must be of type HomePage. Type: " + object.getClass(),
			object instanceof HomePage);

	}
}
