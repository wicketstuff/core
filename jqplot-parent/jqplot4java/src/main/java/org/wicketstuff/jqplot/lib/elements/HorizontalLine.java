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

/**
 * HorizontalLine object.
 *
 * HorizontalLine properties can be set or overriden by the options passed in from the user.
 *
 * @author inaiat
 */
public class HorizontalLine extends Line {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 7228237374262625669L;

    /** The y. */
    private Integer y;

    /** The xmin. */
    private Integer xmin;

    /** The ymin. */
    private Integer ymin;

    /**
     * Instantiates a new horizontal line.
     */
    public HorizontalLine() {
    }

    /**
     * Instantiates a new horizontal line.
     *
     * @param name the name
     */
    public HorizontalLine(String name) {
        setName(name);
    }

    /**
     * Gets the y.
     *
     * @return the y
     */
    public Integer getY() {
        return y;
    }

    /**
     * Sets the y.
     *
     * @param y the new y
     */
    public void setY(Integer y) {
        this.y = y;
    }

    /**
     * Gets the xmin.
     *
     * @return the xmin
     */
    public Integer getXMin() {
        return xmin;
    }

    /**
     * Sets the xmin.
     *
     * @param xmin the new xmin
     */
    public void setXMin(Integer xmin) {
        this.xmin = xmin;
    }

    /**
     * Gets the ymin.
     *
     * @return the ymin
     */
    public Integer getYMin() {
        return ymin;
    }

    /**
     * Sets the ymin.
     *
     * @param ymin the new ymin
     */
    public void setYMin(Integer ymin) {
        this.ymin = ymin;
    }
}
