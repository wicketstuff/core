
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.digilabs.jqplot.chart;

import br.digilabs.jqplot.JqPlotResources;
import br.digilabs.jqplot.chart.data.ChartData;
import br.digilabs.jqplot.chart.elements.Legend;
import br.digilabs.jqplot.chart.elements.RendererOptions;
import br.digilabs.jqplot.chart.elements.SeriesDefaults;
import br.digilabs.jqplot.metadata.JqPlotPlugin;

/**
 *
 * @author bernardo.moura
 */
@JqPlotPlugin(values = {JqPlotResources.DonutRenderer})
public class DonutChart<T extends ChartData<?>> extends DefaultChart<T> {

    public DonutChart() {
        getChartConfiguration().setLegend(new Legend(true, "e"));
        getChartConfiguration().setSeriesDefaults(new SeriesDefaults());
        getChartConfiguration().getSeriesDefaults().setRenderer(JqPlotResources.DonutRenderer);
        getChartConfiguration().getSeriesDefaults().setRendererOptions(new RendererOptions());
        getChartConfiguration().getSeriesDefaults().getRendererOptions().setSliceMargin(4);
        getChartConfiguration().getSeriesDefaults().getRendererOptions().setDataLabels("value");
        getChartConfiguration().getSeriesDefaults().getRendererOptions().setShowDataLabels(true);
    }
}
