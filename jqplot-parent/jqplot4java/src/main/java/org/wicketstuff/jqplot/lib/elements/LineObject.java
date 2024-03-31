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
 * Container for all line types used with a canvas overlay.
 * 
 * @author inaiat
 * 
 */
public class LineObject implements Element {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -1464182411022493695L;

    private DashedHorizontalLine dashedHorizontalLine;
    private DashedVerticalLine dashedVerticalLine;
    private HorizontalLine horizontalLine;
    private VerticalLine verticalLine;
    
    public DashedHorizontalLine dashedHorizontalLineInstance() {
	if (dashedHorizontalLine == null) {
	    dashedHorizontalLine = new DashedHorizontalLine();
	}
	return dashedHorizontalLine;
    }
    
    public DashedVerticalLine dashedVerticalLineInstance() {
	if (dashedVerticalLine == null) {
	    dashedVerticalLine = new DashedVerticalLine();
	}
	return dashedVerticalLine;
    }

    public HorizontalLine horizontalLineInstance() {
	if (horizontalLine == null) {
	    horizontalLine = new HorizontalLine();
	}
	return horizontalLine;
    }
    
    public VerticalLine verticalLineInstance() {
	if (verticalLine == null) {
	    verticalLine = new VerticalLine();
	}
	return verticalLine;
    }

    /**
     * @return the dashedHorizontalLine
     */
    public DashedHorizontalLine getDashedHorizontalLine() {
	return dashedHorizontalLine;
    }

    /**
     * @param dashedHorizontalLine
     *            the dashedHorizontalLine to set
	 * @return DashedHorizontalLine
     */
    public DashedHorizontalLine setDashedHorizontalLine(DashedHorizontalLine dashedHorizontalLine) {
	this.dashedHorizontalLine = dashedHorizontalLine;
	return dashedHorizontalLine;
    }

    /**
     * @return the dashedVerticalLine
     */
    public DashedVerticalLine getDashedVerticalLine() {
	return dashedVerticalLine;
    }

    /**
     * @param dashedVerticalLine
     *            the dashedVerticalLine to set
	 * @return DashedHorizontalLine
	 */
    public DashedVerticalLine setDashedVerticalLine(DashedVerticalLine dashedVerticalLine) {
	this.dashedVerticalLine = dashedVerticalLine;
	return dashedVerticalLine;
    }

    /**
     * @return the horizontalLine
	 * @return HorizontalLine
     */
    public HorizontalLine getHorizontalLine() {
	return horizontalLine;
    }

    /**
     * @param horizontalLine
     *            the horizontalLine to set
	 * @return HorizontalLine
	 */
    public HorizontalLine setHorizontalLine(HorizontalLine horizontalLine) {
	this.horizontalLine = horizontalLine;
	return horizontalLine;
    }

    /**
     * @return the verticalLine
     */
    public VerticalLine getVerticalLine() {
	return verticalLine;
    }

    /**
     * @param verticalLine
     *            the verticalLine to set
	 * @return VerticalLine
     */
    public VerticalLine setVerticalLine(VerticalLine verticalLine) {
	this.verticalLine = verticalLine;
	return verticalLine;
    }
}
