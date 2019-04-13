package ro.nextreports.jofc2.model.elements;

import java.io.Serializable;

import ro.nextreports.jofc2.model.metadata.Converter;
import ro.nextreports.jofc2.util.NullConverter;

@Converter(NullConverter.class)
public class NullElement implements Serializable {

	@Override
	public String toString() {
		return null;
	}
}
