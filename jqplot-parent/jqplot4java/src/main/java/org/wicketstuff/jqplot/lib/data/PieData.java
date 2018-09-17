/*
 *  Copyright 2011 Inaiat H. Moraes.
 * 
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  under the License.
 */
package org.wicketstuff.jqplot.lib.data;

import java.util.ArrayList;
import java.util.Collection;

import org.wicketstuff.jqplot.lib.data.item.LabeledItem;

import com.github.openjson.JSONArray;

/**
 * Data Helper to build Pie Charts.
 *
 * @param <T> the generic type
 * @author inaiat
 */
public class PieData<T extends Number> extends AbstractCollectionData<LabeledItem<T>> {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 5789136755213249502L;
	
    /** The data. */
    private Collection<LabeledItem<T>> data = new ArrayList<LabeledItem<T>>();

    /* (non-Javadoc)
     * @see br.com.digilabs.jqplot.data.ChartData#toJsonString()
     */
    public String toJsonString() {
        JSONArray outerArray = new JSONArray();
        JSONArray jsonArray = new JSONArray();
        for (LabeledItem<T> item : data) {
            JSONArray itemArray = new JSONArray();
            itemArray.put(item.getLabel());
            itemArray.put(item.getValue());
            jsonArray.put(itemArray);
        }
        outerArray.put(jsonArray);
        return outerArray.toString();
    }

    /* (non-Javadoc)
     * @see br.com.digilabs.jqplot.data.ChartData#getData()
     */
    public Collection<LabeledItem<T>> getData() {
        return data;
    }

    /**
     * Gets the size of the data.
     *
     * @return the data
     */
    public int size()
    {
        return data.size();
    }
}
