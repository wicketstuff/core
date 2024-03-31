/*
 * Created on Dec 11, 2007
 */
package org.wicketstuff.googlecharts.examples;

import java.awt.Color;
import java.awt.Dimension;

import org.apache.wicket.markup.html.WebPage;
import org.wicketstuff.googlecharts.AbstractChartData;
import org.wicketstuff.googlecharts.Chart;
import org.wicketstuff.googlecharts.ChartAxis;
import org.wicketstuff.googlecharts.ChartAxisType;
import org.wicketstuff.googlecharts.ChartDataEncoding;
import org.wicketstuff.googlecharts.ChartProvider;
import org.wicketstuff.googlecharts.ChartType;
import org.wicketstuff.googlecharts.IChartData;
import org.wicketstuff.googlecharts.LineStyle;
import org.wicketstuff.googlecharts.LinearGradientFill;
import org.wicketstuff.googlecharts.MarkerType;
import org.wicketstuff.googlecharts.ShapeMarker;
import org.wicketstuff.googlecharts.SolidFill;
import org.wicketstuff.googlecharts.TextValueMarker;
import org.wicketstuff.googlecharts.TextValueMarkerType;

/**
 * @author Daniel Spiewak
 */
public class Home extends WebPage
{

	private static final long serialVersionUID = 1L;

	public Home()
	{
		IChartData data = new AbstractChartData()
		{

			private static final long serialVersionUID = 1L;

			public double[][] getData()
			{
				return new double[][] { { 34, 22 } };
			}
		};

		ChartProvider provider = new ChartProvider(new Dimension(250, 100), ChartType.PIE_3D, data);
		provider.setPieLabels(new String[] { "Hello", "World" });

		add(new Chart("helloWorld", provider));

		data = new AbstractChartData()
		{

			private static final long serialVersionUID = 1L;

			public double[][] getData()
			{
				return new double[][] { { 34, 30, 38, 38, 41, 22, 41, 44, 38, 29 } };
			}
		};

		provider = new ChartProvider(new Dimension(200, 125), ChartType.LINE, data);

		ChartAxis axis = new ChartAxis(ChartAxisType.BOTTOM);
		axis.setLabels(new String[] { "Mar", "Apr", "May", "June", "July" });
		provider.addAxis(axis);

		axis = new ChartAxis(ChartAxisType.LEFT);
		axis.setLabels(new String[] { null, "50 Kb" });
		provider.addAxis(axis);

		add(new Chart("lineHelloWorld", provider));

		data = new AbstractChartData(ChartDataEncoding.TEXT, 100)
		{

			private static final long serialVersionUID = 1L;

			public double[][] getData()
			{
				return new double[][] { { 0, 30, 60, 70, 90, 95, 100 },
						{ 20, 30, 40, 50, 60, 70, 80 }, { 10, 30, 40, 45, 52 },
						{ 100, 90, 40, 20, 10 }, null, { 5, 33, 50, 55, 7 } };
			}
		};

		provider = new ChartProvider(new Dimension(200, 125), ChartType.LINE_XY, data);
		provider.setColors(new Color[] { Color.decode("#3072F3"), Color.RED,
				Color.decode("#00aaaa") });
		provider.setLineStyles(new LineStyle[] { new LineStyle(2, 4, 1) });

		provider.addShapeMarker(new ShapeMarker(MarkerType.SQUARE, Color.RED, 0, -1, 5));
		provider.addShapeMarker(new ShapeMarker(MarkerType.SQUARE, Color.BLUE, 1, -1, 5));
		provider.addShapeMarker(new ShapeMarker(MarkerType.SQUARE, Color.decode("#00aa00"), 2, -1,
			5));
		provider.addTextValueMarker(new TextValueMarker(TextValueMarkerType.FORMATTING_STRING, "*f0*", 0, Color.GREEN, -1, 11));

		add(new Chart("lineChart", provider));

		data = new AbstractChartData()
		{

			private static final long serialVersionUID = 1L;

			public double[][] getData()
			{
				return new double[][] { { 7, 4, 11, 11 }, { 22, 14, 17, 11 } };
			}
		};

		provider = new ChartProvider(new Dimension(200, 125), ChartType.BAR_HORIZONTAL_SET, data);
		provider.setColors(new Color[] { Color.RED, Color.decode("#00aa00") });

		add(new Chart("horizontalBarSet", provider));

		data = new AbstractChartData()
		{

			private static final long serialVersionUID = 1L;

			public double[][] getData()
			{
				return new double[][] { { 34, 30, 38, 38, 41 } };
			}
		};

		provider = new ChartProvider(new Dimension(200, 125), ChartType.BAR_HORIZONTAL_SET, data);

		add(new Chart("horizontalScaledBar", provider));

		data = new AbstractChartData(ChartDataEncoding.TEXT, 100)
		{

			private static final long serialVersionUID = 1L;

			public double[][] getData()
			{
				return new double[][] { { 100, 80, 60, 30, 30, 30, 10 } };
			}
		};

		provider = new ChartProvider(new Dimension(200, 100), ChartType.VENN, data);

		add(new Chart("venn", provider));

		data = new AbstractChartData()
		{

			private static final long serialVersionUID = 1L;

			public double[][] getData()
			{
				return new double[][] { { 42, 43, 41, 38, 31, 24, 14, 13, 14, 12, 4, 1, 0, 10, 15,
						14, 16, 21, 19, 23, 25, 30, 31, 29, 27, 20, 29, 33, 28, 42, 44, 43, 50, 47,
						47, 50, 55, 61, 55, 52, 46, 42, 41, 41, 40, 37, 31, 33, 33, 36, 42 } };
			}
		};

		provider = new ChartProvider(new Dimension(200, 125), ChartType.LINE, data);
		provider.setColors(new Color[] { Color.RED });
		provider.setLineStyles(new LineStyle[] { new LineStyle(4, 3, 0) });

		provider.setBackgroundFill(new SolidFill(Color.decode("#EFEFEF")));
		provider.setChartFill(new LinearGradientFill(45, new Color[] { Color.WHITE,
				Color.decode("#76A4FB") }, new double[] { 0, 0.75 }));

		axis = new ChartAxis(ChartAxisType.BOTTOM);
		axis.setLabels(new String[] { "1", "2", "3", "4", "5" });
		provider.addAxis(axis);

		axis = new ChartAxis(ChartAxisType.LEFT);
		axis.setLabels(new String[] { "0", "50", "100" });
		provider.addAxis(axis);

		add(new Chart("backgrounds", provider));

		data = new AbstractChartData(ChartDataEncoding.TEXT, 100)
		{

			private static final long serialVersionUID = 1L;

			public double[][] getData()
			{
				return new double[][] { { 12, 4, 4, 8, 24, 28, 24, 28, 12, 12, 16 },
						{ 28, 28, 96, 40, 16, 32, 12, 24, 100, 44, 16, 8 },
						{ 36, 40, 40, 24, 56, 72, 12, 8, 4, 48, 40, 12 } };
			}
		};

		provider = new ChartProvider(new Dimension(200, 125), ChartType.LINE, data);
		provider.setColors(new Color[] { Color.GREEN, Color.BLUE, Color.RED });
		provider.setLegend(new String[] { "2005", "2006", "2007" });
		provider.setLineStyles(new LineStyle[] { new LineStyle(3, 2, 0), new LineStyle(3, 2, 0),
				new LineStyle(3, 2, 0) });
		provider.setBackgroundFill(new SolidFill(new Color(0, 0, 0, 0)));

		axis = new ChartAxis(ChartAxisType.BOTTOM);
		axis.setLabels(new String[] { "J", "F", "M", "A", "M", "J", "J", "A", "S", "O", "N", "D" });
		provider.addAxis(axis);

		axis = new ChartAxis(ChartAxisType.LEFT);
		axis.setLabels(new String[] { null, "5", "10", "15", "20", "25" });
		provider.addAxis(axis);

		add(new Chart("alex", provider));
	}
}
