package org.wicketstuff.egrid.attribute;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public abstract class BaseHTMLAttribute implements Serializable, Attribute {

    @Serial
    private static final long serialVersionUID = 1L;

    private Map<String, String> attributes = null;

    public BaseHTMLAttribute() {
        attributes = new HashMap<String, String>();
    }

    protected final void put(final String key, final String value) {
        attributes.put(key, value);
    }

    public final Object get(final Object key) {
        return attributes.get(key);
    }

    public Set<Entry<String, String>> attributeEntries() {
        return attributes.entrySet();
    }

    public final Set<String> getAttributeKeys() {
        return attributes.keySet();
    }
}
