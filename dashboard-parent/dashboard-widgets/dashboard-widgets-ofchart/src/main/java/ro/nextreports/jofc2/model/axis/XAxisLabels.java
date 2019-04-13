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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ro.nextreports.jofc2.OFC;
import ro.nextreports.jofc2.model.metadata.Alias;

public class XAxisLabels extends Label {
    /**
	 * 
	 */
	private static final long serialVersionUID = -6134375829177947590L;
	private Integer steps;
    @Alias("visible-steps")
    private Integer visibleSteps;
    private List<Object> labels;
    
    public XAxisLabels() {
        //when no labels are needed
    }
    
    public XAxisLabels(String... labels) {
        addLabels(labels);
    }
    
    public XAxisLabels(List<String> labels) {
        addLabels(OFC.toArray(labels, String.class));
    }
    
    public List<Object> getLabels() {
        return labels;
    }
    
    public XAxisLabels addLabels(String... labels) {
        checkLabels();
        this.labels.addAll(Arrays.asList(labels));
        return this;
    }
    
    public XAxisLabels addLabels(Label... labels) {
        checkLabels();
        this.labels.addAll(Arrays.asList(labels));
        return this;
    }
    
    public XAxisLabels addLabels(List<Label> labels) {
        checkLabels();
        this.labels.addAll(labels);
        return this;
    }
    
    public XAxisLabels setSteps(Integer steps) {
        this.steps = steps;
        return this;
    }
    
    public Integer getSteps() {
        return steps;
    }
    
    private synchronized void checkLabels() {
        if (labels == null) labels = new ArrayList<Object>();
    }

    public Integer getVisibleSteps() {
        return visibleSteps;
    }

    public void setVisibleSteps(Integer visibleSteps) {
        this.visibleSteps = visibleSteps;
    }
}
