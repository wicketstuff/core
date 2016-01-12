/*
 * Created on Dec 11, 2007
 */
package org.wicketstuff.googlecharts;

/**
 * @author Daniel Spiewak
 */
public enum AreaFillType
{

	BETWEEN("b"), DOWN("B");
	private final String rendering;

	private AreaFillType(String rendering)
	{
		this.rendering = rendering;
	}

	public String getRendering()
	{
		return rendering;
	}
}
