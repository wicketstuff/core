package org.wicketstuff.minis.behavior.image;

import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.markup.IMarkupFragment;
import org.apache.wicket.markup.Markup;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.util.tester.TagTester;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for {@link ImageDimensionProvider}.
 * 
 * @author akiraly
 */
public class ImageDimensionProviderTest
{
	private WicketTester tester = new WicketTester();

	@After
	public void after()
	{
		tester.destroy();
	}

	@Test
	public void testDimensions()
	{
		Image image = new Image("foo", AbstractDefaultAjaxBehavior.INDICATOR);
		ImageDimensionProvider dimensionProvider = new ImageDimensionProvider(
			Integer.toString(200), Integer.toString(70));
		image.add(dimensionProvider);

		IMarkupFragment markup = Markup.of("<div><img wicket:id=\"" + image.getId() + "\" /></div>");

		tester.startComponent(image, markup);

		TagTester tagTester = tester.getTagByWicketId(image.getId());

		Assert.assertNotNull(tagTester);
		Assert.assertEquals(dimensionProvider.getWidth(),
			tagTester.getAttribute(AbstractImageDimensionProvider.WIDTH));
		Assert.assertEquals(dimensionProvider.getHeight(),
			tagTester.getAttribute(AbstractImageDimensionProvider.HEIGHT));
	}
}
