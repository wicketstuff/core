package org.wicketstuff.htmlcompressor;

import junit.framework.Assert;

import org.apache.wicket.RuntimeConfigurationType;
import org.apache.wicket.mock.MockApplication;
import org.apache.wicket.util.crypt.CharEncoding;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Test;

/**
 * Tests for {@link HtmlCompressingMarkupFactory} and {@link HtmlCompressingXmlPullParser}.
 * 
 * @author akiraly
 */
public class HtmlCompressingMarkupFactoryTest
{
	/**
	 * Uses {@link TestPage} for testing. Compares not compressed and compressed variants.
	 */
	@Test
	public void testDifference()
	{
		String notCompressed = renderAndDestroy(new WicketTester(new MockApplication()
		{
			@Override
			protected void init()
			{
				super.init();
				getMarkupSettings().setCompressWhitespace(true);
				getMarkupSettings().setDefaultMarkupEncoding(CharEncoding.UTF_8);
			}

			@Override
			public RuntimeConfigurationType getConfigurationType()
			{
				return RuntimeConfigurationType.DEPLOYMENT;
			}
		}));
		String compressed = renderAndDestroy(new WicketTester(new MockApplication()
		{
			@Override
			protected void init()
			{
				super.init();
				getMarkupSettings().setMarkupFactory(new HtmlCompressingMarkupFactory());
				getMarkupSettings().setDefaultMarkupEncoding(CharEncoding.UTF_8);
			}

			@Override
			public RuntimeConfigurationType getConfigurationType()
			{
				return RuntimeConfigurationType.DEPLOYMENT;
			}
		}));

		Assert.assertTrue("Length of compressed html (" + compressed.length() +
			") should be smaller than not compressed one (" + notCompressed.length() + ").",
			notCompressed.length() > compressed.length());
	}

	private String renderAndDestroy(WicketTester tester)
	{
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
