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
package com.googlecode.wicket.kendo.ui.widget.window;

import org.apache.wicket.ajax.AjaxRequestTarget;

/**
 * Event listener shared by the {@link AbstractWindow} widget and the {@link WindowBehavior}
 *
 * @author Sebastien Briquet - sebfz1
 * @since 6.17.0
 */
interface IWindowListener
{
	/**
	 * Indicates whether the action events are enabled.<br/>
	 * If true, the {@link #onAction(AjaxRequestTarget, String)} event will be triggered
	 *
	 * @return false by default
	 */
	boolean isActionEventEnabled();

	/**
	 * Indicates whether the close event is enabled. Both 'X' icon and 'escape' key fire this event.<br/>
	 * If true, the {@link #onClose(AjaxRequestTarget)} event will be triggered
	 *
	 * @return false by default
	 */
	boolean isCloseEventEnabled();

	/**
	 * Triggered when an action button is clicked.
	 *
	 * @param target the {@link AjaxRequestTarget}
	 * @param action the action that fired the event
	 */
	void onAction(AjaxRequestTarget target, String action);

	/**
	 * Triggered when then the Window closes, either trough the 'X' icon or the 'escape' key ({@link #isCloseEventEnabled()} should return {@code true}), or if {@link AbstractWindow#close(AjaxRequestTarget)} has been called
	 *
	 * @param target the {@link AjaxRequestTarget}
	 */
	void onClose(AjaxRequestTarget target);
}
