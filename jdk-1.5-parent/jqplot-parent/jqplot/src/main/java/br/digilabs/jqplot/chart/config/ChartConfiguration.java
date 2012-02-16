/*
 * Copyright 2011 inaiat.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package br.digilabs.jqplot.chart.config;

import java.io.Serializable;
import java.util.Collection;

import br.digilabs.jqplot.axis.Axis;
import br.digilabs.jqplot.axis.XAxis;
import br.digilabs.jqplot.axis.YAxis;
import br.digilabs.jqplot.chart.elements.Axes;
import br.digilabs.jqplot.chart.elements.Legend;
import br.digilabs.jqplot.chart.elements.Serie;
import br.digilabs.jqplot.chart.elements.SeriesDefaults;
import br.digilabs.jqplot.chart.elements.Title;

/**
 *
 * @author inaiat
 */
public class ChartConfiguration implements Serializable {

	private static final long serialVersionUID = 7082325039222592701L;

	protected Collection<Serie> series;
    protected Axes axes;
    protected Title title;
    protected Axis axesDefaults;
    private SeriesDefaults seriesDefaults;
    private Boolean stackSeries;
    private Boolean showMarker;
    protected Legend legend;

    public Legend getLegend() {
        return legend;
    }

    public void setLegend(Legend legend) {
        this.legend = legend;
    }
    
    public void setSimpleTitle(String title) {
        if (title == null) {
            this.title = new Title(title);
        } else {
            this.title.setText(title);
        }
    }
    
    public Axes createAxes() {
        if (axes == null) {
            this.axes = new Axes();
        }
        return this.axes;
    }

    public XAxis createXAxis() {
        Axes newAxes = createAxes();
        if (newAxes.getXaxis()==null) {
            XAxis xAxis = new XAxis();
            newAxes.setXaxis(xAxis);
        }
        return newAxes.getXaxis();
    }

    public YAxis createYAxis() {
        Axes newAxes = createAxes();
        if (newAxes.getYaxis()==null) {
            YAxis yAxis = new YAxis();
            newAxes.setYaxis(yAxis);
        }
        return newAxes.getYaxis();
    }

    public Axis createAxesDefaults() {
        if (axesDefaults == null) {
            axesDefaults = new Axis();
        }
        return axesDefaults;
    }

    public void setLabelX(String label) {
        createXAxis().setLabel(label);
    }

    public void setLabelY(String label) {
        createYAxis().setLabel(label);
    }

    /**
     * @return the series
     */
    public Collection<Serie> getSeries() {
        return series;
    }

    /**
     * @param series the series to set
     */
    public void setSeries(Collection<Serie> series) {
        this.setSeries(series);
    }

    /**
     * @return the title
     */
    public Title getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(Title title) {
        this.title = title;
    }

    /**
     * @return the axesDefaults
     */
    public Axis getAxesDefaults() {
        return axesDefaults;
    }

    /**
     * @param axesDefaults the axesDefaults to set
     */
    public void setAxesDefaults(Axis axesDefaults) {
        this.axesDefaults = axesDefaults;
    }

    /**
     * @return the axes
     */
    public Axes getAxes() {
        return axes;
    }

    /**
     * @param axes the axes to set
     */
    public void setAxes(Axes axes) {
        this.axes = axes;
    }

    /**
     * @return the seriesDefaults
     */
    public SeriesDefaults getSeriesDefaults() {
        return seriesDefaults;
    }

    /**
     * @param seriesDefaults the seriesDefaults to set
     */
    public void setSeriesDefaults(SeriesDefaults seriesDefaults) {
        this.seriesDefaults = seriesDefaults;
    }

    /**
     * @return the stackSeries
     */
    public Boolean getStackSeries() {
        return stackSeries;
    }

    /**
     * @param stackSeries the stackSeries to set
     */
    public void setStackSeries(Boolean stackSeries) {
        this.stackSeries = stackSeries;
    }

    /**
     * @return the showMarker
     */
    public Boolean getShowMarker() {
        return showMarker;
    }

    /**
     * @param showMarker the showMarker to set
     */
    public void setShowMarker(Boolean showMarker) {
        this.showMarker = showMarker;
    }
}
