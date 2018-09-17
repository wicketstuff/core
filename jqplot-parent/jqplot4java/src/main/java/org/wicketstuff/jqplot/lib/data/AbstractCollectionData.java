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

import java.util.Arrays;
import java.util.Collection;

/**
 * 
 * Abstract class to help build end charts that uses collections.
 * 
 * @author inaiat
 */
public abstract class AbstractCollectionData<T> implements ChartData<Collection<T>>  {        
    
    private static final long serialVersionUID = 7797681985474175237L;

	/**
     * Add values to collection data
     * @param value Collection of values
     */
    public void addValues(Collection<T> value) {
        getData().addAll(value);
    }

    /**
	 * Add values to collection data
	 * @param values Collection of values
     */
    public void addValues(T... values) {
        getData().addAll(Arrays.asList(values));
    }

    /**
	 * Add values to collection data
	 * @param value generic value
     */
    public void addValue(T value) {
        getData().add(value);
    }    
    
}
