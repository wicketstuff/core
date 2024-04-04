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
package com.googlecode.wicket.jquery.ui.interaction.resizable;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.util.io.IClusterable;

/**
 * Event listener shared by the {@link ResizablePanel} widget and the {@link ResizableBehavior}
 *
 * @author Sebastien Briquet - sebfz1
 *
 */
public interface IResizableListener extends IClusterable
{
	/**
	 * Indicates whether the 'start' event is enabled.<br>
	 * If true, the {@link #onResizeStart(AjaxRequestTarget, int, int, int, int)} event will be triggered.
	 *
	 * @return false by default
	 */
	boolean isResizeStartEventEnabled();

	/**
	 * Indicates whether the 'stop' event is enabled.<br>
	 * If true, the {@link #onResizeStop(AjaxRequestTarget, int, int, int, int)} event will be triggered.
	 *
	 * @return false by default
	 */
	boolean isResizeStopEventEnabled();

	/**
	 * Triggered when the resize event starts
	 *
	 * @param target the {@link AjaxRequestTarget}
	 * @param top the position's top value
	 * @param left the position's left value
	 * @param width the size's width value
	 * @param height the size's height value
	 *
	 * @see #isResizeStartEventEnabled()
	 */
	void onResizeStart(AjaxRequestTarget target, int top, int left, int width, int height);

	/**
	 * Triggered when the resize event stops
	 *
	 * @param target the {@link AjaxRequestTarget}
	 * @param top the position's top value
	 * @param left the position's left value
	 * @param width the size's width value
	 * @param height the size's height value
	 *
	 * @see #isResizeStopEventEnabled()
	 */
	void onResizeStop(AjaxRequestTarget target, int top, int left, int width, int height);
}
