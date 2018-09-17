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
 * VerticalLine object.
 *
 * VerticalLine properties can be set or overriden by the options passed in from the user.
 *
 * @author inaiat
 */
public class VerticalLine extends Line {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 7228237374962625669L;


    /**
     * Instantiates a new vertical line.
     */
    public VerticalLine() {
    }

    /**
     * Instantiates a new vertical line.
     *
     * @param name the name
     */
    public VerticalLine(String name) {
        setName(name);
    }

}
