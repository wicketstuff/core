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

import com.github.openjson.JSONArray;

/**
 * Data Helper to build Meter Charts.
 *
 * @author inaiat
 */
public class MeterData extends NumberData<Float> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -1543265764447003656L;

    /**
     * Instantiates a new meter data.
     */
    public MeterData() {
        super(0F);
    }

    /**
     * Instantiates a new meter data.
     *
     * @param value the value
     */
    public MeterData(Float value) {
        super(value);
    }
    
    /* (non-Javadoc)
     * @see br.com.digilabs.jqplot.data.NumberData#toJsonString()
     */
    @Override
    public String toJsonString() {
        JSONArray jsonArray = new JSONArray();
        JSONArray valueArray = new JSONArray();
        valueArray.put(getData());
        jsonArray.put(valueArray);
        return jsonArray.toString();
    }
}
