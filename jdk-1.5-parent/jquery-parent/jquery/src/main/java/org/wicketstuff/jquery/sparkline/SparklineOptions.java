package org.wicketstuff.jquery.sparkline;

import org.wicketstuff.jquery.Options;


public class SparklineOptions extends Options
{
	private static final long serialVersionUID = 1L;

	public enum TYPE
	{
		line, bar, tristate, discrete, bullet, pie, box
	};

	public SparklineOptions()
	{
		super();
	}

	public SparklineOptions(TYPE type)
	{
		super();
		set("type", type.name());
	}

	public SparklineOptions setType(TYPE type)
	{
		set("type", type.name());
		return this;
	}

	public SparklineOptions setWidth(String w)
	{
		set("width", w);
		return this;
	}

	public SparklineOptions setHeight(String h)
	{
		set("height", h);
		return this;
	}

	public SparklineOptions setLineColor(String v)
	{
		set("lineColor", v);
		return this;
	}

	public SparklineOptions setFillColor(String v)
	{
		set("fillColor", v);
		return this;
	}

	public SparklineOptions setChartRangeMin(int v)
	{
		set("chartRangeMin", v);
		return this;
	}

	public SparklineOptions setChartRangeMax(int v)
	{
		set("chartRangeMax", v);
		return this;
	}
}
