package org.wicketstuff.yui.markup.html.slider;

import java.io.Serializable;

import org.apache.wicket.ResourceReference;

public class YuiSliderSettings implements Serializable
{

	private static final long serialVersionUID = 1L;

	protected static int LEFT_DOWN = 0;

	private Integer startValue;

	private Integer length;

	private Integer tickValue;

	private ResourceReference thumbResource;

	private ResourceReference backgroundResource;

	private ResourceReference leftUpResource;

	private ResourceReference rightDownResource;

	public static YuiSliderSettings getHorizDefault(int length, int tick, int startValue)
	{
		ResourceReference background = new ResourceReference(YuiSlider.class, "style/def/bg-h.png");
		ResourceReference thumb = new ResourceReference(YuiSlider.class, "style/def/thumb-h.png");
		ResourceReference leftCorner = new ResourceReference(YuiSlider.class, "style/def/left.gif");
		ResourceReference rightCorner = new ResourceReference(YuiSlider.class,
				"style/def/right.gif");

		return new YuiSliderSettings(length, tick, startValue, background, thumb, leftCorner,
				rightCorner);
	}

	public static YuiSliderSettings getVertDefault(int length, int tick, int startValue)
	{
		ResourceReference background = new ResourceReference(YuiSlider.class, "style/def/bg-v.png");
		ResourceReference thumb = new ResourceReference(YuiSlider.class, "style/def/thumb-v.png");
		ResourceReference leftCorner = new ResourceReference(YuiSlider.class, "style/def/top.gif");
		ResourceReference rightCorner = new ResourceReference(YuiSlider.class,
				"style/def/bottom.gif");

		return new YuiSliderSettings(length, tick, startValue, background, thumb, leftCorner,
				rightCorner);
	}

	public YuiSliderSettings()
	{
	}

	/**
	 * 
	 * @param length
	 *            the size of the slider in px
	 * @param tick
	 *            the px size the thumb will slide by
	 * @param startValue
	 *            the initial px size
	 * @param bg
	 *            the Background resource
	 * @param thumb
	 *            the Thumb resource
	 * @param leftUp
	 *            the Left/up Resource
	 * @param rightDown
	 *            the Right/down resource
	 */
	public YuiSliderSettings(int length, int tick, int startValue, ResourceReference bg,
			ResourceReference thumb, ResourceReference leftUp, ResourceReference rightDown)
	{
		this.length = length;
		this.tickValue = tick;
		this.startValue = startValue;
		this.backgroundResource = bg;
		this.thumbResource = thumb;
		this.leftUpResource = leftUp;
		this.rightDownResource = rightDown;
	}

	public Integer getStartValue()
	{
		return startValue;
	}

	public void setStartValue(Integer startValue)
	{
		this.startValue = startValue;
	}

	public ResourceReference getBackgroundResource()
	{
		return backgroundResource;
	}

	public void setBackgroundResource(ResourceReference backgroundResource)
	{
		this.backgroundResource = backgroundResource;
	}

	public Integer getTickValue()
	{
		return tickValue;
	}

	public void setTickValue(Integer tickValue)
	{
		this.tickValue = tickValue;
	}

	public ResourceReference getThumbResource()
	{
		return thumbResource;
	}

	public void setThumbResource(ResourceReference thumbResource)
	{
		this.thumbResource = thumbResource;
	}

	public Integer getLength()
	{
		return length;
	}

	public void setLength(Integer length)
	{
		this.length = length;
	}

	public ResourceReference getLeftUpResource()
	{
		return leftUpResource;
	}

	public void setLeftUpResource(ResourceReference leftUpResource)
	{
		this.leftUpResource = leftUpResource;
	}

	public ResourceReference getRightDownResource()
	{
		return rightDownResource;
	}

	public void setRightDownResource(ResourceReference rightDownResource)
	{
		this.rightDownResource = rightDownResource;
	}
}
