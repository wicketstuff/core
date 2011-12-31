package br.digilabs.jqplot.chart;

import br.digilabs.jqplot.JqPlotResources;
import br.digilabs.jqplot.chart.data.ChartData;
import br.digilabs.jqplot.metadata.JqPlotPlugin;

/**
 *
 * @author inaiat
 */
@JqPlotPlugin(values={JqPlotResources.CanvasTextRenderer, JqPlotResources.CategoryAxisRenderer})
public class LineChart<T extends ChartData<?>> extends DefaultChart<T> {

    public LineChart() {
        getChartConfiguration().createAxesDefaults().setLabelRenderer(JqPlotResources.CanvasAxisLabelRenderer);
        getChartConfiguration().createXAxis().setPad(0F);
    }

    public LineChart(String title, String labelX, String labelY) {
        this();
        getChartConfiguration().setLabelX(labelX);
        getChartConfiguration().setLabelY(labelY);
    }  
}
