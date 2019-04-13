/*
This file is part of jofc2.

jofc2 is free software: you can redistribute it and/or modify
it under the terms of the Lesser GNU General Public License as
published by the Free Software Foundation, either version 3 of
the License, or (at your option) any later version.

jofc2 is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

See <http://www.gnu.org/licenses/lgpl-3.0.txt>.
*/

package ro.nextreports.jofc2.model.elements;

import ro.nextreports.jofc2.model.metadata.Alias;

public class SketchBarChart extends FilledBarChart {
    /**
	 * 
	 */
	private static final long serialVersionUID = 7562070898232847510L;
	private static final transient String TYPE = "bar_sketch";
    @Alias("offset") private Integer funFactor;
    
    public SketchBarChart() {
        super(TYPE);
    }
    
    public SketchBarChart(String colour, String outlineColour, Integer funFactor) {
        super(TYPE);
        setColour(colour);
        setOutlineColour(outlineColour);
        setFunFactor(funFactor);
    }
    
    public Integer getFunFactor() {
        return funFactor;
    }
    
    public BarChart setFunFactor(Integer funFactor) {
        this.funFactor = funFactor;
        return this;
    }
    
    public static class Bar extends FilledBarChart.Bar {
        @Alias("offset") private Integer funFactor;
        
        public Bar(Number top) {
            super(top);
        }
        
        public Bar(Number top, Integer funFactor) {
            super(top);
            setFunFactor(funFactor);
        }
        
        public Bar(Number top, Number bottom, Integer funFactor) {
            super(top, bottom);
            setFunFactor(funFactor);
        }
        
        public Bar setFunFactor(Integer funFactor) {
            this.funFactor = funFactor;
            return this;
        }
        
        public Integer getFunFactor() {
            return funFactor;
        }
    }
}
