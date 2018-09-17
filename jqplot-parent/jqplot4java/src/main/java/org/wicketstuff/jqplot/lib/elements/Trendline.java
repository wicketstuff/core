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
 * TrendLine
 * 
 * @author inaiat
 * 
 */
public class Trendline implements Element {

    private static final long serialVersionUID = -2866673142982586438L;

    private Boolean show;
    private String color;
    private String label;
    private Boolean shadow;
    private Float lineWidth;
    private Float shadowAngle;
    private Float shadowOffset;
    private Float shadowAlpha;
    private Float shadowDepth;

    public Boolean getShow() {
	return show;
    }

    public Trendline setShow(Boolean show) {
	this.show = show;
	return this;
    }

    public String getColor() {
	return color;
    }

    public Trendline setColor(String color) {
	this.color = color;
	return this;
    }

    public String getLabel() {
	return label;
    }

    public Trendline setLabel(String label) {
	this.label = label;
	return this;
    }

    public Boolean getShadow() {
	return shadow;
    }

    public Trendline setShadow(Boolean shadow) {
	this.shadow = shadow;
	return this;
    }

    public Float getLineWidth() {
	return lineWidth;
    }

    public Trendline setLineWidth(Float lineWidth) {
	this.lineWidth = lineWidth;
	return this;
    }

    public Float getShadowAngle() {
	return shadowAngle;
    }

    public Trendline setShadowAngle(Float shadowAngle) {
	this.shadowAngle = shadowAngle;
	return this;
    }

    public Float getShadowOffset() {
	return shadowOffset;
    }

    public Trendline setShadowOffset(Float shadowOffset) {
	this.shadowOffset = shadowOffset;
	return this;
    }

    public Float getShadowAlpha() {
	return shadowAlpha;
    }

    public Trendline setShadowAlpha(Float shadowAlpha) {
	this.shadowAlpha = shadowAlpha;
	return this;
    }

    public Float getShadowDepth() {
	return shadowDepth;
    }

    public Trendline setShadowDepth(Float shadowDepth) {
	this.shadowDepth = shadowDepth;
	return this;
    }

}
