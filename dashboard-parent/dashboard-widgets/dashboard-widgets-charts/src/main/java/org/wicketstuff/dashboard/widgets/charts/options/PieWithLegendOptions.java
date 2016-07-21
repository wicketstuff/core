/*
 * Copyright 2014 Paul Bors
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this work except in compliance with
 * the License. You may obtain a copy of the License in the LICENSE file, or at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package org.wicketstuff.dashboard.widgets.charts.options;

import com.googlecode.wickedcharts.highcharts.options.ChartOptions;
import com.googlecode.wickedcharts.highcharts.options.Cursor;
import com.googlecode.wickedcharts.highcharts.options.DataLabels;
import com.googlecode.wickedcharts.highcharts.options.PlotOptions;
import com.googlecode.wickedcharts.highcharts.options.PlotOptionsChoice;
import com.googlecode.wickedcharts.highcharts.options.SeriesType;
import com.googlecode.wickedcharts.highcharts.options.Title;
import com.googlecode.wickedcharts.highcharts.options.Tooltip;
import com.googlecode.wickedcharts.highcharts.options.color.NullColor;
import com.googlecode.wickedcharts.highcharts.options.series.Point;
import com.googlecode.wickedcharts.highcharts.options.series.PointSeries;

/**
 * @author <a href="http://www.GitHub.com/PaulBors">Paul Bors</a>
 */
public class PieWithLegendOptions extends ShowcaseOptions {
	private static final long serialVersionUID = 1L;

	public PieWithLegendOptions() {
		setChartOptions(new ChartOptions()
			.setPlotBackgroundColor(new NullColor())
			.setPlotBorderWidth(null)
			.setPlotShadow(Boolean.FALSE)
		);

		setTitle(new Title("Browser market shares at a specific website, 2010"));

		setTooltip(new Tooltip()
			.setPointFormat("{series.name}: <b>{point.percentage}%</b>")
			.setPercentageDecimals(1)
		);

		setPlotOptions(new PlotOptionsChoice()
			.setPie(new PlotOptions()
				.setAllowPointSelect(Boolean.TRUE)
				.setCursor(Cursor.POINTER)
				.setDataLabels(new DataLabels()
					.setEnabled(Boolean.TRUE))
				.setShowInLegend(Boolean.TRUE)
			)
		);

		addSeries(new PointSeries()
			.setType(SeriesType.PIE)
			.setName("Browser share")
			.addPoint(new Point("Firefox", 45.0))
			.addPoint(new Point("IE", 26.8))
			.addPoint(new Point("Chrome", 12.8)
				.setSliced(Boolean.TRUE)
				.setSelected(Boolean.TRUE))
			.addPoint(new Point("Safari", 8.5))
			.addPoint(new Point("Opera", 6.2))
			.addPoint(new Point("Others", 0.7))
		);

	}

	@Override
	public String getLabel() {
		return super.getLabel() + "Pie with legend";
	}
}