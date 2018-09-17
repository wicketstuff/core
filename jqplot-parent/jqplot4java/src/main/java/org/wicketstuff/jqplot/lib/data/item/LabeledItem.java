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


/**
 * LabeledItem's data.
 *
 * @param <T> the generic type
 * @author inaiat
 */
public class LabeledItem<T extends Number> implements BaseItem {


	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -3747989015336492114L;

	/** The label. */
	private String label;
    
    /** The value. */
    private T value;

    /**
     * Instantiates a new labeled item.
     */
    public LabeledItem() {
    }

    /**
     * Instantiates a new labeled item.
     *
     * @param label the label
     * @param value the value
     */
    public LabeledItem(String label, T value) {
        this.label = label;
        this.value = value;
    }

    /**
     * Sets the label.
     *
     * @param label the label to set
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * Gets the label.
     *
     * @return the label
     */
    public String getLabel() {
        return label;
    }

    /**
     * Sets the value.
     *
     * @param value the value to set
     */
    public void setValue(T value) {
        this.value = value;
    }

    /**
     * Gets the value.
     *
     * @return the value
     */
    public T getValue() {
        return value;
    }
}
