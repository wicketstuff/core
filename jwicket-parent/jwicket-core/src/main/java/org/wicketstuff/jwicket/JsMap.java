package org.wicketstuff.jwicket;


import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;


public class JsMap extends HashMap<String, Object> implements Serializable {

	private static final long serialVersionUID = 1L;

	public JsMap() {
		super();
	}


	public String toString(final String rawOptions) {
		if (isEmpty()) {
			return "";
		}
		StringBuffer buffer = new StringBuffer();
		toString(buffer, rawOptions);
		return buffer.toString();
	}

	public void toString(final StringBuffer buffer, final String rawOptions) {
		boolean first = true;
		for (Iterator<String> iter = keySet().iterator(); iter.hasNext();) {
			if (!first)
				buffer.append(",");
			else
				first = false;

			String key = iter.next();
			Object value = get(key);

			buffer.append(key);
			buffer.append(":");
			if (value instanceof CharSequence) {
				buffer.append("'");
				buffer.append(value);
				buffer.append("'");
			}
			else if (value instanceof JsScript) {
				buffer.append(value.toString());
			}
			else if (value instanceof JsOption[]) {
				boolean firstOption = true;
				buffer.append("{");
				for (JsOption o : ((JsOption[])value)) {
					if (!firstOption)
						buffer.append(",");
					else
						firstOption = false;
					buffer.append(o);
				}
				buffer.append("}");
			}
			else if (value instanceof JsOption) {
				buffer.append("{");
				buffer.append((JsOption)value);
				buffer.append("}");
			}
			else if (value instanceof Object[]) {
				boolean firstOption = true;
				buffer.append("[");
				for (Object o : ((Object[])value)) {
					if (!firstOption)
						buffer.append(",");
					else
						firstOption = false;
					if (o instanceof CharSequence) {
						buffer.append("'");
						buffer.append(o);
						buffer.append("'");
					}
					else
						buffer.append(o);
				}
				buffer.append("]");
			}
			else
				buffer.append(value);
		}
		if (rawOptions != null) {
			buffer.append(",");
			buffer.append(rawOptions);
		}
	}

	public void put(final String key, JsOption...options) {
		super.put(key, options);
	}

	public void put(final String key, Object...options) {
		super.put(key, options);
	}
}
