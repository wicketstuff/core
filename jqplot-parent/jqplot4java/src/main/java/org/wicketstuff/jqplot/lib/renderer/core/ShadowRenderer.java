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
package org.wicketstuff.jqplot.lib.renderer.core;

import org.wicketstuff.jqplot.lib.JqPlotResources;
import org.wicketstuff.jqplot.lib.renderer.Renderer;

/**
 *
 * @author inaiat
 */
@Deprecated
public class ShadowRenderer implements Renderer {

	private static final long serialVersionUID = 4595091532005199206L;

	private Double angle = 45.0;
    private Double offset = 1.0;
    private Double alpha = 0.07;
    private Double lineWidth = 1.5;
    private String lineJoint = "miter";
    private String lineCap = "round";
    private Boolean closePath = false;
    private Boolean fill = false;
    private Double depth = 3.0;
    private String strokeStyle = "rgba(0,0,0,0.1)";
    private Boolean isarc = false;

    public Double getAlpha() {
        return alpha;
    }

    public void setAlpha(Double alpha) {
        this.alpha = alpha;
    }

    public Double getAngle() {
        return angle;
    }

    public void setAngle(Double angle) {
        this.angle = angle;
    }

    public Boolean getClosePath() {
        return closePath;
    }

    public void setClosePath(Boolean closePath) {
        this.closePath = closePath;
    }

    public Double getDepth() {
        return depth;
    }

    public void setDepth(Double depth) {
        this.depth = depth;
    }

    public Boolean getFill() {
        return fill;
    }

    public void setFill(Boolean fill) {
        this.fill = fill;
    }

    public Boolean getIsarc() {
        return isarc;
    }

    public void setIsarc(Boolean isarc) {
        this.isarc = isarc;
    }

    public String getLineCap() {
        return lineCap;
    }

    public void setLineCap(String lineCap) {
        this.lineCap = lineCap;
    }

    public String getLineJoint() {
        return lineJoint;
    }

    public void setLineJoint(String lineJoint) {
        this.lineJoint = lineJoint;
    }

    public Double getLineWidth() {
        return lineWidth;
    }

    public void setLineWidth(Double lineWidth) {
        this.lineWidth = lineWidth;
    }

    public Double getOffset() {
        return offset;
    }

    public void setOffset(Double offset) {
        this.offset = offset;
    }

    public String getStrokeStyle() {
        return strokeStyle;
    }

    public void setStrokeStyle(String strokeStyle) {
        this.strokeStyle = strokeStyle;
    }

    public JqPlotResources resource() {
        return JqPlotResources.ShadowRenderer;
    }
}
