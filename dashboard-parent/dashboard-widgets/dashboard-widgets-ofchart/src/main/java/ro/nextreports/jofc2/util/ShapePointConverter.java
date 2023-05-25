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

import ro.nextreports.jofc2.model.elements.ShapeChart;
import ro.nextreports.jofc2.model.elements.ShapeChart.Point;

import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.io.path.PathTrackingWriter;

public class ShapePointConverter extends ConverterBase<Point>
{
    @Override
    public void convert(ShapeChart.Point o, PathTrackingWriter writer, MarshallingContext mc)
    {
        writeNode(writer, "x", o.getX(), false);
        writeNode(writer, "y", o.getY(), false);
    }

    public boolean canConvert(Class arg0)
    {
        return Point.class.isAssignableFrom(arg0);
    }
}