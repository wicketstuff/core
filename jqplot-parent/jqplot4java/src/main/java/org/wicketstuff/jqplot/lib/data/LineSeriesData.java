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

import org.wicketstuff.jqplot.lib.data.item.LineSeriesItem;

import com.github.openjson.JSONArray;

/**
 * Data Helper to build Line Series Charts.
 *
 * @param <I> {@link LineSeriesItem} Index type
 * @param <V> {@link LineSeriesItem} Value type
 */
public class LineSeriesData<I extends Number,V extends Number> extends AbstractCollectionData<Collection<LineSeriesItem<I, V>>> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -8490476851004459871L;

	/** The data. */
	private List<Collection<LineSeriesItem<I, V>>> data = new ArrayList<Collection<LineSeriesItem<I, V>>>();

	/* (non-Javadoc)
	 * @see org.wicketstuff.jqplot.lib.data.ChartData#getData()
	 */
	@Override
	public Collection<Collection<LineSeriesItem<I, V>>> getData() {
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
        int ret = 0;
        for(Collection<LineSeriesItem<I,V>> series : data)
        {
            if(series.size() > 0)
                ++ret;
        }
        return ret;
    }

	/* (non-Javadoc)
	 * @see org.wicketstuff.jqplot.lib.data.ChartData#toJsonString()
	 */
	@Override
	public String toJsonString() {
    	JSONArray jsonArray = new JSONArray();
        for (Collection<LineSeriesItem<I, V>> col : data) {
        	JSONArray serie = new JSONArray();
        	for (LineSeriesItem<I, V> lineSeriesItem : col) {
                JSONArray itemArray = new JSONArray();
                itemArray.put(lineSeriesItem.getIndex());
                itemArray.put(lineSeriesItem.getValue());
                serie.put(itemArray);
			}
        	jsonArray.put(serie);

        }
        return jsonArray.toString();
	}

}
