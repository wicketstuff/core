package org.wicketstuff.rest.utils.mounting;

import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.wicketstuff.rest.WicketApplication;

public class MountingAnnotationTest
{
	private WicketTester tester;

	@Before
	public void setUp()
	{
		tester = new WicketTester(new WicketApplication(new Roles())
		{
			@Override
			public void init()
			{
				super.init();
				PackageScanner.scanPackage(this, "org.wicketstuff.rest.resource");
			}
		});
	}

	@Test
	public void testResourceMounted() throws Exception
	{
		tester.getRequest().setMethod("GET");
		tester.executeUrl("./mountedpath");
		Assert.assertEquals(200, tester.getLastResponse().getStatus());
	}

}
