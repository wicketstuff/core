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

import com.googlecode.wickedcharts.highcharts.options.Axis;
import com.googlecode.wickedcharts.highcharts.options.ChartOptions;
import com.googlecode.wickedcharts.highcharts.options.DataLabels;
import com.googlecode.wickedcharts.highcharts.options.Function;
import com.googlecode.wickedcharts.highcharts.options.Legend;
import com.googlecode.wickedcharts.highcharts.options.PlotOptions;
import com.googlecode.wickedcharts.highcharts.options.PlotOptionsChoice;
import com.googlecode.wickedcharts.highcharts.options.SeriesType;
import com.googlecode.wickedcharts.highcharts.options.Title;
import com.googlecode.wickedcharts.highcharts.options.Tooltip;
import com.googlecode.wickedcharts.highcharts.options.series.CoordinatesSeries;

/**
 * @author <a href="http://www.GitHub.com/PaulBors">Paul Bors</a>
 */
public class ColumnRangeOptions extends ShowcaseOptions {
	private static final long serialVersionUID = 1L;

	public ColumnRangeOptions() {
		setChartOptions(new ChartOptions()
			.setType(SeriesType.COLUMNRANGE)
			.setInverted(Boolean.TRUE)
		);

		setTitle(new Title("Temperature variation by month"));

		setSubtitle(new Title("Observed in Vik i Sogn, Norway, 2009"));

		setxAxis(new Axis()
			.setCategories(
				"Jan",
				"Feb",
				"Mar",
				"Apr",
				"May",
				"Jun",
				"Jul",
				"Aug",
				"Sep",
				"Oct",
				"Nov",
				"Dec"
			)
		);

		setyAxis(new Axis()
			.setTitle(new Title("Temperature (°C)"))
		);

		setTooltip(new Tooltip()
			.setValueSuffix("°C")
		);

		setPlotOptions(new PlotOptionsChoice()
			.setColumnrange(new PlotOptions()
				.setDataLabels(new DataLabels()
					.setEnabled(Boolean.TRUE)
					.setFormatter(new Function("return this.y + '°C';"))
					.setY(0))
			)
		);

		setLegend(new Legend(Boolean.FALSE));

		addSeries(new CoordinatesSeries()
			.addPoint(-9.7, 9.4)
			.addPoint(-8.7, 6.5)
			.addPoint(-3.5, 9.4)
			.addPoint(-1.4, 19.9)
			.addPoint(0.0, 22.6)
			.addPoint(2.9, 29.5)
			.addPoint(9.2, 30.7)
			.addPoint(7.3, 26.5)
			.addPoint(4.4, 18.0)
			.addPoint(-3.1, 11.4)
			.addPoint(-5.2, 10.4)
			.addPoint(-13.5, 9.8)
		);
	}

	@Override
	public String getLabel() {
		return super.getLabel() + "Column range";
	}
}