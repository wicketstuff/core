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

import com.github.openjson.JSONArray;


/**
 * Data Helper to build Bar Charts.
 *
 * @param <T> Type of number. Ex.: Double, Integer, Float, etc.
 * @author inaiat
 */
public class BarData<T extends Number> extends AbstractCollectionData<Collection<T>> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -2087356275172825289L;

	/** The data. */
	private Collection<Collection<T>> data = new ArrayList<Collection<T>>();

    /**
     * Instantiates a new bar data.
     */
    public BarData() {
    }

    /**
     * Instantiates a new bar data.
     *
     * @param values the values
     */
    public BarData(Collection<T>... values) {
        addValues(values);
    }

    /* (non-Javadoc)
     * @see org.wicketstuff.jqplot.lib.data.ChartData#getData()
     */
    @Override
	public Collection<Collection<T>> getData() {
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
        for(Collection<T> series : data)
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
        JSONArray jsonArray = new JSONArray(data);
        return jsonArray.toString();
    }


}
