/*
 * Created on Dec 11, 2007
 */
package org.wicketstuff.googlecharts;

import java.awt.Color;

/**
 * @author Daniel Spiewak
 */
public class SolidFill implements ISolidFill
{

	private static final long serialVersionUID = -8021753397593009779L;
	private Color color;

	public SolidFill(Color color)
	{
		this.color = color;
	}

	public Color getColor()
	{
		return color;
	}

	public void setColor(Color color)
	{
		this.color = color;
	}
}
