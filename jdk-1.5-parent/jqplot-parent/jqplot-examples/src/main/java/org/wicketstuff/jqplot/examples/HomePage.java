package org.wicketstuff.jqplot.examples;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.WebPage;
import org.wicketstuff.jqplot.JqPlotChart;

import br.com.digilabs.jqplot.chart.AreaChart;
import br.com.digilabs.jqplot.chart.DonutChart;
import br.com.digilabs.jqplot.chart.LineChart;
import br.com.digilabs.jqplot.chart.PieChart;
import br.com.digilabs.jqplot.chart.data.AreaFillData;
import br.com.digilabs.jqplot.chart.data.LinedData;
import br.com.digilabs.jqplot.chart.data.PieChartData;

public class HomePage extends WebPage {

    private static final long serialVersionUID = 1L;

    public HomePage(final PageParameters parameters) {
        LineChart<LinedData<Integer>> lineChart = new LineChart<LinedData<Integer>>();
        LinedData<Integer> linedData = new LinedData<Integer>(1, 2, 3, 4, 5, 6);
        lineChart.setChartData(linedData);
        add(new JqPlotChart("line1", lineChart));

        List<Integer> l2 = new ArrayList<Integer>(Arrays.asList(11, 9, 5, 12, 14));
        List<Integer> l3 = new ArrayList<Integer>(Arrays.asList(4, 8, 5, 3, 6));
        List<Integer> l4 = new ArrayList<Integer>(Arrays.asList(12, 6, 13, 11, 2));        
        @SuppressWarnings("unchecked")
		AreaFillData<Integer> area1 = new AreaFillData<Integer>(l2, l3, l4);
        AreaChart<AreaFillData<Integer>> areaChart = new AreaChart<AreaFillData<Integer>>();
        areaChart.setChartData(area1);
        areaChart.getAxes().getXaxis().setTicks(new String[]{"Seg", "Ter", "Qua", "Qui", "Sex"});
        add(new JqPlotChart("area1", areaChart));
        
        PieChart<PieChartData<Object>> pieChart = new PieChart<PieChartData<Object>>();        
        PieChartData<Object> pieData = new PieChartData<Object>();
        Collection<Object> c1 = new ArrayList<Object>();
        c1.add(Arrays.<Object>asList("A", 1));
        c1.add(Arrays.<Object>asList("B", 2.1));
        c1.add(Arrays.<Object>asList("C", 4.4));
        pieData.addValue(c1);
        pieChart.setChartData(pieData);
        add(new JqPlotChart("pie1", pieChart));

        PieChartData<Object> donutData = new PieChartData<Object>();
        DonutChart<PieChartData<Object>> donutChart = new DonutChart<PieChartData<Object>>();        
        Collection<Object> d1 = new ArrayList<Object>();
        d1.add(Arrays.<Object>asList("A", 1));
        d1.add(Arrays.<Object>asList("B", 2.1));
        d1.add(Arrays.<Object>asList("C", 4.4));
        Collection<Object> d2 = new ArrayList<Object>();
        d2.add(Arrays.<Object>asList("A", 1));
        d2.add(Arrays.<Object>asList("B", 2.1));
        d2.add(Arrays.<Object>asList("C", 4.4));
        donutData.addValue(d1);
        donutData.addValue(d2);
        
        donutChart.setChartData(donutData);
        add(new JqPlotChart("donut1", donutChart));
    }
}
