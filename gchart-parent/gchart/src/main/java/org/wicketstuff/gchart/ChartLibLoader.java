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

import org.apache.wicket.markup.head.HeaderItem;

/**
 * Interface for implementation of central lib and package loading mechanism.
 * The implementation should load the Google chart lib only once and load every
 * package needed by all charts added. If a Maps API key is set in one of the charts,
 * it is also added.
 *
 * @author Dieter Tremel
 */
public interface ChartLibLoader {

    /**
     * Add a chart to the loader, so it's package is loaded to.
     *
     * @param chart Chart to add.
     * @return True if Chart is added.
     */
    public boolean addChart(Chart chart);

    /**
     * Remove a previous added chart.
     *
     * @param chart Chart to remove.
     * @return True if the Chart was managed and is removed now, false otherwise.
     */
    public boolean removeChart(Chart chart);

    /**
     * Get the HeaderItem the loader adds, only to make dependencies to.
     *
     * @return HeaderItem that the loader contributes.
     */
    public HeaderItem getHeaderItem();
}
