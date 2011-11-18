package br.digilabs.jqplot.chart;

import java.util.Collection;

import br.digilabs.jqplot.axis.Axis;
import br.digilabs.jqplot.axis.XAxis;
import br.digilabs.jqplot.axis.YAxis;
import br.digilabs.jqplot.chart.config.ChartConfiguration;
import br.digilabs.jqplot.chart.data.ChartData;
import br.digilabs.jqplot.chart.elements.Axes;
import br.digilabs.jqplot.chart.elements.Serie;
import br.digilabs.jqplot.chart.elements.SeriesDefaults;
import br.digilabs.jqplot.chart.elements.Title;

/**
 *
 * @author inaiat
 */
public class DefaultChart<T extends ChartData<?>> implements Chart<T> {

    private final ChartConfiguration configuration;
    private T chartData;

    public DefaultChart() {
        this.configuration = new ChartConfiguration();
    }   

    public ChartConfiguration getChartConfiguration() {
        return configuration;
    }

    public T getChartData() {
        return chartData;
    }

    public void setChartData(T value) {
        this.chartData = value;
    }

    public void setSimpleTitle(String title) {
        getChartConfiguration().setSimpleTitle(title);
    }

    public Axes createAxes() {
        return getChartConfiguration().createAxes();
    }

    public XAxis createXAxis() {
        return getChartConfiguration().createXAxis();
    }

    public YAxis createYAxis() {
        return getChartConfiguration().createYAxis();
    }

    public Axis createAxesDefaults() {
        return getChartConfiguration().createAxesDefaults();
    }

    public void setLabelX(String label) {
        getChartConfiguration().setLabelX(label);
    }

    public void setLabelY(String label) {
        getChartConfiguration().setLabelY(label);
    }

    /**
     * @return the series
     */
    public Collection<Serie> getSeries() {
        return getChartConfiguration().getSeries();
    }

    /**
     * @param series the series to set
     */
    public void setSeries(Collection<Serie> series) {
        this.getChartConfiguration().setSeries(series);
    }

    /**
     * @return the title
     */
    public Title getTitle() {
        return getChartConfiguration().getTitle();
    }

    /**
     * @param title the title to set
     */
    public void setTitle(Title title) {
        getChartConfiguration().setTitle(title);
    }

    /**
     * @return the axesDefaults
     */
    public Axis getAxesDefaults() {
        return getChartConfiguration().getAxesDefaults();
    }

    /**
     * @param axesDefaults the axesDefaults to set
     */
    public void setAxesDefaults(Axis axesDefaults) {
        getChartConfiguration().setAxesDefaults(axesDefaults);
    }

    /**
     * @return the axes
     */
    public Axes getAxes() {
        return getChartConfiguration().getAxes();
    }

    /**
     * @param axes the axes to set
     */
    public void setAxes(Axes axes) {
        getChartConfiguration().setAxes(axes);
    }

    /**
     * @return the seriesDefaults
     */
    public SeriesDefaults getSeriesDefaults() {
        return getChartConfiguration().getSeriesDefaults();
    }

    /**
     * @param seriesDefaults the seriesDefaults to set
     */
    public void setSeriesDefaults(SeriesDefaults seriesDefaults) {
        getChartConfiguration().setSeriesDefaults(seriesDefaults);
    }
}
