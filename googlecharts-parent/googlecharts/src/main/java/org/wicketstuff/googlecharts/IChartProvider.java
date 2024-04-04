/*
 * Created on Dec 11, 2007
 */
package org.wicketstuff.googlecharts;

import java.awt.Color;
import java.awt.Dimension;
import java.io.Serializable;

/**
 * @author Daniel Spiewak
 */
public interface IChartProvider extends Serializable
{

	public Dimension getSize();

	public IChartData getData();

	public ChartType getType();

	public int getBarWidth();

	public int getBarGroupSpacing();

	public String getTitle();

	public String[] getLegend();

	public Color[] getColors();

	public IChartFill getChartFill();

	public IChartFill getBackgroundFill();

	public String[] getPieLabels();

	public IChartAxis[] getAxes();

	public ILineStyle[] getLineStyles();

	public IChartGrid getGrid();

	public IShapeMarker[] getShapeMarkers();

	public IRangeMarker[] getRangeMarkers();

	public IFillArea[] getFillAreas();

	public ITextValueMarker[] getTextValueMarkers();
}
