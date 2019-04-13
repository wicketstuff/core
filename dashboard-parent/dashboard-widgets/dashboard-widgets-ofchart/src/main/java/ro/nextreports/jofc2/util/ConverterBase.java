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

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.path.PathTrackingWriter;

public abstract class ConverterBase<T> implements Converter {

	public final Object unmarshal(HierarchicalStreamReader arg0, UnmarshallingContext arg1) {
		return null;
	}

	@SuppressWarnings("unchecked")
	public final void marshal(Object o, HierarchicalStreamWriter hsw, MarshallingContext mc) {
		convert((T) o, (PathTrackingWriter) hsw, mc);
	}

	public final void writeNode(PathTrackingWriter writer, String name, Object o, boolean ignoreNull) {
		if (ignoreNull && o == null) {
			return;
		}
		//Tell xstream to treat null values as integers so that quotes are omitted.
		writer.startNode(name, o == null ? Integer.class : o.getClass());
		writer.setValue(o == null ? "null" : o.toString());
		writer.endNode();
	}

	public abstract void convert(T o, PathTrackingWriter writer, MarshallingContext mc);
}
