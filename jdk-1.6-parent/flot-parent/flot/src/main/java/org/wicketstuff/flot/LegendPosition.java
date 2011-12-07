package org.wicketstuff.flot;

/**
 * @author Jason Novotny
 * @version $Id$
 */
public enum LegendPosition
{

	NORTHWEST("nw"), NORTHEAST("ne"), SOUTHWEST("sw"), SOUTHEAST("se");

	String pos;

	LegendPosition(String pos)
	{
		this.pos = pos;
	}

	public String getPosition()
	{
		return pos;
	}

	public static LegendPosition toLegendPosition(String pos)
	{
		for (LegendPosition p : values())
		{
			if (p.getPosition().equals(pos))
				return p;
		}
		throw new IllegalArgumentException("Unable to identify LegendPosition: " + pos);
	}
}
