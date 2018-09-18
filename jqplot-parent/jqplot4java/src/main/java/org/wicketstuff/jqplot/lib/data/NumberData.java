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

/**
 * Helper to handle Data Number with generics.
 *
 * @param <T> the generic type
 * @author inaiat
 */
public abstract class NumberData<T extends Number> implements ChartData<T> {


	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 3043833458517563918L;

	/** The data. */
	private T data;

    /**
     * Instantiates a new number data.
     *
     * @param value the value
     */
    public NumberData(T value) {
        data = value;
    }

    /* (non-Javadoc)
     * @see org.wicketstuff.jqplot.lib.data.ChartData#getData()
     */
    @Override
	public T getData() {
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
        return data.intValue();
    }

    /**
     * Sets the value.
     *
     * @param value the new value
     */
    public void setValue(T value) {
        this.data = value;
    }

    /* (non-Javadoc)
     * @see org.wicketstuff.jqplot.lib.data.ChartData#toJsonString()
     */
    @Override
	public abstract String toJsonString();
}
