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
package com.googlecode.wicket.kendo.ui.interaction.droppable;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;

import com.googlecode.wicket.kendo.ui.interaction.draggable.DraggableBehavior;

/**
 * Event listener shared by the {@link Droppable} widget and the {@link DroppableBehavior}
 *
 * @author Sebastien Briquet - sebfz1
 *
 */
interface IDroppableListener
{
	/**
	 * Indicates whether the 'enter' event is enabled.<br />
	 * If true, the {@link #onDragEnter(AjaxRequestTarget, Component)} event will be triggered.
	 * 
	 * @return false by default
	 */
	boolean isDragEnterEventEnabled();

	/**
	 * Indicates whether the 'leave' event is enabled.<br />
	 * If true, the {@link #onDragLeave(AjaxRequestTarget, Component)} event will be triggered.
	 * 
	 * @return false by default
	 */
	boolean isDragLeaveEventEnabled();

	/**
	 * Triggered when a component with {@link DraggableBehavior} enters the droppable area
	 * 
	 * @param target the {@link AjaxRequestTarget}
	 * @param component the component with {@link DraggableBehavior}
	 * @see #isDragEnterEventEnabled()
	 */
	void onDragEnter(AjaxRequestTarget target, Component component);

	/**
	 * Triggered when a component with {@link DraggableBehavior} leaves the droppable area
	 * 
	 * @param target the {@link AjaxRequestTarget}
	 * @param component the component with {@link DraggableBehavior}
	 * @see #isDragLeaveEventEnabled()
	 */
	void onDragLeave(AjaxRequestTarget target, Component component);

	/**
	 * Triggered when a component with {@link DraggableBehavior} has been dropped
	 * 
	 * @param target the {@link AjaxRequestTarget}
	 * @param component the component with {@link DraggableBehavior}
	 */
	void onDrop(AjaxRequestTarget target, Component component);
}
