package org.wicketstuff.jqplot.examples;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.wicketstuff.jqplot.JqPlotChart;
import org.wicketstuff.jqplot.lib.chart.AreaChart;
import org.wicketstuff.jqplot.lib.chart.BarChart;
import org.wicketstuff.jqplot.lib.chart.LabeledLineChart;
import org.wicketstuff.jqplot.lib.chart.LineChart;
import org.wicketstuff.jqplot.lib.chart.LineSeriesChart;
import org.wicketstuff.jqplot.lib.chart.PieChart;
import org.wicketstuff.jqplot.lib.data.item.LabeledItem;
import org.wicketstuff.jqplot.lib.data.item.LineSeriesItem;
import org.wicketstuff.jqplot.lib.elements.GridPadding;
import org.wicketstuff.jqplot.lib.elements.Legend;
import org.wicketstuff.jqplot.lib.elements.LegendRenderer;
import org.wicketstuff.jqplot.lib.elements.Location;
import org.wicketstuff.jqplot.lib.elements.MarkerOptions;
import org.wicketstuff.jqplot.lib.elements.RendererOptions;
import org.wicketstuff.jqplot.lib.elements.Serie;
import org.wicketstuff.jqplot.lib.elements.SeriesDefaults;
import org.wicketstuff.jqplot.lib.elements.TickOptions;
import org.wicketstuff.jqplot.lib.elements.Trendline;

public class HomePage extends WebPage {

    private static final long serialVersionUID = 1L;

    public HomePage(final PageParameters parameters) {
        add(new JqPlotChart("chart1", lineChart1()));
        add(new JqPlotChart("chart2", lineChart2()));
        add(new JqPlotChart("barChart1", barChart1()));
        add(new JqPlotChart("barChart2", barChart2()));
        add(new JqPlotChart("pieChart1", pieChart1()));
        add(new JqPlotChart("pieChart2", pieChart2()));
        add(new JqPlotChart("lineSeries1", lineSeries()));
        add(new JqPlotChart("areaChart1", areaChart1()));
        add(new JqPlotChart("areaChart2", areaChart2()));

        addChartWithImage();

        addTestLineChartWithTicks();

    }

    private void addChartWithImage() {
        PieChart<Number> pieChart = new PieChart<>("Pizza Chart Custom Legend");
        pieChart.addValue("Drops", 10f);
        pieChart.addValue("Chocolate", 20f);
        pieChart.addValue("Jujuba", 5f);
        pieChart.getSeriesDefaults().getRendererOptions().setPadding(8);
        pieChart.getSeriesDefaults().getRendererOptions().setShowDataLabels(true);
        pieChart.setGridPadding(new GridPadding(0, 38, 0, 0));
        pieChart.getSeriesDefaults().setTrendline(new Trendline().setShow(false));

        Legend legend = new Legend();
        legend.setShow(true);
        legend.setPlacement("outsite");
        legend.setRendererOptions(new LegendRenderer().setNumberRows(1));
        legend.setLocation(Location.s);
        legend.setMarginTop("15px");
        pieChart.setLegend(legend);

        final JqPlotChart jqPlotChart = new JqPlotChart("areaChartWithImage", pieChart);
        final WebMarkupContainer jqPlotImage = new WebMarkupContainer("image");
        jqPlotImage.setOutputMarkupId(true);

        AjaxLink<Void> ajaxLink = new AjaxLink<Void>("plotImage") {
            private static final long serialVersionUID = 1L;

            @Override
            public void onClick(AjaxRequestTarget target) {
                StringBuilder jsBuilder = new StringBuilder();
                jsBuilder//.append("$(document).ready(function(){\r\n")
                        .append("var imgData = $('#").append(jqPlotChart.getMarkupId())
                        .append("').jqplotToImageStr({});\r\n")
                        .append("var imgElem = $('<img/>').attr('src',imgData);\r\n")
                        .append("$('#").append(jqPlotImage.getMarkupId())
                        .append("').append(imgElem);\r\n");
                target.appendJavaScript(jsBuilder);
            }
        };

        add(ajaxLink);
        add(jqPlotChart);
        add(jqPlotImage);

    }

    public AreaChart<Integer> areaChart1() {

        AreaChart<Integer> areaChart = new AreaChart<>("Area chart Example");
        areaChart.addValue(Arrays.<Integer>asList(11, 9, 5, 12, 14));
        areaChart.addValue(Arrays.<Integer>asList(4, 8, 5, 3, 6));
        areaChart.addValue(Arrays.<Integer>asList(12, 6, 13, 11, 2));
        areaChart
                .getChartConfiguration()
                .xAxisInstance()
                .setTicks(new String[]{"Mon", "Tue", "Wed", "Thr", "Fri"});


        return areaChart;
    }

    public AreaChart<Integer> areaChart2() {

        AreaChart<Integer> areaChart = new AreaChart<>("Area chart Example");
        areaChart.addValue(Arrays.<Integer>asList(11, 9, 5, 12, 14, 8, 7, 9, 6, 11, 9, 3, 4));
        areaChart.addValue(Arrays.<Integer>asList(4, 8, 5, 3, 6, 5, 3, 2, 6, 7, 4, 3, 2));
        areaChart.addValue(Arrays.<Integer>asList(12, 6, 13, 11, 2, 3, 4, 2, 1, 5, 7, 4, 8));
        areaChart
                .getChartConfiguration()
                .xAxisInstance()
                .setTicks(new String[]{"Dec 10", "Jan 11", "Feb 11", "Mar 11", "Apr 11", "May 11", "Jun 11", "Jul 11", "Aug 11", "Sep 11", "Oct 11", "Nov 11", "Dec 11"});

        Collection<Serie> series = new ArrayList<>();
        series.add(new Serie("Beans"));
        series.add(new Serie("Orange"));
        series.add(new Serie("Crackers"));
        areaChart.setSeries(series);

        areaChart.getSeriesDefaults().setFill(true);
        RendererOptions options = new RendererOptions();
        options.setHighlightMouseDown(true);
        areaChart.getSeriesDefaults().setRendererOptions(options);

        Legend legend = new Legend();
        legend.setShow(true);
        legend.setPlacement("outsideGrid");
        areaChart.setLegend(legend);

        TickOptions tickOptions = new TickOptions();
        tickOptions.setAngle(-90);
        areaChart.getAxes().getXaxis().setTickOptions(tickOptions);
        return areaChart;
    }

    private static PieChart<Number> pieChart2() {
        PieChart<Number> pieChart = new PieChart<>("Pizza Chart Custom Legend");
        pieChart.addValue("Drops", 10f);
        pieChart.addValue("Chocolate", 20f);
        pieChart.addValue("Jujuba", 5f);
        pieChart.getSeriesDefaults().getRendererOptions().setPadding(8);
        pieChart.getSeriesDefaults().getRendererOptions().setShowDataLabels(true);
        pieChart.setGridPadding(new GridPadding(0, 38, 0, 0));
        pieChart.getSeriesDefaults().setTrendline(new Trendline().setShow(false));

        Legend legend = new Legend();
        legend.setShow(true);
        legend.setPlacement("outsite");
        legend.setRendererOptions(new LegendRenderer().setNumberRows(1));
        legend.setLocation(Location.s);
        legend.setMarginTop("15px");
        pieChart.setLegend(legend);

        return pieChart;

    }

    private void addTestLineChartWithTicks() {
        List<LineSeriesItem<Double, Double>> cosPoints = new ArrayList<>();
        for (double i = 0; i < 2 * Math.PI; i++) {
            cosPoints.add(new LineSeriesItem<>(i, Math.cos(i)));
        }

        List<LineSeriesItem<Double, Double>> sinPoints = new ArrayList<>();
        for (double i = 0; i < 2 * Math.PI; i += 0.4) {
            sinPoints.add(new LineSeriesItem<>(i, 2 * Math.sin(i - .8)));
        }

        List<LineSeriesItem<Double, Double>> powPoints1 = new ArrayList<>();
        for (double i = 0; i < 2 * Math.PI; i++) {
            powPoints1.add(new LineSeriesItem<>(i, 2.5 + Math.pow(i / 4, 2)));
        }

        List<LineSeriesItem<Double, Double>> powPoints2 = new ArrayList<>();
        for (double i = 0; i < 2 * Math.PI; i++) {
            powPoints2.add(new LineSeriesItem<>(i, -2.5 - Math.pow(i / 4, 2)));
        }

        LineSeriesChart<Double, Double> lineChart = new LineSeriesChart<>(
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

    private static LineSeriesChart<Double, Double> lineSeries() {
        List<LineSeriesItem<Double, Double>> cosPoints = new ArrayList<>();
        for (double i = 0; i < 2 * Math.PI; i++) {
            cosPoints.add(new LineSeriesItem<>(i, Math.cos(i)));
        }

        List<LineSeriesItem<Double, Double>> sinPoints = new ArrayList<>();
        for (double i = 0; i < 2 * Math.PI; i += 0.4) {
            sinPoints.add(new LineSeriesItem<>(i, 2 * Math.sin(i - .8)));
        }

        List<LineSeriesItem<Double, Double>> powPoints1 = new ArrayList<>();
        for (double i = 0; i < 2 * Math.PI; i++) {
            powPoints1.add(new LineSeriesItem<>(i, 2.5 + Math.pow(i / 4, 2)));
        }

        List<LineSeriesItem<Double, Double>> powPoints2 = new ArrayList<>();
        for (double i = 0; i < 2 * Math.PI; i++) {
            powPoints2.add(new LineSeriesItem<>(i, -2.5 - Math.pow(i / 4, 2)));
        }

        LineSeriesChart<Double, Double> lineChart = new LineSeriesChart<>(
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

    private static BarChart<Integer> barChart2() {
        BarChart<Integer> barChart2 = new BarChart<>("Bar Chart with Custom Colors");
        barChart2
                .getChartConfiguration()
                .xAxisInstance()
                .setTicks("Nissan", "Porche", "Acura", "Aston Martin", "Rolls Royce");
        barChart2.addValues(4, 6, 2, 5, 6);
        barChart2.addSeriesColors("#85802b", "#00749F", "#73C774", "#C7754C", "#17BDB8");
        // Set varyBarColor to true to use the custom colors on the bars.
        barChart2.getSeriesDefaults().rendererOptionsInstance().setVaryBarColor(true);
        return barChart2;
    }

    private static BarChart<Integer> barChart1() {
        BarChart<Integer> barChart1 = new BarChart<>("Bar Chart");
        Legend legend = new Legend();
        legend.setShow(true);
        legend.setPlacement("outsideGrid");
        barChart1.setLegend(legend);
        barChart1
                .getChartConfiguration()
                .yAxisInstance()
                .setPadMin(1.05f);
        barChart1.setStackSeries(true);
        barChart1.setCaptureRightClick(true);
        barChart1.setHighlightMouseDown(true);
        barChart1.setBarMargin(30);

        barChart1.addValue(Arrays.<Integer>asList(200, 600, 700, 1000));
        barChart1.addValue(Arrays.<Integer>asList(200, 600, 700, 1000));
        barChart1.addValue(Arrays.<Integer>asList(200, 600, 700, 1000));
        return barChart1;
    }

    private static LabeledLineChart<Integer> lineChart2() {
        LabeledLineChart<Integer> chart2 = new LabeledLineChart<>("Labeled Line Charts",
                "Incliment Occurrance", "Incliment Factor", 15);
        chart2.addValue(new LabeledItem<>("1/1/2008", 42));
        chart2.addValue(new LabeledItem<>("2/14/2008", 56));
        chart2.addValue(new LabeledItem<>("3/7/2008", 39));
        chart2.addValue(new LabeledItem<>("4/22/2008", 81));
        return chart2;
    }

    private static LineChart<Integer> lineChart1() {
        LineChart<Integer> lineChart = new LineChart<>("Line Charts");
        lineChart.addValue(1);
        lineChart.addValue(2);
        lineChart.addValue(3);
        lineChart.addValue(5);
        return lineChart;
    }

    public PieChart<Number> pieChart1() {
        PieChart<Number> pieChart1 = new PieChart<>("Pie Charts");
        pieChart1.addValue("Banana", 10f);
        pieChart1.addValue("Chocolate", 20f);
        pieChart1.addValue("Peperone", 5f);
        return pieChart1;
    }
}
