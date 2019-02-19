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
import java.util.List;

import org.wicketstuff.jqplot.lib.data.item.LabeledItem;

import com.github.openjson.JSONArray;

/**
 * Data Helper to build Labeled Charts.
 *
 * @param <T> Type of number. Ex.: Double, Integer, Float, etc.
 * @author inaiat
 */
public class LabeledData<T extends Number> extends AbstractCollectionData<LabeledItem<T>> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -1202233319043142424L;

	/** The data. */
	private List<LabeledItem<T>> data = new ArrayList<>();

    /**
     * Instantiates a new labeled data.
     */
    public LabeledData() {
    }

    /**
     * Instantiates a new labeled data.
     *
     * @param values the values
     */
    public LabeledData(@SuppressWarnings("unchecked") LabeledItem<T>... values) {
        addValues(values);
    }

    /* (non-Javadoc)
     * @see org.wicketstuff.jqplot.lib.data.ChartData#toJsonString()
     */
    @Override
	public String toJsonString() {
        JSONArray jsonArray = new JSONArray();
        for (LabeledItem<T> labeledItem : data) {
            JSONArray itemArray = new JSONArray();
            itemArray.put(labeledItem.getLabel());
            itemArray.put(labeledItem.getValue());
            jsonArray.put(itemArray);
        }
        JSONArray outerArray = new JSONArray();
        outerArray.put(jsonArray);
        return outerArray.toString();
    }

    /* (non-Javadoc)
     * @see org.wicketstuff.jqplot.lib.data.ChartData#getData()
     */
    @Override
	public Collection<LabeledItem<T>> getData() {
        return data;
    }

    /**
     * Gets the size of the data.
     *
     * @return the data
     */
    @Override
	public int size()
    {
        return data.size();
    }
}
