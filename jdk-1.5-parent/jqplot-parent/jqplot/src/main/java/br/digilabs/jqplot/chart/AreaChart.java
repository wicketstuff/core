package br.digilabs.jqplot.chart;

import br.digilabs.jqplot.JqPlotResources;
import br.digilabs.jqplot.axis.XAxis;
import br.digilabs.jqplot.chart.data.ChartData;
import br.digilabs.jqplot.chart.elements.SeriesDefaults;

/**
 *
 * @author inaiat
 */
public class AreaChart<T extends ChartData<?>> extends DefaultChart<T> {

    public AreaChart() {
        getChartConfiguration().setStackSeries(true);
        getChartConfiguration().setShowMarker(false);
        SeriesDefaults defaults = new SeriesDefaults();
        defaults.setFill(true);
        setSeriesDefaults(defaults);        
        XAxis xAxis = createXAxis();
        xAxis.setRenderer(JqPlotResources.CategoryAxisRenderer);
    }    
    
}
