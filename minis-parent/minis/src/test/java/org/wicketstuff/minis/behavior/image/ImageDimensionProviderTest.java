package org.wicketstuff.minis.behavior.image;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.markup.IMarkupFragment;
import org.apache.wicket.markup.Markup;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.util.tester.TagTester;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link ImageDimensionProvider}.
 *
 * @author akiraly
 */
public class ImageDimensionProviderTest
{
	private final WicketTester tester = new WicketTester();

	@AfterEach
	public void after()
	{
		tester.destroy();
	}

	@Test
	public void testDimensions()
	{
		Image image = new Image("foo", AbstractDefaultAjaxBehavior.INDICATOR);
		int width = 200;
		int height = 70;
		ImageDimensionProvider dimensionProvider = new ImageDimensionProvider(width, height);
		image.add(dimensionProvider);

		IMarkupFragment markup = Markup.of("<div><img wicket:id=\"" + image.getId() + "\" /></div>");

		tester.startComponentInPage(image, markup);

		TagTester tagTester = tester.getTagByWicketId(image.getId());

		assertNotNull(tagTester);
		assertEquals(Integer.toString(width),
			tagTester.getAttribute(AbstractImageDimensionProvider.WIDTH));
		assertEquals(Integer.toString(height),
			tagTester.getAttribute(AbstractImageDimensionProvider.HEIGHT));
	}
}
