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
import java.util.Collection;
import org.apache.wicket.util.io.IClusterable;

import com.github.openjson.JSONArray;
import com.github.openjson.JSONObject;

/**
 * Modelling one row of data as List. Elements can be basic objects (eg. String,
 * Number, ..., see {@link ColumnType}) for simple use cases or {@link DataCell}
 * instances for special uses.
 *
 * @author Dieter Tremel
 */
public class DataRow extends ArrayList<Object> implements IClusterable, Jsonable {

    private static final long serialVersionUID = 1L;

    public DataRow(int initialCapacity) {
        super(initialCapacity);
    }

    public DataRow() {
    }

    public DataRow(Collection<? extends Object> c) {
        super(c);
    }

    @Override
    public JSONObject toJSON() {
        JSONObject row = new JSONObject();

        JSONArray cells = new JSONArray();
        for (Object c : this) {
            if (c instanceof DataCell) {
                cells.put(((DataCell) c).toJSON());
            } else if (//c instanceof JSONObject || c instanceof JSONArray || c instanceof JsonFunction ||
                    c == JSONObject.NULL || c == null) {
                cells.put(c);
            } else {
                final JSONObject valueObj = new JSONObject();
                valueObj.put("v", DataCell.getJsValue(c));
                cells.put(valueObj);
            }
        }
        row.put("c", cells);
        return row;
    }

}
