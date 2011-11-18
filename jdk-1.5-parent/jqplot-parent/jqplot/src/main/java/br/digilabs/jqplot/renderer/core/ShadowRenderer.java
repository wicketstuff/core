/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.digilabs.jqplot.renderer.core;

import br.digilabs.jqplot.JqPlotResources;
import br.digilabs.jqplot.chart.elements.Renderer;

/**
 *
 * @author bernardo.moura
 */
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
