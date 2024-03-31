/*
 * Created on Dec 11, 2007
 */
package org.wicketstuff.googlecharts;

import java.awt.Color;
import java.io.Serializable;

/**
 * @author Daniel Spiewak
 */
public interface IFillArea extends Serializable
{

	public AreaFillType getType();

	public Color getColor();

	public int getStartIndex();

	// unnecessary if BOTTOM
	public int getEndIndex();
}
