/*
 * Created on Dec 11, 2007
 */
package org.wicketstuff.googlecharts;

import java.io.Serializable;

/**
 * @author Daniel Spiewak
 */
public class Range implements Serializable
{

	private static final long serialVersionUID = 5280524789527071838L;
	private double start, end;

	public Range(double start, double end)
	{
		this.start = start;
		this.end = end;
	}

	public double getStart()
	{
		return start;
	}

	public void setStart(double start)
	{
		this.start = start;
	}

	public double getEnd()
	{
		return end;
	}

	public void setEnd(double end)
	{
		this.end = end;
	}
}
