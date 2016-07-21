package org.wicketstuff.htmlcompressor;


import org.apache.wicket.RuntimeConfigurationType;
import org.apache.wicket.mock.MockApplication;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.util.crypt.CharEncoding;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for {@link HtmlCompressingMarkupFactory} and {@link HtmlCompressingXmlPullParser}.
 * 
 * @author akiraly
 */
public class HtmlCompressingMarkupFactoryTest
{
	/**
	 * Base web application for tests.
	 */
	private static class BaseMockApplication extends MockApplication
	{
		@Override
		protected void init()
		{
			super.init();
			getMarkupSettings().setDefaultMarkupEncoding(CharEncoding.UTF_8);
		}

		@Override
		public RuntimeConfigurationType getConfigurationType()
		{
			return RuntimeConfigurationType.DEPLOYMENT;
		}
	}

	/**
	 * Uses {@link TestPage} for testing. Compares not compressed and compressed variants.
	 */
	@Test
	public void testDifference()
	{
		String notCompressed = renderAndDestroy(new BaseMockApplication()
		{
			@Override
			protected void init()
			{
				super.init();
				getMarkupSettings().setCompressWhitespace(true);
			}
		});
		String compressed = renderAndDestroy(new BaseMockApplication()
		{
			@Override
			protected void init()
			{
				super.init();
				getMarkupSettings().setMarkupFactory(new HtmlCompressingMarkupFactory());
			}
		});

		Assert.assertTrue("Length of compressed html (" + compressed.length() +
                          ") should be smaller than not compressed one (" + notCompressed.length() + ").",
                          notCompressed.length() > compressed.length());
	}

	/**
	 * Renders test page does some tests on it and returns rendered response.
	 * 
	 * @param application
	 *            used or rendering, not null
	 * @return last response as string, not null
	 */
	private String renderAndDestroy(WebApplication application)
	{
		WicketTester tester = new WicketTester(application);
		try
		{
			tester.startPage(TestPage.class);
			tester.assertLabel("label", "Hello World!");
			tester.assertVisible("label");
			tester.assertContains("€");
			return tester.getLastResponseAsString();
		}
		finally
		{
			tester.destroy();
		}
	}
}
