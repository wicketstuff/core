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

import org.wicketstuff.jqplot.lib.axis.X2Axis;
import org.wicketstuff.jqplot.lib.axis.XAxis;
import org.wicketstuff.jqplot.lib.axis.Y2Axis;
import org.wicketstuff.jqplot.lib.axis.YAxis;

/**
 * Axes options are specified within an axes object at the top level of the plot
 * options like so:
 * 
 * <pre>
 *  {
 *   axes: {
 *      xaxis: {min: 5},
 *        yaxis: {min: 2, max: 8, numberTicks:4},
 *        x2axis: {pad: 1.5},
 *        y2axis: {ticks:[22, 44, 66, 88]}
 *    }
 *  }
 * </pre>
 * 
 * There are 2 x axes, 'xaxis' and 'x2axis', and 9 yaxes, 'yaxis', 'y2axis'.
 * 'y3axis', ... Any or all of which may be specified.
 * 
 * @author inaiat
 * 
 */
public class Axes<T extends Serializable> implements Element {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -1464182411022493685L;

    private XAxis<T> xaxis;
    private YAxis<T> yaxis;
    private X2Axis<T> x2axis;
    private Y2Axis<T> y2axis;
    
    public XAxis<T> xAxisInstance() {
	if (xaxis==null) {
	    xaxis = new XAxis<T>();
	}
	return xaxis;
    }
    
    public YAxis<T> yAxisInstance() {
	if (yaxis==null) {
	    yaxis = new YAxis<T>();
	}
	return yaxis;
    }
    
    public X2Axis<T> x2axisInstance() {
	if (x2axis==null) {
	    x2axis = new X2Axis<T>();
	}
	return x2axis;
    }
    
    public Y2Axis<T> y2axisInstance() {
	if (y2axis==null) {
	    y2axis = new Y2Axis<T>();
	}
	return y2axis;
    }

    /**
     * @return the xaxis
     */
    public XAxis<T> getXaxis() {
	return xaxis;
    }

    /**
     * @param xaxis
     *            the xaxis to set
	 * @return Axes
     */
    public Axes<T> setXaxis(XAxis<T> xaxis) {
	this.xaxis = xaxis;
	return this;
    }

    /**
     * @return the yaxis
     */
    public YAxis<T> getYaxis() {
	return yaxis;
    }

    /**
     * @param yaxis
     *            the yaxis to set
	 * @return Axes
     */
    public Axes<T> setYaxis(YAxis<T> yaxis) {
	this.yaxis = yaxis;
	return this;
    }

    /**
     * @return the x2axis
     */
    public X2Axis<T> getX2axis() {
	return x2axis;
    }

    /**
     * @param x2axis
     *            the x2axis to set
	 * @return Axes
	 */
    public Axes<T> setX2axis(X2Axis<T> x2axis) {
	this.x2axis = x2axis;
	return this;
    }

    /**
     * @return the y2axis
     */
    public Y2Axis<T> getY2axis() {
	return y2axis;
    }

    /**
     * @param y2axis
     *            the y2axis to set
	 * @return Axes
     */
    public Axes<T> setY2axis(Y2Axis<T> y2axis) {
	this.y2axis = y2axis;
	return this;
    }
}
