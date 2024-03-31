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

import java.util.ArrayList;
import java.util.Collection;

/**
 * Line object.  
 * 
 * Line properties can be set or overriden by the options passed in from the user.
 *
 * @author inaiat
 */
public class Line extends CanvasOverlay {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 7228237374262625679L;
    
    /** The start. */
    private Collection<Integer> start;
       
    /** The stop. */
    private Collection<Integer> stop;

    /**
     * Instantiates a new line.
     */
    public Line() {
    }

    /**
     * Instantiates a new line.
     *
     * @param name the name
     */
    public Line(String name) {
        setName(name);
    }

	public Collection<Integer> startInstance() {
		if (this.start == null) {
			this.start = new ArrayList<Integer>();
		}
		return start;
	}

	/**
	 * Gets the start.
	 * 
	 * @return the start
	 */
	public Collection<Integer> getStart() {
		return start;
	}

	/**
	 * Sets the start.
	 * 
	 * @param start
	 *            the new start
	 * @return Line
	 */
	public Line setStart(Collection<Integer> start) {
		this.start = start;
		return this;
	}

	/**
	 * Get stop instance
	 * @return Collection of integer type
	 */
	public Collection<Integer> stopInstance() {
		if (this.stop == null) {
			this.stop = new ArrayList<Integer>();
		}
		return stop;
	}

	/**
	 * Gets the stop.
	 * 
	 * @return the stop
	 */
	public Collection<Integer> getStop() {
		return stop;
	}

	/**
	 * Sets the stop.
	 * 
	 * @param stop
	 *            the new stop
	 * @return Line
	 */
	public Line setStop(Collection<Integer> stop) {
		this.stop = stop;
		return this;
	}
}
