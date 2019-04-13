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

public class FilledBarChart extends BarChart {
    /**
	 * 
	 */
	private static final long serialVersionUID = 3471991868191065273L;
	private static final transient String TYPE = "bar_filled";
    @Alias("outline-colour") private String outlineColour;
    
    public FilledBarChart() {
        super(TYPE);
    }
    
    public FilledBarChart(String colour, String outlineColour) {
        super(TYPE);
        setColour(colour);
        setOutlineColour(outlineColour);
    }
    
    protected FilledBarChart(String style) {
        super(style);
    }
    
    public String getOutlineColour() {
        return outlineColour;
    }
    
    public BarChart setOutlineColour(String outlineColour) {
        this.outlineColour = outlineColour;
        return this;
    }
    
    public static class Bar extends BarChart.Bar {
        @Alias("outline-colour") private String outlineColour;
        
        public Bar(Number top, Number bottom) {
            super(top, bottom);
        }
        
        public Bar(Number top, Number bottom, String colour, String outlineColour) {
            super(top, bottom);
            setColour(colour);
            setOutlineColour(outlineColour);
        }
        
        public Bar(Number top) {
            super(top);
        }
        
        public Bar setOutlineColour(String outlineColour) {
            this.outlineColour = outlineColour;
            return this;
        }
        
        public String getOutlineColour() {
            return outlineColour;
        }
    }
}
