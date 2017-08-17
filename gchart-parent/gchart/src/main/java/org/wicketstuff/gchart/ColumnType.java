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

/**
 * Google Column Types, see
 * <a href="https://google-developers.appspot.com/chart/interactive/docs/reference#DataTable">DataTable</a>.
 *
 * @author Dieter Tremel
 */
public enum ColumnType implements JavaScriptable {
    // string, number, boolean, date, datetime, and timeofday
    STRING, NUMBER, BOOLEAN, DATE, DATETIME, TIMEOFDAY;

    @Override
    public String toJavaScript() {
        return toString().toLowerCase();
    }
}
