/*
 * Copyright 2017 Dieter Tremel.
 * http://www.tremel-computer.de
 * All rights, if not explicitly granted, reserved.
 */
package org.wicketstuff.gchart.gchart.json;

import java.util.Map;
import org.apache.wicket.ajax.json.JSONException;
import org.apache.wicket.ajax.json.JSONObject;
import org.apache.wicket.ajax.json.JSONTokener;

/**
 *
 * @author Dieter Tremel
 */
public class ModelAwareJSONObject extends JSONObject {

    public ModelAwareJSONObject() {
    }

    public ModelAwareJSONObject(Map copyFrom) {
        super(copyFrom);
    }

    public ModelAwareJSONObject(JSONTokener readFrom) throws JSONException {
        super(readFrom);
    }

    public ModelAwareJSONObject(String json) throws JSONException {
        super(json);
    }

    public ModelAwareJSONObject(JSONObject copyFrom, String[] names) throws JSONException {
        super(copyFrom, names);
    }

    public ModelAwareJSONObject(Object bean) throws JSONException {
        super(bean);
    }

    @Override
    public String toString() {
        return toString(new ModelAwareJSONStringer());
    }
}
