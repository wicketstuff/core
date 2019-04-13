package ro.nextreports.jofc2;

import java.io.Writer;

import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;

public class OFCJSONDriver extends JsonHierarchicalStreamDriver {

	@Override
	public HierarchicalStreamWriter createWriter(Writer out) {
		return new NullAwareJsonWriter(out);
	}
}
