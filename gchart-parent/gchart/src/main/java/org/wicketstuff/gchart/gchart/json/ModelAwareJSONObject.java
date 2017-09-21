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
