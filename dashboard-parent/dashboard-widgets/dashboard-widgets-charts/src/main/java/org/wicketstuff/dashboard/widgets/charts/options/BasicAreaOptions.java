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
import com.googlecode.wickedcharts.highcharts.options.CssStyle;
import com.googlecode.wickedcharts.highcharts.options.Labels;
import com.googlecode.wickedcharts.highcharts.options.Marker;
import com.googlecode.wickedcharts.highcharts.options.PlotOptions;
import com.googlecode.wickedcharts.highcharts.options.PlotOptionsChoice;
import com.googlecode.wickedcharts.highcharts.options.SeriesType;
import com.googlecode.wickedcharts.highcharts.options.State;
import com.googlecode.wickedcharts.highcharts.options.StatesChoice;
import com.googlecode.wickedcharts.highcharts.options.Symbol;
import com.googlecode.wickedcharts.highcharts.options.Title;
import com.googlecode.wickedcharts.highcharts.options.Tooltip;
import com.googlecode.wickedcharts.highcharts.options.series.Series;
import com.googlecode.wickedcharts.highcharts.options.series.SimpleSeries;

import java.util.Arrays;

/**
 * @author <a href="http://www.GitHub.com/PaulBors">Paul Bors</a>
 */
public class BasicAreaOptions extends ShowcaseOptions {
    private static final long serialVersionUID = 1L;

    public BasicAreaOptions() {

        ChartOptions chartOptions = new ChartOptions();
        chartOptions
                .setType(SeriesType.AREA);
        setChartOptions(chartOptions);

        setTitle(new Title("US and USSR nuclear stockpiles"));
        setSubtitle(new Title(
            "Source: <a href=\"http://thebulletin.metapress.com/content/c4120650912x74k7/fulltext.pdf\">thebulletin.metapress.com</a>"
        ));

        Axis xAxis = new Axis();
        xAxis.setLabels(
            new Labels().setStyle(new CssStyle())
        );
        setxAxis(xAxis);

        Axis yAxis = new Axis();
        yAxis.setTitle(new Title("Nuclear weapon states"));
        yAxis.setLabels(
            new Labels().setStyle(new CssStyle())
        );
        setyAxis(yAxis);

        setTooltip(new Tooltip());

        State hoverState = new State();
        hoverState.setEnabled(Boolean.TRUE);

        StatesChoice statesChoice = new StatesChoice();
        statesChoice.setHover(hoverState);

        Marker marker = new Marker();
        marker.setEnabled(Boolean.TRUE);
        marker.setSymbol(new Symbol(Symbol.PredefinedSymbol.CIRCLE));
        marker.setRadius(2);
        marker.setStates(statesChoice);

        PlotOptions plotOptions = new PlotOptions();
        plotOptions.setPointStart(1940l);
        plotOptions.setMarker(marker);

        PlotOptionsChoice plotOptionsChoice = new PlotOptionsChoice();
        plotOptionsChoice.setArea(plotOptions);
        setPlotOptions(plotOptionsChoice);

        Series<Number> series1 = new SimpleSeries();
        series1.setName("USA");
        series1.setData(Arrays
                .asList(new Number[]{null, null, null, null, null, 6, 11, 32,
                        110, 235, 369, 640, 1005, 1436, 2063, 3057, 4618, 6444, 9822,
                        15468, 20434, 24126, 27387, 29459, 31056, 31982, 32040, 31233,
                        29224, 27342, 26662, 26956, 27912, 28999, 28965, 27826, 25579,
                        25722, 24826, 24605, 24304, 23464, 23708, 24099, 24357, 24237,
                        24401, 24344, 23586, 22380, 21004, 17287, 14747, 13076, 12555,
                        12144, 11009, 10950, 10871, 10824, 10577, 10527, 10475, 10421,
                        10358, 10295, 10104}));
        addSeries(series1);

        Series<Number> series2 = new SimpleSeries();
        series2.setName("USSR/Russia");
        series2.setData(Arrays
                .asList(new Number[]{null, null, null, null, null, null, null,
                        null, null, null, 5, 25, 50, 120, 150, 200, 426, 660, 869,
                        1060, 1605, 2471, 3322, 4238, 5221, 6129, 7089, 8339, 9399,
                        10538, 11643, 13092, 14478, 15915, 17385, 19055, 21205, 23044,
                        25393, 27935, 30062, 32049, 33952, 35804, 37431, 39197, 45000,
                        43000, 41000, 39000, 37000, 35000, 33000, 31000, 29000, 27000,
                        25000, 24000, 23000, 22000, 21000, 20000, 19000, 18000, 18000,
                        17000, 16000}));
        addSeries(series2);
    }

    @Override
    public String getLabel() {
		return super.getLabel() + "Basic area";
    }
}
