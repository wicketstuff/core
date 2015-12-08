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
import com.googlecode.wickedcharts.highcharts.options.CreditOptions;
import com.googlecode.wickedcharts.highcharts.options.Function;
import com.googlecode.wickedcharts.highcharts.options.HorizontalAlignment;
import com.googlecode.wickedcharts.highcharts.options.Legend;
import com.googlecode.wickedcharts.highcharts.options.LegendLayout;
import com.googlecode.wickedcharts.highcharts.options.PlotBand;
import com.googlecode.wickedcharts.highcharts.options.PlotOptions;
import com.googlecode.wickedcharts.highcharts.options.PlotOptionsChoice;
import com.googlecode.wickedcharts.highcharts.options.SeriesType;
import com.googlecode.wickedcharts.highcharts.options.Title;
import com.googlecode.wickedcharts.highcharts.options.Tooltip;
import com.googlecode.wickedcharts.highcharts.options.VerticalAlignment;
import com.googlecode.wickedcharts.highcharts.options.color.HexColor;
import com.googlecode.wickedcharts.highcharts.options.color.RgbaColor;
import com.googlecode.wickedcharts.highcharts.options.series.SimpleSeries;

import java.util.Arrays;
import java.util.Collections;

/**
 * @author <a href="http://www.GitHub.com/PaulBors">Paul Bors</a>
 */
public class AreaSplineOptions extends ShowcaseOptions {
    private static final long serialVersionUID = 1L;

    public AreaSplineOptions() {
        setChart(new ChartOptions().setType(SeriesType.AREASPLINE));

        setTitle(new Title("Average fruit consumption during one week"));

        setLegend(new Legend()
            .setLayout(LegendLayout.VERTICAL)
            .setAlign(HorizontalAlignment.LEFT)
            .setVerticalAlign(VerticalAlignment.TOP)
            .setX(150)
            .setY(100)
            .setFloating(Boolean.TRUE)
            .setBorderWidth(1)
            .setBackgroundColor(new HexColor("#FFFFFF"))
        );

        setxAxis(new Axis()
            .setCategories(
                Arrays.asList(new String[]{
                    "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"
                })
            )
            .setPlotBands(Collections.singletonList(new PlotBand()
                .setFrom(4.5f)
                .setTo(6.5f)
                .setColor(new RgbaColor(68, 170, 213, .2f)))
            )
        );

        setyAxis(new Axis().setTitle(new Title("Fruit units")));

        setTooltip(
            new Tooltip().setFormatter(
                new Function(" return ''+this.x +': '+ this.y +' units';")
            )
        );

        setCredits(new CreditOptions().setEnabled(Boolean.FALSE));

        setPlotOptions(
            new PlotOptionsChoice().setAreaspline(
                new PlotOptions().setFillOpacity(0.5f)
            )
        );

        addSeries(
            new SimpleSeries()
                .setName("John")
                .setData(Arrays.asList(new Number[] { 3, 4, 3, 5, 4, 10, 12 }))
        );

        addSeries(
            new SimpleSeries()
                .setName("Jane")
                .setData(Arrays.asList(new Number[] { 1, 3, 4, 3, 3,  5, 4 }))
        );
    }

    @Override
    public String getLabel() {
        return super.getLabel() + "Area spline";
    }
}
