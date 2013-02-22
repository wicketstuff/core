package org.wicketstuff.jwicket;


import org.apache.wicket.request.Request;
import org.apache.wicket.util.string.Strings;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class SpecialKeys implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final JQueryResourceReference specialKeysJs = new JQueryResourceReference(SpecialKeys.class, "SpecialKeys.js", JQueryResourceReferenceType.NOT_OVERRIDABLE);


    public SpecialKeys(final SpecialKey... additionalSpecialKeys) {
        Collections.addAll(this.specialKeys, additionalSpecialKeys);
    }

    public SpecialKeys(final Request request) {
        String rawKeys = request.getRequestParameters().getParameterValue("keys").toString();
        if (rawKeys != null && rawKeys.length() > 0) {
            String[] strings = Strings.split(rawKeys, ',');
            for (String string : strings)
                this.specialKeys.add(SpecialKey.getSpecialKey(string));
        }
    }


    private final List<SpecialKey> specialKeys = new ArrayList<SpecialKey>();

    public List<SpecialKey> getSpecialKeys() {
        return this.specialKeys;
    }

    public void addSpecialKeys(final SpecialKey... additionalSpecialKeys) {
        Collections.addAll(this.specialKeys, additionalSpecialKeys);
    }

    public void removeSpecialKeys(final SpecialKey... additionalSpecialKeys) {
        for (SpecialKey key : additionalSpecialKeys)
            this.specialKeys.remove(key);
    }


    public boolean is(final SpecialKey... keys) {
        for (SpecialKey key : keys)
            if (!this.specialKeys.contains(key))
                return false;
        return true;
    }


    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (SpecialKey key : this.specialKeys) {
            if (sb.length() > 0)
                sb.append(',');
            sb.append(key);
        }


        return sb.toString();
    }
}
