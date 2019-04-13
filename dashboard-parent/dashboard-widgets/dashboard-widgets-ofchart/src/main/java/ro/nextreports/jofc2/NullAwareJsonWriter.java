package ro.nextreports.jofc2;

import java.io.Writer;

import com.thoughtworks.xstream.io.json.JsonWriter;

public class NullAwareJsonWriter extends JsonWriter {
	public NullAwareJsonWriter(Writer writer) {
		super(writer);
	}

	@Override
	protected void addValue(String value, Type type) {
		if (type == Type.STRING && value == null) {
			writer.write("null");
		} else {
			super.addValue(value, type);
		}
	}
}
