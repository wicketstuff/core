/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.googlecode.wicket.jquery.ui.interaction.sortable;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.util.io.IClusterable;

/**
 * Event listener shared by the {@link Sortable} widget and the {@link SortableBehavior}
 *
 * @param <T> the type of the model object
 * @author Sebastien Briquet - sebfz1
 */
public interface ISortableListener<T> extends IClusterable
{
	/**
	 * Indicates whether the 'receive' event is enabled.<br>
	 * If true, the {@link #onReceive(AjaxRequestTarget, Object, int)} event will be triggered.
	 * 
	 * @return false by default
	 */
	boolean isOnReceiveEnabled();

	/**
	 * Indicates whether the 'remove' event is enabled.<br>
	 * If true, the {@link #onRemove(AjaxRequestTarget, Object)} event will be triggered.
	 * 
	 * @return false by default
	 */
	boolean isOnRemoveEnabled();

	/**
	 * Triggered when the user stopped sorting and the DOM position has changed.
	 *
	 * @param target the {@link AjaxRequestTarget}
	 * @param item the item that has been sorted
	 * @param index the item's new index (zero based)
	 */
	void onUpdate(AjaxRequestTarget target, T item, int index);

	/**
	 * Triggered when a connected sortable list has received an item from another list.
	 * 
	 * @param target the {@link AjaxRequestTarget}
	 * @param item the item that has been received
	 * @param index the item's new index (zero based)
	 */
	void onReceive(AjaxRequestTarget target, T item, int index);

	/**
	 * Triggered when a sortable item has been dragged out from the list and into another.
	 * 
	 * @param target the {@link AjaxRequestTarget}
	 * @param item the item that has been received
	 */
	void onRemove(AjaxRequestTarget target, T item);
}
