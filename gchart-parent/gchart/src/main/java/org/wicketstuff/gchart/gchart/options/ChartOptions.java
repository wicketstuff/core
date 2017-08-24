/* 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wicketstuff.gchart.gchart.options;

import org.wicketstuff.gchart.JavaScriptable;
import org.wicketstuff.gchart.Jsonable;
import org.wicketstuff.gchart.gchart.json.ModelAwareJSONObject;
import org.wicketstuff.gchart.gchart.json.ModelAwareJSONStringer;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.apache.wicket.ajax.json.JSONObject;
import org.apache.wicket.ajax.json.JSONStringer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.io.IClusterable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Specialization of {@code  HashMap<String, Object>} to hold chart options and
 * generate JSON to represent them.
 *
 * ChartOptions can be nested, so more complex settings are realized in JSON
 * output.
 *
 * For instance
 * <pre>{@code
 * ChartOptions opts = new ChartOptions("options");
 * opts.put("title", "Metar Temperatur");
 * //        opts.put("width", 400);
 * //        opts.put("height", 300);
 *
 * ChartOptions hAxisOpts = new ChartOptions();
 *
 * ChartOptions vAxisOpts = new ChartOptions();
 * ChartOptions vAxisGridline = new ChartOptions();
 * vAxisGridline.put("count", 8);
 * vAxisOpts.put("gridlines", vAxisGridline);
 * opts.put("vAxis", vAxisOpts);
 * } </pre> results in
 * {@code var options = {"vAxis":{"gridlines":{"count":8}},"title":"Metar Temperatur"};}
 * <p>
 *
 * The root instance must have a name so an identifier for the variable ist
 * given.
 *
 * Values can be models ({@code instanceof IModel}). If the ChartOptions are
 * rendered to JavaScript by {@link #toJavaScript()},
 * {@code getObject()} will be applied automatically to the models to retreive
 * the value that goes to JavaScript. This is even possible if values are
 * JSONObjects with Models as values, since a subclass of {@link JSONStringer}
 * is used, that dereferences the models before rendering the value.
 *
 * @see ModelAwareJSONStringer
 *
 * @author Dieter Tremel
 */
public class ChartOptions extends HashMap<String, Object> implements IClusterable, JavaScriptable, Jsonable {

    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger(ChartOptions.class);

    private String name;

    /**
     * Default constructor.
     */
    public ChartOptions() {
        this.name = null;
    }

    /**
     * Constructor defining name.
     *
     * @param name The name is used to render a variable name in javascript.
     */
    public ChartOptions(String name) {
        this.name = name;
    }

    public ChartOptions(ChartOptions m) {
        super(m);
        this.name = m.getName();
    }

    /**
     * Constructor from another map.
     *
     * @param name The name is used to render a variable name in javascript.
     * @param m Map to initialize from.
     */
    public ChartOptions(String name, Map<? extends String, ? extends Object> m) {
        super(m);
        this.name = name;
    }

    /**
     * Build an instance from a JSON string for instance as seen in tutorials.
     * For users, that find coding in JSON easier than building nested
     * ChartOptions, this my be an additional offer. Implemented as
     * {@code fromJson(new JSONObject(json));}, see {@link #fromJson(org.apache.wicket.ajax.json.JSONObject)
     * }.
     *
     * @param json JSON string like
     * {@code {'title':'How Much Pizza I Ate Last Night','width':400,'height':300}}
     * @return New instance with data from the JSON String.
     */
    public static ChartOptions fromJson(String json) {
        return fromJson(new JSONObject(json));
    }

    /**
     * Build instance from a JSONobject.
     *
     * @param json JSON object to build map from. If this contains other
     * JSONObjects nested, a corresponding nested structure of ChartOptions will
     * be built.
     * @return New instance with data from the JSONObject.
     */
    public static ChartOptions fromJson(JSONObject json) {
        ChartOptions chartOptions = new ChartOptions();
        for (String key : json.keySet()) {
            Object obj = json.get(key);
            if (obj instanceof JSONObject) {
                chartOptions.put(key, fromJson((JSONObject) obj));
            } else {
                chartOptions.put(key, obj);
            }
        }
        return chartOptions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ChartOptions other = (ChartOptions) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.name) + 23 * super.hashCode();
        return hash;
    }

    /**
     * Create a deep copy of the instance, where all models
     * ({@code instanceof IModel}) are replaced with there value by
     * {@code getObject()}.
     *
     * @return Deep copy with all models replaced.
     */
    public ChartOptions getModelObjects() {
        ChartOptions opts = new ChartOptions(name);
        for (Entry<String, Object> e : entrySet()) {
            String key = e.getKey();
            Object value = e.getValue();
            if (value instanceof ChartOptions) {
                opts.put(key, ((ChartOptions) value).getModelObjects());
            } else if (value instanceof IModel) {
                opts.put(key, ((IModel) value).getObject());
            } else {
                opts.put(key, value);
            }
        }
        return opts;
    }

    @Override
    public String toJavaScript() {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name must not be null or empty for Javascript generation of ChartOptions.");
        }
        return "var " + name + " = " + toJSON() + ";\n";
    }

    @Override
    public JSONObject toJSON() {
        return new ModelAwareJSONObject(this.getModelObjects());
    }
}
