package org.wicketstuff.jquery.ui.slider;

import net.sf.json.JSONArray;
import net.sf.json.JSONFunction;
import net.sf.json.JSONObject;
import org.apache.wicket.util.io.IClusterable;

/**
 * An integration of JQuery UI Slider widget (http://docs.jquery.com/UI/Slider/slider)
 * 
 * A Java representation of Slider's options (http://docs.jquery.com/UI/Slider/slider#options)
 * 
 * @author Martin Grigorov <martingrigorov @ users.sf.net>
 */
public class SliderOptions implements IClusterable
{

	private static final long serialVersionUID = 1L;

	private static final String HANDLES = "handles";

	private static final String CHANGE = "change";

	private static final String RANGE = "range";

	private static final String MAX = "max";

	private static final String MIN = "min";

	private static final String STEPPING = "stepping";

	private static final String ANIMATE = "animate";

	private static final String START = "start";

	private static final String STOP = "stop";

	private static final String SLIDE = "slide";

	private transient final JSONObject json = new JSONObject();

	public SliderOptions setStepping(final Integer stepping)
	{
		json.put(STEPPING, stepping);
		return this;
	}

	public Integer getStepping()
	{
		return (Integer)json.get(STEPPING);
	}

	public SliderOptions setMin(final Integer min)
	{
		json.put(MIN, min);
		return this;
	}

	public Integer getMin()
	{
		return (Integer)json.get(MIN);
	}

	public SliderOptions setMax(final Integer max)
	{
		json.put(MAX, max);
		return this;
	}

	public Integer getMax()
	{
		return (Integer)json.get(MAX);
	}

	public SliderOptions setRange(final Boolean range)
	{
		json.put(RANGE, range);
		return this;
	}

	public Boolean getRange()
	{
		return (Boolean)json.get(RANGE);
	}

	public SliderOptions setAnimate(final Boolean animate)
	{
		json.put(ANIMATE, animate);
		return this;
	}

	public Boolean getAnimate()
	{
		return (Boolean)json.get(ANIMATE);
	}

	public SliderOptions setOnChange(final String body, final String... parametersNames)
	{
		putFunction(CHANGE, body, parametersNames);
		return this;
	}

	public SliderOptions setOnStart(final String body, final String... parametersNames)
	{
		putFunction(START, body, parametersNames);
		return this;
	}

	public SliderOptions setOnStop(final String body, final String... parametersNames)
	{
		putFunction(STOP, body, parametersNames);
		return this;
	}

	public SliderOptions setOnSlide(final String body, final String... parametersNames)
	{
		putFunction(SLIDE, body, parametersNames);
		return this;
	}

	public JSONFunction getOnChange()
	{

		return (JSONFunction)json.get(CHANGE);
	}

	public SliderOptions setHandles(final SliderHandleOptions... handlesSettings)
	{

		if (handlesSettings != null && handlesSettings.length > 0)
		{
			final JSONArray handlesArray = new JSONArray();

			for (final SliderHandleOptions sliderHandleSettings : handlesSettings)
			{
				handlesArray.add(sliderHandleSettings.getJSON());
			}

			json.put(HANDLES, handlesArray);
		}

		return this;
	}

	public String toJSON()
	{
		return json.toString();
	}

	private void putFunction(final String keyName, final String body, final String[] parametersNames)
	{

		final JSONFunction function;

		if (parametersNames != null && parametersNames.length > 0)
		{
			function = new JSONFunction(parametersNames, body);
		}
		else
		{
			function = new JSONFunction(body);
		}

		json.put(keyName, function);
	}

}
