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

/**
 * The Class FillBetween.
 *
 * @author inaiat
 */
public class FillBetween implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 8516029001505871387L;
    
    /** The serie1. */
    private Integer serie1;
    
    /** The serie2. */
    private Integer serie2;
    
    /** The color. */
    private String color;
    
    /** The base series. */
    private Integer baseSeries;
    
    /** The fill. */
    private Boolean fill;

    /**
     * Gets the base series.
     *
     * @return baseSeries
     */
    public Integer getBaseSeries() {
        return baseSeries;
    }

    /**
     * Sets the base series.
     *
     * @param baseSeries the new base series
     */
    public void setBaseSeries(Integer baseSeries) {
        this.baseSeries = baseSeries;
    }

    /**
     * Gets the color.
     *
     * @return color
     */
    public String getColor() {
        return color;
    }

    /**
     * Sets the color.
     *
     * @param color the new color
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * Gets the fill.
     *
     * @return fill true ou false
     */
    public Boolean getFill() {
        return fill;
    }

    /**
     * Sets the fill.
     *
     * @param fill the new fill
     */
    public void setFill(Boolean fill) {
        this.fill = fill;
    }

    /**
     * Gets the serie1.
     *
     * @return serie1
     */
    public Integer getSerie1() {
        return serie1;
    }

    /**
     * Sets the serie1.
     *
     * @param serie1 the new serie1
     */
    public void setSerie1(Integer serie1) {
        this.serie1 = serie1;
    }

    /**
     * Gets the serie2.
     *
     * @return serie2
     */
    public Integer getSerie2() {
        return serie2;
    }

    /**
     * Sets the serie2.
     *
     * @param serie2 the new serie2
     */
    public void setSerie2(Integer serie2) {
        this.serie2 = serie2;
    }
}
