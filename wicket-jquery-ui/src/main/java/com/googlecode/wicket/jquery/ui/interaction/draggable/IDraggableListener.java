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
package com.googlecode.wicket.jquery.ui.interaction.draggable;

import org.apache.wicket.ajax.AjaxRequestTarget;

/**
 * TODO javadoc
 * @author Sebastien Briquet - sebfz1
 *
 */
interface IDraggableListener
{
	/**
	 * Indicates whether the 'stop' event is enabled.<br />
	 * If true, the {@link #onDragStop(AjaxRequestTarget)} event will be triggered.
	 *
	 * @return false by default
	 */
	boolean isStopEventEnabled();

	/**
	 * Triggered when the drag starts
	 * @param target the {@link AjaxRequestTarget}
	 */
	void onDragStart(AjaxRequestTarget target);

	/**
	 * Triggered when the drag stops<br />
	 * @param target the {@link AjaxRequestTarget}
	 * @see #isStopEventEnabled()
	 */
	void onDragStop(AjaxRequestTarget target);
}
