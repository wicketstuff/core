/*
 * Copyright 2017 Dieter Tremel.
 * http://www.tremel-computer.de
 * All rights, if not explicitly granted, reserved.
 */
package org.wicketstuff.gchart.gchart.json;

import org.apache.wicket.ajax.json.JSONException;
import org.apache.wicket.ajax.json.JSONStringer;
import org.wicketstuff.gchart.gchart.options.ChartOptions;
import org.apache.wicket.model.IModel;

/**
 * Subclass of {@link JSONStringer}, that dereferences models ({@code instanceof IModel})
 * before rendering to JSON.
 * Useful when using JSONObject inside complex nested structures of {@link ChartOptions},
 * the value can be in model to allow dynamic creation or lazy loading.
 *
 * @author Dieter Tremel
 */
public class ModelAwareJSONStringer extends JSONStringer {

    @Override
    public JSONStringer value(Object value) throws JSONException {
        if (value instanceof IModel) {
            return super.value(((IModel) value).getObject());
        } else {
            return super.value(value);
        }
    }

}
