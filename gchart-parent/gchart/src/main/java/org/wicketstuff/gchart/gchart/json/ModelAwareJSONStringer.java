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

import org.wicketstuff.gchart.gchart.options.ChartOptions;

import com.github.openjson.JSONException;
import com.github.openjson.JSONStringer;

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
