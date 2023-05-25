package ro.nextreports.jofc2.util;

import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.io.path.PathTrackingWriter;

import ro.nextreports.jofc2.model.elements.NullElement;

public class NullConverter extends ConverterBase<NullElement> {

	public boolean canConvert(Class type) {
		return NullElement.class.isAssignableFrom(type);
	}

	@Override
	public void convert(NullElement o, PathTrackingWriter writer, MarshallingContext mc) {
		writer.underlyingWriter().setValue(null);
	}
}
