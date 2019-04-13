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
package ro.nextreports.jofc2.util;

import ro.nextreports.jofc2.model.elements.BarChart;
import ro.nextreports.jofc2.model.elements.FilledBarChart;
import ro.nextreports.jofc2.model.elements.SketchBarChart;

import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.io.path.PathTrackingWriter;

public class BarConverter extends ConverterBase<BarChart.Bar> {

	@Override
	public void convert(BarChart.Bar b, PathTrackingWriter writer, MarshallingContext mc) {
		writeNode(writer, "top", b.getTop(), false);
		writeNode(writer, "bottom", b.getBottom(), true);
		writeNode(writer, "colour", b.getColour(), true);
		writeNode(writer, "tip", b.getTooltip(), true);
		writeNode(writer, "on-click", b.getOnClick(), true);

        if (b instanceof FilledBarChart.Bar) {
			writeNode(writer, "outline-colour", ((FilledBarChart.Bar) b).getOutlineColour(), true);
		}
		if (b instanceof SketchBarChart.Bar) {
			writeNode(writer, "offset", ((SketchBarChart.Bar) b).getFunFactor(), true);
		}
	}

	public boolean canConvert(Class clazz) {
		return BarChart.Bar.class.isAssignableFrom(clazz);
	}
}
