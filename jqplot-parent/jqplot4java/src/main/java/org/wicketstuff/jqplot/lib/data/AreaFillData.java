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

import com.github.openjson.JSONArray;

/**
 * Data Helper to build Area Charts.
 *
 * @param <T>
 *            Type of number. Ex.: Double, Integer, Float, etc.
 *
 * @author inaiat
 */
public class AreaFillData<T extends Number> extends
	AbstractCollectionData<List<T>> {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -2456625545492808162L;

    /** The data. */
    private List<List<T>> data = new ArrayList<List<T>>();

    /**
     * Instantiates a new area fill data.
     */
    public AreaFillData() {
    }

    /**
     * Instantiates a new area fill data.
     *
     * @param values
     *            the values
     */
    public AreaFillData(List<T>... values) {
	for (int i = 0; i < values.length; i++) {
	    List<T> list = values[i];
	    addValue(list);
	}
    }

    /*
     * (non-Javadoc)
     *
     * @see org.wicketstuff.jqplot.lib.data.ChartData#getData()
     */
    @Override
	public Collection<List<T>> getData() {
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

    /*
     * (non-Javadoc)
     *
     * @see org.wicketstuff.jqplot.lib.data.ChartData#toJsonString()
     */
    @Override
	public String toJsonString() {
        JSONArray jsonArray = new JSONArray(data);
        return jsonArray.toString();
    }
}
