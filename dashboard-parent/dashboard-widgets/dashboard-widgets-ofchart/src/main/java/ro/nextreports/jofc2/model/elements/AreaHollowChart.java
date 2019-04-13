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

public class AreaHollowChart extends LineChart {
    /**
	 * 
	 */
	private static final long serialVersionUID = 6023248458467056064L;

	private static transient final Float DEFAULT_ALPHA = 0.35f;
    
    @Alias("fill-alpha") private Float fillAlpha;
    @Alias("fill") private String fillColor;
    
    public AreaHollowChart() {
        super("area_hollow");
        setFillAlpha(DEFAULT_ALPHA);
    }

    public Float getFillAlpha() {
        return fillAlpha;
    }

    public AreaHollowChart setFillAlpha(Float fillAlpha) {
        this.fillAlpha = fillAlpha;
        return this;
    }

    public String getFillColor()
    {
        return fillColor;
    }

    public AreaHollowChart setFillColor(String fillColor)
    {
        this.fillColor = fillColor;
        return this;
    }
}