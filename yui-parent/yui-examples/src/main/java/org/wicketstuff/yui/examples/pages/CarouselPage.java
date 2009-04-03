package org.wicketstuff.yui.examples.pages;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.resource.ContextRelativeResource;
import org.wicketstuff.yui.examples.WicketExamplePage;
import org.wicketstuff.yui.helper.Attributes;
import org.wicketstuff.yui.markup.html.carousel.YuiCarousel;

/**
 * Example on using Animation...
 * 
 * @author josh
 * 
 */
public class CarouselPage extends WicketExamplePage
{

	public CarouselPage()
	{
		List<String> list = Arrays.asList(new String[] { "images/bangkok.jpg", "images/hk.jpg",
				"images/jakarta.jpg", "images/sgp.jpg", "images/makati.jpg" });

		// first carousel with just core css for functionality
		add(new YuiCarousel<String>("carousel", list)
		{
			private static final long serialVersionUID = 1L;

			@Override
			protected Component newPanel(String id, String object)
			{
				return new ImagePanel(id, object);
			}

			@Override
			protected String getOpts()
			{
				return carouselOpts();
			}

			@Override
			protected String getCssClass()
			{
				return "custom";
			}
		});

		// 2nd carousel with sam skin by default
		add(new YuiCarousel<String>("carousel_sam", list)
		{
			private static final long serialVersionUID = 1L;

			@Override
			protected Component newPanel(String id, String object)
			{
				return new ImagePanel(id, object);
			}

			@Override
			protected String getOpts()
			{
				return carouselOpts();
			}
		});

		// 3nd carousel with sam skin by default but customised
		add(new YuiCarousel<String>("carousel_sam_customised", list)
		{
			private static final long serialVersionUID = 1L;

			@Override
			protected Component newPanel(String id, String object)
			{
				return new ImagePanel(id, object);
			}

			@Override
			protected String getOpts()
			{
				return carouselOpts();
			}

			@Override
			protected String getCssClass()
			{
				return "customised yui-skin-sam";
			}
		});
	}

	protected String carouselOpts()
	{
		// or simply {}
		Attributes attributes = new Attributes();

		// animation - animation : {speed : 0.5}
		attributes.add(new Attributes("animation", new Attributes("speed", 0.5F)));

		// circular
		attributes.add(new Attributes("isCircular", "true"));

		// num visible
		attributes.add(new Attributes("numVisible", 1));

		// reveal amount
		attributes.add(new Attributes("revealAmount", 0));

		// vertical
		// attributes.add(new Attributes("isVertical", "true"));

		// navigation - currently needs a patch
		// attributes.add(new Attributes("navigation", new
		// Attributes("prev", "myPrev").add("next", "myNext")
		// .toString()));

		return attributes.toString();
	}

	public class ImagePanel extends Panel
	{
		private static final long serialVersionUID = 1L;

		public ImagePanel(String id, String object)
		{
			super(id);
			add(new Image("image", new ContextRelativeResource(object)));
		}
	}
}
