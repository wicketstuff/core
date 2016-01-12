package org.wicketstuff.gmap.api;

import org.wicketstuff.gmap.js.Array;
import org.wicketstuff.gmap.js.ObjectLiteral;

/**
 * Used in GMarkerClusterOptions
 * 
 * JS code with options:
 * 'styles': (object) An object that has style properties:
 *    'url': (string) The image url.
 *    'height': (number) The image height.
 *    'width': (number) The image width.
 *    'anchor': (Array) The anchor position of the label text.
 *    'textColor': (string) The text color.
 *    'textSize': (number) The text size.
 *    'backgroundPosition': (string) The position of the backgound x, y.
 * 
 * @author Rob Sonke
 *
 */
public class GMarkerClusterStyle implements GValue
{
	private static final long serialVersionUID = 1L;

	private String url;
	private Integer height;
	private Integer width;
	private Integer[] anchor = new Integer[0];
	private String textColor;
	private Integer textSize;
	private String backgroundPosition;
	
	@Override
	public String getJSconstructor() 
	{
		ObjectLiteral literal = new ObjectLiteral();
		
		if(getUrl() != null)
			literal.setString("url", getUrl());
		if(getHeight() != null)
			literal.set("height", getHeight().toString());
		if(getWidth() != null)
			literal.set("width", getWidth().toString());
		
		Array array = new Array();
		for (Integer value: getAnchor()) 
		{
			array.add(value);
		}
		literal.set("anchor", array.toJS());
		
		if(getTextColor() != null)
			literal.setString("textColor", getTextColor());
		if(getTextSize() != null)
			literal.set("textSize", getTextSize().toString());
		if(getBackgroundPosition() != null)
			literal.setString("backgroundPosition", getBackgroundPosition());
		
		return literal.toJS();
	}
	
	public String getUrl() 
	{
		return url;
	}
	
	public GMarkerClusterStyle setUrl(String url) 
	{
		this.url = url;
		return this;
	}
	
	public Integer getHeight() 
	{
		return height;
	}
	
	public GMarkerClusterStyle setHeight(Integer height) 
	{
		this.height = height;
		return this;
	}
	
	public Integer getWidth() 
	{
		return width;
	}
	
	public GMarkerClusterStyle setWidth(Integer width) 
	{
		this.width = width;
		return this;
	}
	
	public Integer[] getAnchor() 
	{
		return anchor;
	}
	
	public GMarkerClusterStyle setAnchor(Integer[] anchor) 
	{
		this.anchor = anchor;
		return this;
	}
	
	public String getTextColor() 
	{
		return textColor;
	}
	
	public GMarkerClusterStyle setTextColor(String textColor) 
	{
		this.textColor = textColor;
		return this;
	}
	
	public Integer getTextSize() 
	{
		return textSize;
	}
	
	public GMarkerClusterStyle setTextSize(Integer textSize) 
	{
		this.textSize = textSize;
		return this;
	}
	
	public String getBackgroundPosition() 
	{
		return backgroundPosition;
	}
	
	public GMarkerClusterStyle setBackgroundPosition(String backgroundPosition) 
	{
		this.backgroundPosition = backgroundPosition;
		return this;
	}

}
