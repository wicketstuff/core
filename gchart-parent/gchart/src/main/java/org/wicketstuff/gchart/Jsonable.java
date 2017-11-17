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

import com.github.openjson.JSONObject;

/**
 * Interface for object that can render a JSON representation of an instance.
 * Makes use of Wicket JSON library, see package {@code com.github.openjson}.
 *
 * @author Dieter Tremel
 */
public interface Jsonable {

    /**
     * Create the JSON serialization of the instance.
     *
     * @return Json object for the instance.
     */
    public JSONObject toJSON();
}
