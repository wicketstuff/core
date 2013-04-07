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
package com.googlecode.wicket.jquery.ui.interaction.droppable;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;

import com.googlecode.wicket.jquery.ui.interaction.draggable.DraggableBehavior;

/**
 * TODO javadoc
 * @author Sebastien Briquet - sebfz1
 *
 */
interface IDroppableListener
{
	/**
	 * Indicates whether the 'over' event is enabled.<br />
	 * If true, the {@link #onOver(AjaxRequestTarget, Component)} event will be triggered.
	 * @return false by default
	 */
	boolean isOverEventEnabled();

	/**
	 * Indicates whether the 'exit' (or 'out') event is enabled.<br />
	 * If true, the {@link #onExit(AjaxRequestTarget, Component)} event will be triggered.
	 * @return false by default
	 */
	boolean isExitEventEnabled();

	/**
	 * Triggered when a component with {@link DraggableBehavior} has been dropped
	 * @param target the {@link AjaxRequestTarget}
	 * @param component the component with {@link DraggableBehavior}
	 */
	void onDrop(AjaxRequestTarget target, Component component);

	/**
	 * Triggered when a component with {@link DraggableBehavior} overs the droppable area
	 * @param target the {@link AjaxRequestTarget}
	 * @param component the component with {@link DraggableBehavior}
	 * @see #isOverEventEnabled()
	 */
	void onOver(AjaxRequestTarget target, Component component);

	/**
	 * Triggered when a component with {@link DraggableBehavior} exits the droppable area
	 * @param target the {@link AjaxRequestTarget}
	 * @param component the component with {@link DraggableBehavior}
	 * @see #isExitEventEnabled()
	 */
	void onExit(AjaxRequestTarget target, Component component);
}
