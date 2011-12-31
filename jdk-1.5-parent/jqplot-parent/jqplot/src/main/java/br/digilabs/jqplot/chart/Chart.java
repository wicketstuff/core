package br.digilabs.jqplot.chart;

import br.digilabs.jqplot.chart.config.ChartConfiguration;
import br.digilabs.jqplot.chart.data.ChartData;

/**
 *
 * @author inaiat
 */
public interface Chart<T extends ChartData<?>> {
    
    ChartConfiguration getChartConfiguration();
    T getChartData();
    void setChartData(T value);
}
