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

/**
 * Roles of columns in chart. Use in {@link ColumnDeclaration}.
 * See <a href="https://developers.google.com/chart/interactive/docs/roles">Column Roles</a>.
 *
 * @author Dieter Tremel
 */
public enum ColumnRole {
    ANNOTATION,
    ANNOTATIONTEXT,
    CERTAINTY,
    EMPHASIS,
    INTERVAL,
    SCOPE,
    STYLE,
    TOOLTIP;

    public String toJavaScript() {
        return toString().toLowerCase();
    }
}
