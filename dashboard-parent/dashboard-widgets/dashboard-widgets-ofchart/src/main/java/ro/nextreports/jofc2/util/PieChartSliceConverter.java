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

import ro.nextreports.jofc2.model.elements.PieChart;
import ro.nextreports.jofc2.model.elements.PieChart.Slice;

import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.io.path.PathTrackingWriter;

public class PieChartSliceConverter extends ConverterBase<Slice> {

	public boolean canConvert(Class c) {
		return PieChart.Slice.class.isAssignableFrom(c);
	}

	@Override
	public void convert(Slice o, PathTrackingWriter writer, MarshallingContext mc) {
		writeNode(writer, "value", o.getValue(), false);
		writeNode(writer, "label", o.getLabel(), true);
		writeNode(writer, "tip", o.getTip(), true);
		writeNode(writer, "highlight", o.getHighlight(), false);
		writeNode(writer, "text", o.getText(), true);
		writeNode(writer, "on-click", o.getOnClick(), true);
	}
}
