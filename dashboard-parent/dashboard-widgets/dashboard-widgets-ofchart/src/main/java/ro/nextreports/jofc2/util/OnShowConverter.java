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

import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.io.path.PathTrackingWriter;

import ro.nextreports.jofc2.model.elements.AnimatedElement;

/**
 * -------------------------------------------------------------
 * 22.09.2011 
 * Patched with 'delay' and'cascade' fields for OnShow animation
 * Mihai Dinca-Panaitescu
 * -------------------------------------------------------------
 */
public class OnShowConverter extends ConverterBase<AnimatedElement.OnShow> {
	
	public boolean canConvert(Class c) {
		return AnimatedElement.OnShow.class.isAssignableFrom(c);
	}

	@Override
	public void convert(AnimatedElement.OnShow onShow, PathTrackingWriter writer, MarshallingContext mc) {
		writeNode(writer, "type", onShow.getType(), true);
		writeNode(writer, "delay", onShow.getDelay(), true);
		writeNode(writer, "cascade", onShow.getCascade(), true);
	}
}
