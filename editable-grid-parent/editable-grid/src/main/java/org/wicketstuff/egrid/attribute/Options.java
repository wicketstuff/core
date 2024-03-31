package org.wicketstuff.egrid.attribute;


import java.io.Serial;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;


public class Options implements Serializable {

    @Serial
	private static final long serialVersionUID = 1L;

    private final Map<String, String> options = new LinkedHashMap<String, String>();

    public Options() {
        super();
    }

    public boolean isEmpty() {
        return this.options.isEmpty();
    }

    public String get(final String key) {
        return options.get(key);
    }

    public int getInt(final String key) {
        return Integer.valueOf(this.options.get(key));
    }


    public short getShort(final String key) {
        return Short.valueOf(this.options.get(key));
    }

    public float getFloat(final String key) {
        return Float.valueOf(this.options.get(key));
    }

    public boolean getBoolean(final String key) {

        return Boolean.valueOf(this.options.get(key));
    }

    public Options put(final String key, final String value) {
        this.options.put(key, value);
        return this;
    }

    public Options put(final String key, final int value) {
        this.options.put(key, String.valueOf(value));
        return this;
    }

    public Options put(final String key, final float value) {
        this.options.put(key, String.valueOf(value));
        return this;
    }

    public Options put(final String key, final boolean value) {
        this.options.put(key, String.valueOf(value));
        return this;
    }

    public void removeOption(final String key) {
        this.options.remove(key);
    }

    public String getJavaScriptOptions() {
        StringBuffer sb = new StringBuffer("{");
        int count = 0;

        for (Entry<String, String> entry : this.options.entrySet()) {
            String key = entry.getKey();
            sb.append(key);
            sb.append(":");
            sb.append(entry.getValue());
            if (count < this.options.size() - 1) {
                sb.append(",\n");
            }
            count++;
        }

        return sb.append("}").toString();
    }

    public String getCSSOptions() {
        StringBuffer sb = new StringBuffer();
        int count = 0;

        for (Entry<String, String> entry : this.options.entrySet()) {
            String key = entry.getKey();
            sb.append(key);
            sb.append(":");
            sb.append(entry.getValue());
            if (count < this.options.size() - 1) {
                sb.append("; ");
            }
            count++;
        }
        return sb.toString();
    }

    public boolean containsKey(final Object key) {
        return this.options.containsKey(key);
    }


}
