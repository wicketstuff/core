/*
This file is part of jofc2.

jofc2 is free software: you can redistribute it and/or modify
it under the terms of the Lesser GNU General Public License as
published by the Free Software Foundation, either version 3 of
the License, or (at your option) any later version.

jofc2 is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

See <http://www.gnu.org/licenses/lgpl-3.0.txt>.
 */
package ro.nextreports.jofc2.model.axis;

import ro.nextreports.jofc2.model.metadata.Alias;

import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

public class YAxis extends Axis {

    private static final long serialVersionUID = 7471159737831995334L;
    @Alias("tick-length")
    private Integer tick_length;
    @Alias("labels")
    private YAxisLabels yAxisLabels;
    private List<String> labels;

    public YAxis setTickLength(Integer tick_length) {
        this.tick_length = tick_length;
        return this;
    }

    public Integer getTickLength() {
        return tick_length;
    }

    public YAxis setYAxisLabels(YAxisLabels labels) {
        this.yAxisLabels = labels;
        return this;
    }

    public YAxis setLabels(String... labels) {
        this.labels = new ArrayList<String>();
        this.labels.addAll(Arrays.asList(labels));
        return this;
    }

    public YAxis setLabels(List<String> labels) {
        this.labels = labels;
        return this;
    }

    public YAxis addLabels(Label... labels) {
        if (this.yAxisLabels == null) {
            this.yAxisLabels = new YAxisLabels();
        }
        this.yAxisLabels.addLabels(labels);
        return this;
    }

    public YAxis addLabels(List<Label> labels) {
        if (this.yAxisLabels == null) {
            this.yAxisLabels = new YAxisLabels();
        }
        this.yAxisLabels.addLabels(labels);
        return this;
    }

    public YAxisLabels getYAxisLabels() {
        return this.yAxisLabels;
    }

    public List<String> getLabels() {
        return labels;
    }
}
