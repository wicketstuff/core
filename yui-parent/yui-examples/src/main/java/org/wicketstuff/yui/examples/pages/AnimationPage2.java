package org.wicketstuff.yui.examples.pages;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.Model;
import org.wicketstuff.yui.examples.WicketExamplePage;
import org.wicketstuff.yui.markup.html.animation.thumbnail.AnimatedItem;
import org.wicketstuff.yui.markup.html.animation.thumbnail.AnimatedThumbnail;
import org.wicketstuff.yui.markup.html.animation.thumbnail.AnimatedThumbnailSettings;
import org.wicketstuff.yui.markup.html.image.URIImagePanel;

/**
 * Example on using Animation...
 * 
 * @author josh
 *
 */
public class AnimationPage2 extends WicketExamplePage 
{
	public AnimationPage2()
	{
		// thumbnail 1
		add(new MyAnimatedItem("thumbnail1"));
		
//		Link testLabel = new Link("test")
//		{
//			@Override
//			public void onClick()
//			{
//			}
//			
//		};
//		add(testLabel);
		
		// thumbnail 2
		AnimatedThumbnailSettings settings = new AnimatedThumbnailSettings("images/neuschwanstein_tn.jpg", "images/neuschwanstein.jpg" , "Neuschwanstein Castle" );
		settings.setOpacity(0.7f);
		settings.setPictureDimension(800, 583);
		settings.setThumbnailDimension(100, 74);
		AnimatedItem a;
		add(a = new AnimatedThumbnail("thumbnail2", settings));
		a.getOnunselectAnimation().addTrigger("test");
	}
	
	private class MyAnimatedItem extends AnimatedItem
	{
		public MyAnimatedItem(String id)
		{
			super(id);
			init();
		}

		@Override
		public Component newMouseoverItem(String id)
		{
			return new Label(id, "This is Singapore !! ");
		}

		@Override
		public Component newOnclickItem(String id)
		{
			return new URIImagePanel(id, new Model("images/sgp.jpg"));
		}

		@Override
		public Component newOnloadItem(String id)
		{
			return new URIImagePanel(id, new Model("images/singapore.png"));
		}
	}
}
