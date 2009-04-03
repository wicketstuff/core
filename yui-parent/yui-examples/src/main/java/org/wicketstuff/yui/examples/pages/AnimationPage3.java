package org.wicketstuff.yui.examples.pages;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.wicketstuff.yui.behavior.animation.YuiAnim;
import org.wicketstuff.yui.behavior.animation.YuiAnimEffect;
import org.wicketstuff.yui.behavior.animation.YuiEasing;
import org.wicketstuff.yui.examples.WicketExamplePage;
import org.wicketstuff.yui.helper.Attributes;
import org.wicketstuff.yui.markup.html.animation.thumbnail.AnimatedItem;
import org.wicketstuff.yui.markup.html.animation.thumbnail.AnimatedThumbnail;
import org.wicketstuff.yui.markup.html.animation.thumbnail.AnimatedThumbnailSettings;
import org.wicketstuff.yui.markup.html.ellipse.EllipsePanel;

/**
 * Example on using Animation...
 * 
 * @author josh
 *
 */
public class AnimationPage3 extends WicketExamplePage 
{
	private static int PIC_WIDTH = 300;
	private static int PIC_HEIGHT = 200;
	
	private static int PANEL_WIDTH = 600;
	private static int PANEL_HEIGHT = 400;
	
	private static int ITEM_WIDTH = 100;
	private static int ITEM_HEIGHT = 70;
	
	private static int PIC_LEFT = (PANEL_WIDTH - PIC_WIDTH) / 2;
	private static int PIC_TOP = (PANEL_HEIGHT - PIC_HEIGHT) / 2;
	
	private String textfield;
	
	private List<AnimatedItem> items = new ArrayList<AnimatedItem>();
	
	public AnimationPage3()
	{
		final TextField tf;
		add(tf = new TextField("textfield", new PropertyModel(this, "textfield")));
		
		EllipsePanel ellipsePanel;
		add(ellipsePanel = new EllipsePanel("ep1", new ThumbnailProvider())
		{
			@Override
			public Component newEllipseItem(String id, IModel model, int left, int top)
			{
				AnimatedThumbnailSettings settings = (AnimatedThumbnailSettings) model.getObject();
				settings.setThumbnailDimension(getItemWidth(), getItemHeight());
				settings.setPictureDimension(PIC_WIDTH, PIC_HEIGHT);
				settings.setPicturePosition(PIC_LEFT - left, PIC_TOP - top);
				
				AnimatedItem item = new MyAnimatedThumbnail(id, tf, settings);
				items.add(item);
				
				return item;
			}
			
			@Override
			protected void onBeforeRender()
			{
				super.onBeforeRender();
				for (AnimatedItem anItem : items) 
				{
					for (AnimatedItem anItem2 : items)
					{
						String value = anItem.getSelectValue();
						if (anItem != anItem2)
							anItem.getOnunselectAnimation().addTriggerOnValue(anItem2.getMouseoverItem(), tf , value);
					}
				}
			}
		});
		ellipsePanel.setDimension(PANEL_WIDTH, PANEL_HEIGHT, ITEM_WIDTH, ITEM_HEIGHT);
	}
	
	
	@SuppressWarnings("unused")
	private class MyAnimatedThumbnail extends AnimatedThumbnail
	{
		public MyAnimatedThumbnail(String id, FormComponent element, AnimatedThumbnailSettings settings)
		{
			super(id, element, settings);
		}
		
		@Override
		public YuiAnimEffect onselectEffect()
		{
			Attributes attributes = new Attributes();
			attributes.add(SHOW_ATTRIBUTE);
			attributes.add(new Attributes("width", 0, getSettings().getPictureWidth()));
			attributes.add(new Attributes("height", 0, getSettings().getPictureHeight()));
			attributes.add(new Attributes("left", 0, getSettings().getPictureLeft()));
			attributes.add(new Attributes("top", 0, getSettings().getPictureTop()));
			attributes.add(new Attributes("opacity", 0, 1));
			
			return new YuiAnim(YuiAnimEffect.Type.Anim, attributes, 1, YuiEasing.bounceIn);	
		}
		
		@Override
		public YuiAnimEffect onunselectEffect()
		{
			Attributes attributes = new Attributes();
			attributes.add(HIDE_ATTRIBUTE);
			attributes.add(new Attributes("width", getSettings().getPictureWidth(), 0));
			attributes.add(new Attributes("height", getSettings().getPictureHeight(), 0));
			attributes.add(new Attributes("left", getSettings().getPictureLeft(), 0));
			attributes.add(new Attributes("top", getSettings().getPictureTop(), 0));
			attributes.add(new Attributes("opacity", 0.6f, 0f));
			
			return new YuiAnim(YuiAnimEffect.Type.Anim, attributes, 1, YuiEasing.bounceOut);
		}
	}
	
	/**
	 * a Provider of thumbnails
	 * @author josh
	 *
	 */
	private class ThumbnailProvider implements IDataProvider
	{
		List<AnimatedThumbnailSettings> listOfThumbnails = new ArrayList<AnimatedThumbnailSettings>();
		
		public ThumbnailProvider()
		{
			listOfThumbnails.add(new AnimatedThumbnailSettings("images/singapore.png", 	"images/sgp.jpg",  	  "Singapore",  "sg"));
			listOfThumbnails.add(new AnimatedThumbnailSettings("images/malaysia.png", 	"images/kl.jpg", 	  "Malaysia",   "my"));
			listOfThumbnails.add(new AnimatedThumbnailSettings("images/indonesia.png", 	"images/jakarta.jpg", "Indonesia",  "in"));
			listOfThumbnails.add(new AnimatedThumbnailSettings("images/philippines.png","images/makati.jpg",  "Philippines","ph"));
			listOfThumbnails.add(new AnimatedThumbnailSettings("images/thailand.png", 	"images/bangkok.jpg", "Thailand", 	"th"));
			listOfThumbnails.add(new AnimatedThumbnailSettings("images/hongkong.png", 	"images/hk.jpg",      "Hong Kong",  "hk"));			
		}

		@SuppressWarnings("unchecked")
		public Iterator iterator(int first, int count)
		{
			return this.listOfThumbnails.iterator();
		}

		public IModel model(Object object)
		{
			return new Model((AnimatedThumbnailSettings) object);
		}

		public int size()
		{
			return this.listOfThumbnails.size();
		}

		public void detach()
		{
		}
	}

	public String getTextfield()
	{
		return textfield;
	}

	public void setTextfield(String textfield)
	{
		this.textfield = textfield;
	}
}
