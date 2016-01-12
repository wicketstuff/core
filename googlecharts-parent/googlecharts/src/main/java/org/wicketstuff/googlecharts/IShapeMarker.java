/*
 * Created on Dec 11, 2007
 */
package org.wicketstuff.googlecharts;

import java.awt.Color;
import java.io.Serializable;

/**
 * @author Daniel Spiewak
 */
public interface IShapeMarker extends Serializable
{

	public MarkerType getType();

	public Color getColor();

	public int getIndex();

	public double getPoint();

	public int getSize();
}
