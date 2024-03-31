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
package org.wicketstuff.jqplot.lib.elements;

import java.io.Serializable;
import java.util.List;

/**
 * The Class PointLabels.
 *
 * @author inaiat
 */
public class PointLabels implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -2768644134500702341L;
    
    /** The show. */
    private Boolean show = true;
    
    /** The location. */
    private String location;
    
    /** The edge tolerance. */
    private Integer edgeTolerance;
    
    /** The labels. */
    private List<String> labels;

    /**
     * Gets the labels.
     *
     * @return the labels
     */
    public List<String> getLabels() {
        return labels;
    }

    /**
     * Sets the labels.
     *
     * @param labels the new labels
	 * @return PointLabels
	 */
    public PointLabels setLabels(List<String> labels) {
        this.labels = labels;
        return this;
    }

    /**
     * Gets the edge tolerance.
     *
     * @return the edge tolerance
     */
    public Integer getEdgeTolerance() {
        return edgeTolerance;
    }

    /**
     * Sets the edge tolerance.
     *
     * @param edgeTolerance the new edge tolerance
	 * @return PointLabels
	 */
    public PointLabels setEdgeTolerance(Integer edgeTolerance) {
        this.edgeTolerance = edgeTolerance;
        return this;
    }

    /**
     * Gets the location.
     *
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the location.
     *
     * @param location the new location
	 * @return PointLabels
	 */
    public PointLabels setLocation(String location) {
        this.location = location;
        return this;
    }

    /**
     * Checks if is show.
     *
     * @return the boolean
     */
    public Boolean isShow() {
        return show;
    }

    /**
     * Sets the show.
     *
     * @param show the new show
	 * @return PointLabels
     */
    public PointLabels setShow(Boolean show) {
        this.show = show;
        return this;
    }
}
