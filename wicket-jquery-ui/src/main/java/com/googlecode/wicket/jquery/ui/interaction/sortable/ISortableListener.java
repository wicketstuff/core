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

/**
 * Event listener shared by the {@link Sortable} widget and the {@link SortableBehavior}
 *
 * @author Sebastien Briquet - sebfz1
 */
interface ISortableListener //TODO remove <T>
{
	/**
	 * Triggered when a selection has been made (stops)
	 *
	 * @param target the {@link AjaxRequestTarget}
	 * @param index the index if the item that has been sorted
	 * @param position the item's new position
	 */
	void onSort(AjaxRequestTarget target, int index, int position);
}
