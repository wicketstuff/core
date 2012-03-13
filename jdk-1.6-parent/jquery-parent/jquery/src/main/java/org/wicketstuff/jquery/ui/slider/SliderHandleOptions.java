package org.wicketstuff.jquery.ui.slider;

import net.sf.json.JSONObject;
import org.apache.wicket.util.io.IClusterable;

/**
 * An integration of JQuery UI Slider widget (http://docs.jquery.com/UI/Slider/slider)
 * 
 * @author Martin Grigorov <martingrigorov @ users.sf.net>
 */
public class SliderHandleOptions implements IClusterable
{

	private static final long serialVersionUID = 1L;

	private static final String ID = "id";

	private static final String START = "start";

	private static final String MAX = "max";

	private static final String MIN = "min";

	private String cssStyle;

	private transient final JSONObject json = new JSONObject();

	public SliderHandleOptions(final String id, final Integer start)
	{

		setId(id);
		setStart(start);
	}

	public void setStyle(final String style)
	{
		cssStyle = style;
	}

	public String getStyle()
	{
		return cssStyle;
	}

	public SliderHandleOptions setId(final String id)
	{
		json.put(ID, id);
		return this;
	}

	public String getId()
	{
		return (String)json.get(ID);
	}

	public SliderHandleOptions setStart(final Integer start)
	{
		json.put(START, start);
		return this;
	}

	public Integer getStart()
	{
		return (Integer)json.get(START);
	}

	public SliderHandleOptions setMin(final Integer min)
	{
		json.put(MIN, min);
		return this;
	}

	public Integer getMin()
	{
		return (Integer)json.get(MIN);
	}

	public SliderHandleOptions setMax(final Integer max)
	{
		json.put(MAX, max);
		return this;
	}

	public Integer getMax()
	{
		return (Integer)json.get(MAX);
	}

	public JSONObject getJSON()
	{
		return json;
	}
}
