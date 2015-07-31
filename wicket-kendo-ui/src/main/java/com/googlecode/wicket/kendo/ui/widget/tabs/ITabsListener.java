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
package com.googlecode.wicket.kendo.ui.widget.tabs;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.util.io.IClusterable;

/**
 * Event listener shared by the {@link TabbedPanel} widget and the {@link TabsBehavior}<br />
 * <br />
 * <b>Warning: </b> At least one event should be enabled for the {@link AjaxTab} to load.
 * 
 * @author Sebastien Briquet - sebfz1
 * @since 6.19.0
 */
public interface ITabsListener extends IClusterable
{
	/**
	 * Indicates whether the 'select' event is enabled.<br />
	 * If true, the {@link #onSelect(AjaxRequestTarget, int, ITab)} event will be triggered.
	 *
	 * @return true by default
	 */
	boolean isSelectEventEnabled();

	/**
	 * Indicates whether the 'show' event is enabled.<br />
	 * If true, the {@link #onShow(AjaxRequestTarget, int, ITab)} event will be triggered.
	 *
	 * @return false by default
	 */
	boolean isShowEventEnabled();

	/**
	 * Indicates whether the 'activate' event is enabled.<br/>
	 * If true, the {@link #onActivate(AjaxRequestTarget, int, ITab)} event will be triggered.
	 *
	 * @return false by default
	 */
	boolean isActivateEventEnabled();

	/**
	 * Triggered before a tab is selected.
	 *
	 * @param target the {@link AjaxRequestTarget}
	 * @param index the tab index that triggered this event
	 * @param tab the {@link ITab} that corresponds to the index
	 * @see #isSelectEventEnabled()
	 */
	void onSelect(AjaxRequestTarget target, int index, ITab tab);

	/**
	 * Triggered just after a tab is being made visible, but before the end of the animation.
	 *
	 * @param target the {@link AjaxRequestTarget}
	 * @param index the tab index that triggered this event
	 * @param tab the {@link ITab} that corresponds to the index
	 * @see #isShowEventEnabled()
	 */
	void onShow(AjaxRequestTarget target, int index, ITab tab);

	/**
	 * Triggered after a tab is being made visible and its animation complete
	 *
	 * @param target the {@link AjaxRequestTarget}
	 * @param index the tab index that triggered this event
	 * @param tab the {@link ITab} that corresponds to the index
	 * @see #isActivateEventEnabled()
	 */
	void onActivate(AjaxRequestTarget target, int index, ITab tab);
}
