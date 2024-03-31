package org.wicketstuff.egrid.attribute;

import java.util.Map.Entry;
import java.util.Set;

public interface Attribute {

    Set<String> getAttributeKeys();

    Set<Entry<String, String>> attributeEntries();
}
