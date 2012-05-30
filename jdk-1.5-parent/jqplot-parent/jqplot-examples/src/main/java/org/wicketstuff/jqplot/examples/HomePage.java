package org.wicketstuff.jqplot.examples;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.wicketstuff.jqplot.JqPlotChart;

import br.com.digilabs.jqplot.chart.BarChart;
import br.com.digilabs.jqplot.chart.LabeledLineChart;
import br.com.digilabs.jqplot.chart.LineChart;
import br.com.digilabs.jqplot.chart.LineSeriesChart;
import br.com.digilabs.jqplot.chart.PieChart;
import br.com.digilabs.jqplot.data.item.LabeledItem;
import br.com.digilabs.jqplot.data.item.LineSeriesItem;
import br.com.digilabs.jqplot.elements.GridPadding;
import br.com.digilabs.jqplot.elements.Legend;
import br.com.digilabs.jqplot.elements.LegendRenderer;
import br.com.digilabs.jqplot.elements.Location;
import br.com.digilabs.jqplot.elements.MarkerOptions;
import br.com.digilabs.jqplot.elements.RendererOptions;
import br.com.digilabs.jqplot.elements.Serie;
import br.com.digilabs.jqplot.elements.SeriesDefaults;
import br.com.digilabs.jqplot.elements.Trendline;

public class HomePage extends WebPage
{

	private static final long serialVersionUID = 1L;

	public HomePage(final PageParameters parameters)
	{
		add(new JqPlotChart("chart1", lineChart1()));

		add(new JqPlotChart("chart2", lineChart2()));

		add(new JqPlotChart("barChart1", barChart1()));

		add(new JqPlotChart("barChart2", barChart2()));

		add(new JqPlotChart("pieChart1", pieChart1()));

		add(new JqPlotChart("pieChart2", pieChart2()));

		add(new JqPlotChart("lineSeries1", lineSeries()));

		addTestLineChartWithTicks();

	}

	private PieChart<Number> pieChart2()
	{
		PieChart<Number> pieChart = new PieChart<Number>("Pizza Chart Custom Legend");
		pieChart.addValue("Drops", 10f);
		pieChart.addValue("Chocolate", 20f);
		pieChart.addValue("Jujuba", 5f);
		pieChart.getSeriesDefaults().getRendererOptions().setPadding(8);
		pieChart.getSeriesDefaults().getRendererOptions().setShowDataLabels(true);
		pieChart.setGridPadding(new GridPadding(0, 38, 0, 0));
		pieChart.getSeriesDefaults().setTrendline(new Trendline().setShow(false));

		Legend legend = new Legend();
		legend.setShow(true);
		legend.setPlacment("outsite");
		legend.setRendererOptions(new LegendRenderer().setNumberRows(1));
		legend.setLocation(Location.s);
		legend.setMarginTop("15px");
		pieChart.setLegend(legend);

		return pieChart;

	}

	private void addTestLineChartWithTicks()
	{
		List<LineSeriesItem<Double, Double>> cosPoints = new ArrayList<LineSeriesItem<Double, Double>>();
		for (double i = 0; i < 2 * Math.PI; i++)
		{
			cosPoints.add(new LineSeriesItem<Double, Double>(i, Math.cos(i)));
		}

		List<LineSeriesItem<Double, Double>> sinPoints = new ArrayList<LineSeriesItem<Double, Double>>();
		for (double i = 0; i < 2 * Math.PI; i += 0.4)
		{
			sinPoints.add(new LineSeriesItem<Double, Double>(i, 2 * Math.sin(i - .8)));
		}

		List<LineSeriesItem<Double, Double>> powPoints1 = new ArrayList<LineSeriesItem<Double, Double>>();
		for (double i = 0; i < 2 * Math.PI; i++)
		{
			powPoints1.add(new LineSeriesItem<Double, Double>(i, 2.5 + Math.pow(i / 4, 2)));
		}

		List<LineSeriesItem<Double, Double>> powPoints2 = new ArrayList<LineSeriesItem<Double, Double>>();
		for (double i = 0; i < 2 * Math.PI; i++)
		{
			powPoints2.add(new LineSeriesItem<Double, Double>(i, -2.5 - Math.pow(i / 4, 2)));
		}

		LineSeriesChart<Double, Double> lineChart = new LineSeriesChart<Double, Double>(
			"Line Style Options");
		lineChart.addValue(cosPoints);
		lineChart.addValue(sinPoints);
		lineChart.addValue(powPoints1);
		lineChart.addValue(powPoints2);

		SeriesDefaults seriesDefaults = new SeriesDefaults();
		seriesDefaults.rendererOptions(new RendererOptions().smooth(true));
		lineChart.setSeriesDefaults(seriesDefaults);

		lineChart.addSerie(new Serie().lineWidth(2).markerOptions(
			new MarkerOptions().style("diamond")));
		lineChart.addSerie(new Serie().showLine(false).markerOptions(
			new MarkerOptions().size(7f).style("x")));
		lineChart.addSerie(new Serie().markerOptions(new MarkerOptions().style("circle")));
		lineChart.addSerie(new Serie().lineWidth(5).markerOptions(
			new MarkerOptions().style("filledSquare").size(10f)));

		add(new JqPlotChart("test", lineChart));

	}

	private LineSeriesChart<Double, Double> lineSeries()
	{
		List<LineSeriesItem<Double, Double>> cosPoints = new ArrayList<LineSeriesItem<Double, Double>>();
		for (double i = 0; i < 2 * Math.PI; i++)
		{
			cosPoints.add(new LineSeriesItem<Double, Double>(i, Math.cos(i)));
		}

		List<LineSeriesItem<Double, Double>> sinPoints = new ArrayList<LineSeriesItem<Double, Double>>();
		for (double i = 0; i < 2 * Math.PI; i += 0.4)
		{
			sinPoints.add(new LineSeriesItem<Double, Double>(i, 2 * Math.sin(i - .8)));
		}

		List<LineSeriesItem<Double, Double>> powPoints1 = new ArrayList<LineSeriesItem<Double, Double>>();
		for (double i = 0; i < 2 * Math.PI; i++)
		{
			powPoints1.add(new LineSeriesItem<Double, Double>(i, 2.5 + Math.pow(i / 4, 2)));
		}

		List<LineSeriesItem<Double, Double>> powPoints2 = new ArrayList<LineSeriesItem<Double, Double>>();
		for (double i = 0; i < 2 * Math.PI; i++)
		{
			powPoints2.add(new LineSeriesItem<Double, Double>(i, -2.5 - Math.pow(i / 4, 2)));
		}

		LineSeriesChart<Double, Double> lineChart = new LineSeriesChart<Double, Double>(
			"Line Style Options");
		lineChart.addValue(cosPoints);
		lineChart.addValue(sinPoints);
		lineChart.addValue(powPoints1);
		lineChart.addValue(powPoints2);

		SeriesDefaults seriesDefaults = new SeriesDefaults();
		seriesDefaults.rendererOptions(new RendererOptions().smooth(true));
		lineChart.setSeriesDefaults(seriesDefaults);

		lineChart.addSerie(new Serie().lineWidth(2).markerOptions(
			new MarkerOptions().style("diamond")));
		lineChart.addSerie(new Serie().showLine(false).markerOptions(
			new MarkerOptions().size(7f).style("x")));
		lineChart.addSerie(new Serie().markerOptions(new MarkerOptions().style("circle")));
		lineChart.addSerie(new Serie().lineWidth(5).markerOptions(
			new MarkerOptions().style("filledSquare").size(10f)));

		return lineChart;
	}

	private BarChart<Integer> barChart2()
	{
		BarChart<Integer> barChart2 = new BarChart<Integer>("Bar Chart with Custom Colors");
		barChart2.setTicks("Nissan", "Porche", "Acura", "Aston Martin", "Rolls Royce");
		barChart2.addValues(4, 6, 2, 5, 6);
		barChart2.addSeriesColors("#85802b", "#00749F", "#73C774", "#C7754C", "#17BDB8");
		// Set varyBarColor to true to use the custom colors on the bars.
		barChart2.getSeriesDefaults().getRendererOptions().setVaryBarColor(true);
		return barChart2;
	}

	private BarChart<Integer> barChart1()
	{
		BarChart<Integer> barChart1 = new BarChart<Integer>("Bar Chart");
		Legend legend = new Legend();
		legend.setShow(true);
		legend.setPlacment("outsideGrid");
		barChart1.setLegend(legend);
		barChart1.setPadMin(1.05f);
		barChart1.setStackSeries(true);
		barChart1.setCaptureRightClick(true);
		barChart1.setHighlightMouseDown(true);
		barChart1.setBarMargin(30);

		barChart1.addValue(Arrays.<Integer> asList(200, 600, 700, 1000));
		barChart1.addValue(Arrays.<Integer> asList(200, 600, 700, 1000));
		barChart1.addValue(Arrays.<Integer> asList(200, 600, 700, 1000));
		return barChart1;
	}

	private LabeledLineChart<Integer> lineChart2()
	{
		LabeledLineChart<Integer> chart2 = new LabeledLineChart<Integer>("Labeled Line Charts",
			"Incliment Occurrance", "Incliment Factor", 15);
		chart2.addValue(new LabeledItem<Integer>("1/1/2008", 42));
		chart2.addValue(new LabeledItem<Integer>("2/14/2008", 56));
		chart2.addValue(new LabeledItem<Integer>("3/7/2008", 39));
		chart2.addValue(new LabeledItem<Integer>("4/22/2008", 81));
		return chart2;
	}

	private LineChart<Integer> lineChart1()
	{
		LineChart<Integer> lineChart = new LineChart<Integer>("Line Charts");
		lineChart.addValue(1);
		lineChart.addValue(2);
		lineChart.addValue(3);
		lineChart.addValue(5);
		return lineChart;
	}

	public PieChart<Number> pieChart1()
	{
		PieChart<Number> pieChart1 = new PieChart<Number>("Pie Charts");
		pieChart1.addValue("Banana", 10f);
		pieChart1.addValue("Chocolate", 20f);
		pieChart1.addValue("Peperone", 5f);
		return pieChart1;
	}

}
