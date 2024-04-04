/*
 * Created on Dec 11, 2007
 */
package org.wicketstuff.googlecharts;

import java.awt.Color;

/**
 * @author Daniel Spiewak
 */
public interface ILinearStripesFill extends IChartFill
{

	public int getAngle();

	public Color[] getColors();

	public double[] getWidths();
}
