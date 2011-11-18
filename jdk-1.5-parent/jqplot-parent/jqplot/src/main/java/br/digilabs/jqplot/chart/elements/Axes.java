/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.digilabs.jqplot.chart.elements;

import br.digilabs.jqplot.axis.X2Axis;
import br.digilabs.jqplot.axis.XAxis;
import br.digilabs.jqplot.axis.Y2Axis;
import br.digilabs.jqplot.axis.YAxis;

/**
 *
 * @author inaiat
 */
public class Axes implements Element {
    
	private static final long serialVersionUID = -1464182411022493685L;

	private XAxis xaxis;
    private YAxis yaxis;
    private X2Axis x2axis;
    private Y2Axis y2axis;

    /**
     * @return the xaxis
     */
    public XAxis getXaxis() {
        return xaxis;
    }

    /**
     * @param xaxis the xaxis to set
     */
    public void setXaxis(XAxis xaxis) {
        this.xaxis = xaxis;
    }

    /**
     * @return the yaxis
     */
    public YAxis getYaxis() {
        return yaxis;
    }

    /**
     * @param yaxis the yaxis to set
     */
    public void setYaxis(YAxis yaxis) {
        this.yaxis = yaxis;
    }

    /**
     * @return the x2axis
     */
    public X2Axis getX2axis() {
        return x2axis;
    }

    /**
     * @param x2axis the x2axis to set
     */
    public void setX2axis(X2Axis x2axis) {
        this.x2axis = x2axis;
    }

    /**
     * @return the y2axis
     */
    public Y2Axis getY2axis() {
        return y2axis;
    }

    /**
     * @param y2axis the y2axis to set
     */
    public void setY2axis(Y2Axis y2axis) {
        this.y2axis = y2axis;
    }

    
}
