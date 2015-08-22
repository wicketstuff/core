/*
 * Created on Dec 11, 2007
 */
package org.wicketstuff.googlecharts;

import java.awt.Color;

/**
 * @author Daniel Spiewak
 */
public class RangeMarker implements IRangeMarker
{

	private static final long serialVersionUID = 885106818212539922L;
	private Color color;
	private double end = -1;
	private double start = -1;
	private RangeType type;

	public RangeMarker(RangeType type, Color color, double start, double end)
	{
		this.type = type;
		this.color = color;
		this.start = start;
		this.end = end;
	}

	public Color getColor()
	{
		return color;
	}

	public double getEnd()
	{
		return end;
	}

	public double getStart()
	{
		return start;
	}

	public RangeType getType()
	{
		return type;
	}

	public void setColor(Color color)
	{
		this.color = color;
	}

	public void setEnd(double end)
	{
		this.end = end;
	}

	public void setStart(double start)
	{
		this.start = start;
	}

	public void setType(RangeType type)
	{
		this.type = type;
	}
}
