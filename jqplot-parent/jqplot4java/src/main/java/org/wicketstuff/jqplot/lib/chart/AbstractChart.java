/*
 *  Copyright 2011 Inaiat H. Moraes.
 * 
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  under the License.
 */
package org.wicketstuff.jqplot.lib.chart;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.wicketstuff.jqplot.lib.Chart;
import org.wicketstuff.jqplot.lib.ChartConfiguration;
import org.wicketstuff.jqplot.lib.axis.Axis;
import org.wicketstuff.jqplot.lib.axis.XAxis;
import org.wicketstuff.jqplot.lib.axis.YAxis;
import org.wicketstuff.jqplot.lib.data.ChartData;
import org.wicketstuff.jqplot.lib.elements.Axes;
import org.wicketstuff.jqplot.lib.elements.CanvasOverlay;
import org.wicketstuff.jqplot.lib.elements.GridPadding;
import org.wicketstuff.jqplot.lib.elements.Legend;
import org.wicketstuff.jqplot.lib.elements.Serie;
import org.wicketstuff.jqplot.lib.elements.SeriesDefaults;
import org.wicketstuff.jqplot.lib.elements.Title;

/**
 * Abstract class to help build charts.
 * 
 * @author inaiat
 * 
 * @param <S>
 *            Type of {@link Axis}
 * 
 */
public abstract class AbstractChart<T extends ChartData<?>, S extends Serializable>
		implements Chart<T> {

	private static final long serialVersionUID = -5744130130488157491L;

	public AbstractChart<T, S> addSeriesColors(String... colors) {

		getChartConfiguration().seriesColorsInstance().addAll(
				Arrays.asList(colors));
		return this;
	}

	public AbstractChart<T, S> addSeriesColors(Collection<String> colors) {
		getChartConfiguration().seriesColorsInstance().addAll(colors);
		return this;
	}

	public AbstractChart<T, S> setSeriesColors(Collection<String> colors) {
		getChartConfiguration().setSeriesColors(colors);
		return this;
	}

	public Collection<String> getSeriesColors() {
		return getChartConfiguration().seriesColorsInstance();
	}

	/**
	 * Adiciona uma serie
	 * 
	 * @param serie Add serie of chart
	 * @return AbstractChart
	 */
	public AbstractChart<T, S> addSerie(Serie serie) {
		Collection<Serie> series = getSeries();
		if (series == null) {
			series = new ArrayList<Serie>();
		}
		series.add(serie);
		return this;
	}

	/**
	 * Add a collection of series
	 * 
	 * @param series Add series of chart
	 * @return AbstractChart
	 */
	public AbstractChart<T, S> addSeries(Serie... series) {
		Collection<Serie> chartSeries = getSeries();
		if (chartSeries == null) {
			chartSeries = new ArrayList<Serie>();
		}
		for (int i = series.length - 1; i >= 0; i--) {
			chartSeries.add(series[i]);
		}
		return this;

	}

	/**
	 * 
	 * @return chartConfiguration
	 */
	public abstract ChartConfiguration<S> getChartConfiguration();

	/**
	 * 
	 * @param title Set title of chart
	 * @return AbstractChart
	 */
	public AbstractChart<T, S> setSimpleTitle(String title) {
		getChartConfiguration().setSimpleTitle(title);
		return this;
	}

	/**
	 * 
	 * @return Axes
	 */
	@Deprecated
	public Axes<S> createAxes() {
		return getChartConfiguration().createAxes();
	}

	/**
	 * 
	 * @return xAxis
	 */
	@Deprecated
	public XAxis<S> createXAxis() {
		return getChartConfiguration().createXAxis();
	}

	/**
	 * 
	 * @return yAxis
	 */
	@Deprecated
	public YAxis<S> createYAxis() {
		return getChartConfiguration().createYAxis();
	}

	/**
	 * 
	 * @return axis
	 */
	@Deprecated
	public Axis<S> createAxesDefaults() {
		return getChartConfiguration().createAxesDefaults();
	}

	/**
	 * 
	 * @param label Set label for axis X
	 * @return AbstractChart
	 */
	public AbstractChart<T, S> setLabelX(String label) {
		getChartConfiguration().setLabelX(label);
		return this;
	}

	/**
	 * 
	 * @param label Set label for axis Y
	 * @return AbstractChart
	 */
	public AbstractChart<T, S> setLabelY(String label) {
		getChartConfiguration().setLabelY(label);
		return this;
	}

	/**
	 * @return the series
	 */
	public Collection<Serie> getSeries() {
		return getChartConfiguration().seriesInstance();
	}

	/**
	 * @param series
	 *            the series to set
	 *
	 * @return AbstractChart
	 */
	public AbstractChart<T, S> setSeries(Collection<Serie> series) {
		this.getChartConfiguration().setSeries(series);
		return this;
	}

	/**
	 * @return the title
	 */
	public Title getTitle() {
		return getChartConfiguration().getTitle();
	}

	/**
	 * @param title
	 *            the title to set
	 * @return AbstractChart
	 */
	public AbstractChart<T, S> setTitle(Title title) {
		getChartConfiguration().setTitle(title);
		return this;
	}

	/**
	 * @return the axesDefaults
	 */
	public Axis<S> getAxesDefaults() {
		return getChartConfiguration().axesDefaultsInstance();
	}

	/**
	 * @param axesDefaults
	 *            the axesDefaults to set
	 * @return AbstractChart
	 */
	public AbstractChart<T, S> setAxesDefaults(Axis<S> axesDefaults) {
		getChartConfiguration().setAxesDefaults(axesDefaults);
		return this;
	}

	/**
	 * @return the seriesDefaults
	 */
	public SeriesDefaults getSeriesDefaults() {
		return getChartConfiguration().seriesDefaultsInstance();
	}

	/**
	 * @param seriesDefaults
	 *            the seriesDefaults to set
	 * @return AbstractChart
	 */
	public AbstractChart<T, S> setSeriesDefaults(SeriesDefaults seriesDefaults) {
		getChartConfiguration().setSeriesDefaults(seriesDefaults);
		return this;
	}

	/**
	 * 
	 * @param values Set values for interval colors
	 * @return AbstractChart
	 */
	public AbstractChart<T, S> addIntervalColors(String... values) {
		getChartConfiguration().seriesDefaultsInstance().getRendererOptions()
				.getIntervalColors().addAll(Arrays.asList(values));
		return this;
	}

	/**
	 * 
	 * @param values Set interval values
	 * @return AbstractChart
	 */
	public AbstractChart<T, S> addIntervals(Integer... values) {
		getChartConfiguration().seriesDefaultsInstance().getRendererOptions()
				.getIntervals().addAll(Arrays.asList(values));
		return this;
	}

	/**
	 * 
	 * @param stackSeries Enable/Disable stackSeries
	 * @return AbstractChart
	 */
	public AbstractChart<T, S> setStackSeries(Boolean stackSeries) {
		getChartConfiguration().setStackSeries(stackSeries);
		return this;
	}

	/**
	 * 
	 * @param captureRightClick Enable/Disable captureRightClick
	 * @return AbstractChart
	 */
	public AbstractChart<T, S> setCaptureRightClick(Boolean captureRightClick) {
		getChartConfiguration().setCaptureRightClick(captureRightClick);
		return this;
	}

	/**
	 * 
	 * @param highlightMouseDown Enable/Disable  highlighMouseDown
	 * @return AbstractChart
	 */
	public AbstractChart<T, S> setHighlightMouseDown(Boolean highlightMouseDown) {
		getChartConfiguration().seriesDefaultsInstance().rendererOptionsInstance()
				.setHighlightMouseDown(highlightMouseDown);
		return this;
	}

	/**
	 * 
	 * @param margin Set value of margin
	 * @return AbstractChart
	 */
	public AbstractChart<T, S> setBarMargin(Integer margin) {
		getChartConfiguration().seriesDefaultsInstance().rendererOptionsInstance()
				.setBarMargin(margin);
		return this;
	}

	/**
	 * 
	 * @param margin Set value o slice margin
	 * @return AbstractChart
	 */
	public AbstractChart<T, S> setSliceMargin(Integer margin) {
		getChartConfiguration().seriesDefaultsInstance().rendererOptionsInstance()
				.setSliceMargin(margin);
		return this;
	}

	/**
	 * 
	 * @param dataLabels Set data labels
	 * @return AbstractChart
	 */
	public AbstractChart<T, S> setDataLabels(String dataLabels) {
		getChartConfiguration().seriesDefaultsInstance().rendererOptionsInstance()
				.setDataLabels(dataLabels);
		return this;
	}

	/**
	 * 
	 * @param width Set line width
	 * @return AbstractChart
	 */
	public AbstractChart<T, S> setLineWidth(Integer width) {
		getChartConfiguration().seriesDefaultsInstance().rendererOptionsInstance()
				.setLineWidth(width);
		return this;
	}

	/**
	 * 
	 * @param showDataLabels Enable/Disable show data labels
	 * @return AbstractChart
	 */
	public AbstractChart<T, S> setShowDataLabels(Boolean showDataLabels) {
		getChartConfiguration().seriesDefaultsInstance().rendererOptionsInstance()
				.setShowDataLabels(showDataLabels);
		return this;
	}

	/**
	 * 
	 * @param fill Enable/Disable  fill
	 * @return AbstractChart
	 */
	public AbstractChart<T, S> setFill(Boolean fill) {
		getChartConfiguration().seriesDefaultsInstance().rendererOptionsInstance()
				.setFill(fill);
		return this;
	}

	/**
	 * 
	 * @param alpha Set value for bubble alpha
	 * @return AbstractChart
	 */
	public AbstractChart<T, S> setBubbleAlpha(Float alpha) {
		getChartConfiguration().seriesDefaultsInstance().rendererOptionsInstance()
				.setBubbleAlpha(alpha);
		return this;
	}

	/**
	 * 
	 * @param alpha Set value for highlight alpha
	 * @return AbstractChart
	 */
	public AbstractChart<T, S> setHighlightAlpha(Float alpha) {
		getChartConfiguration().seriesDefaultsInstance().rendererOptionsInstance()
				.setHighlightAlpha(alpha);
		return this;
	}

	/**
	 * 
	 * @param showLabels Enable/Disable show labels
	 * @return AbstractChart
	 */
	public AbstractChart<T, S> setShowLabels(Boolean showLabels) {
		getChartConfiguration().seriesDefaultsInstance().rendererOptionsInstance()
				.setShowLables(showLabels);
		return this;
	}


	/**
	 *
	 * @param alpha Set value for shadow alpha
	 * @return AbstractChart
	 */
	public AbstractChart<T, S> setShadowAlpha(String alpha) {
		getChartConfiguration().seriesDefaultsInstance().setShadowAlpha(alpha);
		return this;
	}

	/**
	 * 
	 * @param fillZero Enable/Disable fill zeros
	 * @return AbstractChart
	 */
	public AbstractChart<T, S> setFillZero(Boolean fillZero) {
		getChartConfiguration().seriesDefaultsInstance().rendererOptionsInstance()
				.setFillZero(fillZero);
		return this;
	}

	/**
	 * 
	 * @param shadow Enable/Disable  shadow
	 * @return AbstractChart
	 */
	public AbstractChart<T, S> setShadow(Boolean shadow) {
		getChartConfiguration().getSeriesDefaults().setShadow(shadow);
		return this;
	}

	/**
	 * 
	 * @param legend Set {@link Legend} object
	 * @return AbstractChart
	 */
	public AbstractChart<T, S> setLegend(Legend legend) {
		getChartConfiguration().setLegend(legend);
		return this;
	}

	/**
	 *
	 * @param gridPadding Set {@link GridPadding} object
	 * @return AbstractChart
	 */
	public AbstractChart<T, S> setGridPadding(GridPadding gridPadding) {
		getChartConfiguration().setGridPadding(gridPadding);
		return this;
	}

	public GridPadding getGridPadding() {
		return getChartConfiguration().getGridPadding();
	}

	/**
	 * @return the axes
	 */
	public Axes<S> getAxes() {
		return getChartConfiguration().axesInstance();
	}

	/**
	 * @param axes
	 *            the axes to set
	 * @return AbstractChart
	 */
	public AbstractChart<T, S> setAxes(Axes<S> axes) {
		getChartConfiguration().setAxes(axes);
		return this;
	}

	public XAxis<S> getXAxis() {
		return getChartConfiguration().xAxisInstance();
	}

	public YAxis<S> getYAxis() {
		return getChartConfiguration().yAxisInstance();
	}

	/**
	 * 
	 * @param ticks Set values for ticks
	 */
	@Deprecated
	public void setTicks(String... ticks) {
		getChartConfiguration().xAxisInstance().setTicks(ticks);
	}

	/**
	 * 
	 * @param padMin Set value for padMin
	 */
	@Deprecated
	public void setPadMin(Float padMin) {
		getChartConfiguration().yAxisInstance().setPadMin(1.05f);
	}

	/**
	 * @return the canvas overlay
	 */
	public CanvasOverlay getCanvasOverlay() {
		return getChartConfiguration().canvasOverlayInstance();
	}
}
