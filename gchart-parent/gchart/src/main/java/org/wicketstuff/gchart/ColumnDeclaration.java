/* 
 * Copyright 2017 Dieter Tremel.
 *
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
package org.wicketstuff.gchart;

import java.util.HashMap;
import java.util.Map;
import org.apache.wicket.ajax.json.JSONObject;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

/**
 * Abstraction of a data column defintion. The more exotic fields like pattern,
 * role and properties are handled by the setters.
 *
 * @author Dieter Tremel
 */
public class ColumnDeclaration implements Jsonable {

    private ColumnType type;
    private String id = null;
    private IModel<String> labelModel = null;
    private String pattern = null;
    private ColumnRole role = null;
    private final Map<String, Object> properties = new HashMap<>(8);

    /**
     * Default constructor.
     *
     * @param type Datatype of column.
     */
    public ColumnDeclaration(ColumnType type) {
        this.type = type;
        this.labelModel = null;
    }

    /**
     * Default constructor.
     *
     * @param id Id of Column.
     * @param type Datatype of column.
     */
    public ColumnDeclaration(String id, ColumnType type) {
        this.id = id;
        this.type = type;
        this.labelModel = null;
    }

    /**
     * Constructor with label as String.
     *
     * @param type Datatype of column.
     * @param label Label for description of column.
     */
    public ColumnDeclaration(ColumnType type, String label) {
        this.type = type;
        this.labelModel = Model.of(label);
    }

    /**
     * Constructor with label.
     *
     * @param type Datatype of column.
     * @param labelModel Model of Label for description of column.
     */
    public ColumnDeclaration(ColumnType type, IModel<String> labelModel) {
        this.type = type;
        this.labelModel = labelModel;
    }

    /**
     * Constructor with label.
     *
     * @param id Id of Column.
     * @param type Datatype of column.
     * @param labelModel Model of Label for description of column.
     */
    public ColumnDeclaration(String id, ColumnType type, IModel<String> labelModel) {
        this.id = id;
        this.type = type;
        this.labelModel = labelModel;
    }

    /**
     * Constructor with role. Example:
     * <pre>{@code new ColumnDeclaration(ColumnType.STRING, ColumnRole.ANNOTATION);}</pre>
     *
     * @param type Datatype of column.
     * @param role Role of column.
     */
    public ColumnDeclaration(ColumnType type, ColumnRole role) {
        this.type = type;
        this.role = role;

    }

    /**
     * Constructor with role. Example:
     * <pre>{@code new ColumnDeclaration(ColumnType.STRING, ColumnRole.ANNOTATION);}</pre>
     *
     * @param id Id of Column.
     * @param type Datatype of column.
     * @param role Role of column.
     */
    public ColumnDeclaration(String id, ColumnType type, ColumnRole role) {
        this.id = id;
        this.type = type;
        this.role = role;

    }

    /**
     * Build an instance from a JSON string for instance as seen in tutorials.
     * For users, that find coding in JSON easier than building nested
     * ChartOptions, this my be an additional offer. Implemented as
     * {@code fromJson(new JSONObject(json));}, see {@link #fromJSON(org.apache.wicket.ajax.json.JSONObject)
     * }.
     *
     * @param json JSON string like
     * {@code {id: 'task', label: 'Employee Name', type: 'string'}}
     * @return New instance with data from the JSON String.
     */
    public static ColumnDeclaration fromJSON(String json) {
        return fromJSON(new JSONObject(json));
    }

    /**
     * Build instance from a JSONobject.
     *
     * @param jsonObj JSON object to build ColumnDeclaration from. If this
     * contains "properties" nested, it will try to fill them in the instance.
     * @return New instance with data from the JSONObject.
     */
    public static ColumnDeclaration fromJSON(JSONObject jsonObj) {
        if (!jsonObj.has("type")) {
            throw new IllegalArgumentException("The JSON for a ColumnDeclaration must at least have a type definition");
        }
        ColumnType type = ColumnType.valueOf(jsonObj.getString("type").toUpperCase());
        ColumnDeclaration columnDeclaration = new ColumnDeclaration(type);
        if (jsonObj.has("label")) {
            columnDeclaration.setLabelModel(Model.of(jsonObj.getString("label")));
        }
        if (jsonObj.has("pattern")) {
            columnDeclaration.setPattern(jsonObj.getString("pattern"));
        }
        if (jsonObj.has("role")) {
            columnDeclaration.setRole(ColumnRole.valueOf(jsonObj.getString("role")));
        }
        if (jsonObj.has("properties")) {
            if (jsonObj.get("properties") instanceof JSONObject) {
                JSONObject props = (JSONObject) jsonObj.get("properties");
                for (String name : JSONObject.getNames(props)) {
                    columnDeclaration.getProperties().put(name, props.get(name));
                }
            }
        }
        return columnDeclaration;
    }

    public ColumnType getType() {
        return type;
    }

    public void setType(ColumnType type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return labelModel.getObject();
    }

    public IModel<String> getLabelModel() {
        return labelModel;
    }

    public void setLabelModel(IModel<String> labelModel) {
        this.labelModel = labelModel;
    }

    public ColumnRole getRole() {
        return role;
    }

    /**
     * Set role of column. See
     * <a href="https://developers.google.com/chart/interactive/docs/roles">Column
     * Roles</a>.
     *
     * @param role Role of Column.
     */
    public void setRole(ColumnRole role) {
        this.role = role;
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    public String getPattern() {
        return pattern;
    }

    /**
     * Just given for completeness, Google Charts does not use this value at the moment.
     * Use definition of options of axis ticks with value and format
     * or {@code hAxis.format} instead to format axis values.
     *
     * @param pattern Pattern for Column.
     */
    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public JSONObject toJSON() {
        // {id: 'hours', label: 'Hours per Day', type: 'number'}
        JSONObject colDef = new JSONObject();

        if (id != null) {
            colDef.put("id", getId());
        }
        if (labelModel != null) {
            colDef.put("label", getLabel());
        }
        if (pattern != null) {
            colDef.put("pattern", getPattern());
        }
        if (role != null) {
            colDef.put("role", getRole().toJavaScript());
        }
        if (properties != null && !properties.isEmpty()) {
            colDef.put("p", new JSONObject(properties));
        }
        colDef.put("type", getType().toJavaScript());

        return colDef;
    }
}
