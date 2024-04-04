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
package com.googlecode.wicket.kendo.ui.layout;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.util.io.IClusterable;

/**
 * Event listener shared by the {@link ResponsiveMarkupContainer} widget and the {@link ResponsiveBehavior}
 * 
 * @author Sebastien Briquet - sebfz1
 * @since 6.21.0
 * @since 7.1.0
 */
public interface IResponsiveListener extends IClusterable
{
	/**
	 * Indicates whether the 'open' event is enabled.<br>
	 * If true, the {@link #onOpen(AjaxRequestTarget)} event will be triggered.
	 *
	 * @return false by default
	 */
	boolean isOpenEventEnabled();

	/**
	 * Indicates whether the 'close' event is enabled.<br>
	 * If true, the {@link #onClose(AjaxRequestTarget)} event will be triggered.
	 *
	 * @return false by default
	 */
	boolean isCloseEventEnabled();

	/**
	 * Triggered when the responsive panel opens
	 *
	 * @param target the {@link AjaxRequestTarget}
	 * @see #isOpenEventEnabled()
	 */
	void onOpen(AjaxRequestTarget target);

	/**
	 * Triggered when the responsive panel closes
	 *
	 * @param target the {@link AjaxRequestTarget}
	 * @see #isCloseEventEnabled()
	 */
	void onClose(AjaxRequestTarget target);
}
