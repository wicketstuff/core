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
import com.googlecode.wickedcharts.highcharts.options.Background;
import com.googlecode.wickedcharts.highcharts.options.ChartOptions;
import com.googlecode.wickedcharts.highcharts.options.Labels;
import com.googlecode.wickedcharts.highcharts.options.MinorTickInterval;
import com.googlecode.wickedcharts.highcharts.options.Pane;
import com.googlecode.wickedcharts.highcharts.options.PixelOrPercent;
import com.googlecode.wickedcharts.highcharts.options.PlotBand;
import com.googlecode.wickedcharts.highcharts.options.SeriesType;
import com.googlecode.wickedcharts.highcharts.options.TickPosition;
import com.googlecode.wickedcharts.highcharts.options.Title;
import com.googlecode.wickedcharts.highcharts.options.Tooltip;
import com.googlecode.wickedcharts.highcharts.options.color.HexColor;
import com.googlecode.wickedcharts.highcharts.options.color.LinearGradient;
import com.googlecode.wickedcharts.highcharts.options.color.NullColor;
import com.googlecode.wickedcharts.highcharts.options.series.SimpleSeries;

/**
 * @author <a href="http://www.GitHub.com/PaulBors">Paul Bors</a>
 */
public class AngularGaugeOptions extends ShowcaseOptions {
	private static final long serialVersionUID = 1L;

	public AngularGaugeOptions() {
		setChartOptions(new ChartOptions()
			.setType(SeriesType.GAUGE)
			.setPlotBackgroundColor(new NullColor())
			.setPlotBackgroundImage(null)
			.setPlotBorderWidth(0)
			.setPlotShadow(Boolean.FALSE)
		);

		setTitle(new Title("Speedometer"));

		setPane(new Pane()
			.setStartAngle(-150)
			.setEndAngle(150)
			.addBackground(new Background()
				.setBackgroundColor(new LinearGradient(0, 0, 0, 1)
					.addStop(0, HexColor.fromString("#FFFFFF"))
					.addStop(1, HexColor.fromString("#333333")))
				.setBorderWidth(0)
				.setOuterRadius(new PixelOrPercent(109, PixelOrPercent.Unit.PERCENT))
			)
			.addBackground(new Background()
				.setBackgroundColor(new LinearGradient(0, 1, 0, 1)
					.addStop(0, HexColor.fromString("#333333"))
					.addStop(1, HexColor.fromString("#FFFFFF")))
				.setBorderWidth(1)
				.setOuterRadius(new PixelOrPercent(107, PixelOrPercent.Unit.PERCENT))
			)
			.addBackground(Background.DEFAULT_BACKGROUND)
			.addBackground(new Background()
				.setBackgroundColor(HexColor.fromString("#DDDDDD"))
				.setBorderWidth(0)
				.setOuterRadius(new PixelOrPercent(105, PixelOrPercent.Unit.PERCENT))
				.setInnerRadius(new PixelOrPercent(103, PixelOrPercent.Unit.PERCENT))
			)
		);

		addyAxis(new Axis()
			.setMin(0)
			.setMax(200)
			.setMinorTickInterval(new MinorTickInterval().setAuto(Boolean.TRUE))
			.setMinorTickWidth(1)
			.setMinorTickLength(10)
			.setMinorTickPosition(TickPosition.INSIDE)
			.setMinorTickColor(HexColor.fromString("#666666"))
			.setTickPixelInterval(30)
			.setTickWidth(2)
			.setTickPosition(TickPosition.INSIDE)
			.setTickLength(10)
			.setTickColor(HexColor.fromString("#666666"))
			.setLabels(new Labels().setStep(2))
			.setTitle(new Title("km/h"))
			.addPlotBand(new PlotBand()
				.setFrom(0)
				.setTo(120)
				.setColor(HexColor.fromString("#55bf3b"))
			)
			.addPlotBand(new PlotBand()
				.setFrom(120)
				.setTo(160)
				.setColor(HexColor.fromString("#DDDF0D"))
			)
			.addPlotBand(new PlotBand()
				.setFrom(160)
				.setTo(200)
				.setColor(HexColor.fromString("#DF5353"))
			)
		);

		addSeries(new SimpleSeries()
			.addPoint(80)
			.setName("Speed")
			.setTooltip(new Tooltip().setValueSuffix(" km/h"))
		);
	}

	@Override
	public String getLabel() {
		return super.getLabel() + "Angular Gauge";
	}
}