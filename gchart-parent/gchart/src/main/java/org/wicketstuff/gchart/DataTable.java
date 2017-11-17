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
package org.wicketstuff.gchart;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.wicket.util.io.IClusterable;

import com.github.openjson.JSONArray;
import com.github.openjson.JSONObject;

/**
 * Abstraction of a datatable.
 *
 * @author Dieter Tremel
 */
public class DataTable implements IClusterable, Jsonable {

    private static final long serialVersionUID = 1L;

    private final String name;
    private final List<ColumnDeclaration> columnDescs;
    private final List<DataRow> rows;
    private final Map<String, Object> properties;

    public DataTable(String name, Collection<ColumnDeclaration> columDescs, Collection<DataRow> rows) {
        this.name = name;
        this.columnDescs = new ArrayList<>(columDescs);
        this.rows = new ArrayList<>(rows);
        this.properties = new HashMap<>(8);
    }

    public DataTable(String name, Collection<ColumnDeclaration> columDescs, Collection<DataRow> rows, Map<String, Object> properties) {
        this.name = name;
        this.columnDescs = new ArrayList<>(columDescs);
        this.rows = new ArrayList<>(rows);
        this.properties = properties;
    }

    /**
     * Create rows for the datatable from an array.
     *
     * @param data Two dimensional array with data series. First index over
     * rows, second index over columns. Do not include header in data, use
     * {@link ColumnDeclaration} for that.
     * @return List of DataRows suited for {@link #DataTable(java.lang.String, java.util.Collection, java.util.Collection)
     * }.
     */
    public static List<DataRow> fromArray(Object[][] data) {
        ArrayList<DataRow> rows = new ArrayList<>(data.length);
        for (Object[] arrayRow : data) {
            DataRow row = new DataRow(arrayRow.length);
            row.addAll(Arrays.asList(arrayRow));
            rows.add(row);
        }
        return rows;
    }

    public String toJavaScript() {
        StringBuilder sb = new StringBuilder();
        sb.append("var ").append(name).append(" = new google.visualization.DataTable(");
        sb.append(toJSON().toString());
        sb.append(");").append("\n");
        return sb.toString();
    }

    @Override
    public JSONObject toJSON() {
        JSONObject datatable = new JSONObject();

        // column definitions
        JSONArray cols = new JSONArray();
        for (ColumnDeclaration columnDesc : columnDescs) {
            cols.put(columnDesc.toJSON());
        }
        datatable.put("cols", cols);

        // data rows
        JSONArray rowsJSON = new JSONArray();
        for (DataRow row : rows) {
            rowsJSON.put(row.toJSON());
        }
        datatable.put("rows", rowsJSON);

        // properties
        if (properties != null && !properties.isEmpty()) {
            datatable.put("p", new JSONObject(properties));
        }

        return datatable;
    }

    public String getName() {
        return name;
    }

    public List<ColumnDeclaration> getColumnDescs() {
        return columnDescs;
    }

    public List<DataRow> getRows() {
        return rows;
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    public boolean addRow(DataRow newRow) {
        return rows.add(newRow);
    }

    public boolean addRow(Collection<Object> newRow) {
        return rows.add(new DataRow(newRow));
    }

}
