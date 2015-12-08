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
import com.googlecode.wickedcharts.highcharts.options.Marker;
import com.googlecode.wickedcharts.highcharts.options.SeriesType;
import com.googlecode.wickedcharts.highcharts.options.Title;
import com.googlecode.wickedcharts.highcharts.options.ZoomType;
import com.googlecode.wickedcharts.highcharts.options.color.RadialGradient;
import com.googlecode.wickedcharts.highcharts.options.color.RgbaColor;
import com.googlecode.wickedcharts.highcharts.options.series.BubbleSeries;

/**
 * @author <a href="http://www.GitHub.com/PaulBors">Paul Bors</a>
 */
public class BubbleChart3DOptions extends ShowcaseOptions {
	private static final long serialVersionUID = 1L;

	public BubbleChart3DOptions() {
		setChartOptions(new ChartOptions()
			.setType(SeriesType.BUBBLE)
			.setPlotBorderWidth(1)
			.setZoomType(ZoomType.XY)
		);

		setTitle(new Title("Highcharts Bubbles with radial gradient fill"));

		setxAxis(new Axis()
			.setGridLineWidth(1)
		);

		setyAxis(new Axis()
			.setStartOnTick(Boolean.FALSE)
			.setEndOnTick(Boolean.FALSE)
		);

		addSeries(new BubbleSeries()
			.addPoint(9, 81, 63)
			.addPoint(98, 5, 89)
			.addPoint(51, 50, 73)
			.addPoint(41, 22, 14)
			.addPoint(58, 24, 20)
			.addPoint(78, 37, 34)
			.addPoint(55, 56, 53)
			.addPoint(18, 45, 70)
			.addPoint(42, 44, 28)
			.addPoint(3, 52, 59)
			.addPoint(31, 18, 97)
			.addPoint(79, 91, 63)
			.addPoint(93, 23, 23)
			.addPoint(44, 83, 22)
			.setMarker(new Marker().setFillColor(new RadialGradient()
				.setCx(0.4)
				.setCy(0.3)
				.setR(0.7)
				.addStop(0, new RgbaColor(255, 255, 255, 0.5f))
				.addStop(1, new RgbaColor(69, 114, 167, 0.5f))
			))
		);

		addSeries(new BubbleSeries()
			.addPoint(42, 38, 20)
			.addPoint(6, 18, 1)
			.addPoint(1, 93, 55)
			.addPoint(57, 2, 90)
			.addPoint(80, 76, 22)
			.addPoint(11, 74, 96)
			.addPoint(88, 56, 10)
			.addPoint(30, 47, 49)
			.addPoint(57, 62, 98)
			.addPoint(4, 16, 16)
			.addPoint(46, 10, 11)
			.addPoint(22, 87, 89)
			.addPoint(57, 91, 82)
			.addPoint(45, 15, 98)
			.setMarker(new Marker().setFillColor(new RadialGradient()
				.setCx(0.4)
				.setCy(0.3)
				.setR(0.7)
				.addStop(0, new RgbaColor(255, 255, 255, 0.5f))
				.addStop(1, new RgbaColor(170, 70, 67, 0.5f))
			))
		);
	}

	@Override
	public String getLabel() {
		return super.getLabel() + "3D Bubbles";
	}
}