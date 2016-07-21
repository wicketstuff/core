/*
 * Created on Dec 11, 2007
 */
package org.wicketstuff.googlecharts;

import java.awt.Color;
import java.io.Serializable;

/**
 * @author Daniel Spiewak
 */
public interface IRangeMarker extends Serializable
{

	public RangeType getType();

	public Color getColor();

	public double getStart();

	public double getEnd();
}
