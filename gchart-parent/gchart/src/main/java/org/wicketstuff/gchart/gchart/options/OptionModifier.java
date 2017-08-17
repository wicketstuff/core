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
package org.wicketstuff.gchart.gchart.options;

/**
 * Interface for a callback, that modifies a ChartOption.
 * Used for {@link OptionHelper} to modify {@link ChartOptions} that are created within a
 * deeper structure without diving into the structure.
 *
 * @author Dieter Tremel
 */
public interface OptionModifier {

    /**
     * Do modifications on the options node.
     *
     * @param options Options node to modify.
     */
    public void modify(ChartOptions options);
}
