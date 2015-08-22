package org.wicketstuff.yui.markup.html.ellipse;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.behavior.HeaderContributor;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.wicketstuff.yui.helper.CSSInlineStyle;

/**
 * a Panel that can layout it's child in an Ellipse.
 * 
 * @author josh
 *
 */
public abstract class EllipsePanel extends Panel
{
	private static final long serialVersionUID = 1L;

	private int width;
	
	private int height;
	
	private int itemWidth;
	
	private int itemHeight;
	
	private EllipseHelper ellipseHelper;
	
	/**
	 * Constructor
	 * @param id
	 */
	@SuppressWarnings("serial")
	public EllipsePanel(String id, IDataProvider provider)
	{
		super(id);
		add(HeaderContributor.forCss(EllipsePanel.class, "EllipsePanel.css"));
		this.ellipseHelper = new EllipseHelper(provider.size());
	
		WebMarkupContainer panel;
		add(panel = new WebMarkupContainer("ellipse_panel"));
		panel.add(new AttributeAppender("style", true, new AbstractReadOnlyModel()
		{
			@Override
			public Object getObject()
			{
				CSSInlineStyle size = new CSSInlineStyle();
				size.add("width", getWidth());
				size.add("height", getHeight());
				return size;
			}
		}, ""));
		
		panel.add(new DataView("items", provider)
		{
			@Override
			protected void populateItem(Item item)
			{
				// coordinates for the ellipse item
				int index = item.getIndex();
				int left = ellipseHelper.getLeft(index);
				int top = ellipseHelper.getTop(index);
				
				// positions the child item 
				Component child;
				item.add(child = newEllipseItem("item", item.getModel(), left, top));
				child.add(new AttributeAppender("style", true, new Model("left:"+left+";top:"+top+";"), ""));
				child.add(new AttributeAppender("style", true, new Model(getItemDimention()), ""));
				item.setRenderBodyOnly(true);
			}
		});
	}
	
	/**
	 * the Dimension of an Item in this Ellipse
	 * @return
	 */
	protected String getItemDimention()
	{
		CSSInlineStyle dimension = new CSSInlineStyle();
		dimension.add("width", getItemWidth());
		dimension.add("height", getItemHeight());
		return dimension.toString();
	}

	/**
	 * this is the factory method that returns the Component to be added
	 * at the position "left" and "top" in the ellipse
	 * 
	 * @param id
	 * @param model
	 * @param left
	 * @param top
	 * @return
	 */
	public abstract Component newEllipseItem(String id, IModel model, int left, int top);

	/**
	 * the dimension of this Panel and its items
	 * 
	 * @param width
	 * @param height
	 * @param item_height 
	 * @param item_width 
	 */
	public void setDimension(int width, int height, int item_width, int item_height)
	{
		setWidth(width);
		setHeight(height);
		setItemWidth(item_width);
		setItemHeight(item_height);
		getEllipseHelper().setWidth(width - item_width);
		getEllipseHelper().setHeight(height - item_height);
	}

	public int getWidth()
	{
		return width;
	}

	public void setWidth(int width)
	{
		this.width = width;
	}

	public int getHeight()
	{
		return height;
	}

	public void setHeight(int height)
	{
		this.height = height;
	}

	public int getItemWidth()
	{
		return itemWidth;
	}

	public void setItemWidth(int itemWidth)
	{
		this.itemWidth = itemWidth;
	}

	public int getItemHeight()
	{
		return itemHeight;
	}

	public void setItemHeight(int itemHeight)
	{
		this.itemHeight = itemHeight;
	}
	
	public EllipseHelper getEllipseHelper()
	{
		return ellipseHelper;
	}

	public void setEllipseHelper(EllipseHelper ellipseHelper)
	{
		this.ellipseHelper = ellipseHelper;
	}
}
