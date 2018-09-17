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
package org.wicketstuff.jqplot.lib.data.item;

import java.io.Serializable;

/**
 * LineSeriesItem's data.
 *
 * @param <I> index of series
 * @param <V> value of series
 * @author inaiat
 */
public class LineSeriesItem<I extends Serializable, V extends Serializable> implements BaseItem {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The index. */
	private I index;
	
	/** The value. */
	private V value;	
	
	/**
	 * Instantiates a new line series item.
	 */
	public LineSeriesItem() {
	}
	
	/**
	 * Instantiates a new line series item.
	 *
	 * @param index the index
	 * @param value the value
	 */
	public LineSeriesItem(I index, V value) {
		this.index = index;
		this.value = value;
	}
	
	/**
	 * Gets the index.
	 *
	 * @return the index
	 */
	public I getIndex() {
		return index;
	}
	
	/**
	 * Sets the index.
	 *
	 * @param index the new index
	 */
	public void setIndex(I index) {
		this.index = index;
	}
	
	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public V getValue() {
		return value;
	}
	
	/**
	 * Sets the value.
	 *
	 * @param value the new value
	 */
	public void setValue(V value) {
		this.value = value;
	}	

}
