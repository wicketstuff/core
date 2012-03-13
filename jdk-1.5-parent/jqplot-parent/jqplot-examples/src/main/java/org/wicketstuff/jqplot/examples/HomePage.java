package org.wicketstuff.jqplot.examples;

import java.util.Arrays;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.wicketstuff.jqplot.JqPlotChart;

import br.com.digilabs.jqplot.chart.BarChart;
import br.com.digilabs.jqplot.chart.LabeledLineChart;
import br.com.digilabs.jqplot.chart.LineChart;
import br.com.digilabs.jqplot.chart.PieChart;
import br.com.digilabs.jqplot.data.item.LabeledItem;
import br.com.digilabs.jqplot.elements.Legend;

public class HomePage extends WebPage
{

	private static final long serialVersionUID = 1L;

	public HomePage(final PageParameters parameters)
	{
		add(new JqPlotChart("chart1", lineChart1()));

		add(new JqPlotChart("chart2", lineChart2()));

		add(new JqPlotChart("barChart1", barChart1()));

		add(new JqPlotChart("barChart2", barChart2()));
		
		add(new JqPlotChart("pieChart", pieChart1()));

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
	
	public PieChart<Number> pieChart1() {
		PieChart<Number> pieChart1 = new PieChart<Number>("Pie Charts");
		pieChart1.addValue("Banana", 10f);
		pieChart1.addValue("Chocolate", 20f);
		pieChart1.addValue("Peperone", 5f);
		return pieChart1;
	}
}
