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
package com.googlecode.wicket.jquery.ui.widget.menu;

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.io.IClusterable;

/**
 * Interface used to represent a single item in a {@link Menu}
 *
 * @see AbstractMenuItem
 *
 * @author Sebastien Briquet - sebfz1
 * @since 1.4.2
 * @since 6.2.2
 */
public interface IMenuItem extends IClusterable
{
	/**
	 * Gets the menu-item markup id
	 * @return the menu-item markup id
	 */
	String getId();

	/**
	 * Gets the menu-item title
	 * @return the menu-item title
	 */
	IModel<String> getTitle();

	/**
	 * Gets the icon css class being displayed in the {@link Menu}
	 * @return the icon css class
	 */
	String getIcon();

	/**
	 * Indicates whether the menu-item is enabled
	 * @return true or false
	 */
	boolean isEnabled();

	/**
	 * Gets the {@link List} of submenu-items
	 * @return the {@link List} of submenu-items
	 */
	List<IMenuItem> getItems();

	/**
	 * Triggered when the menu-item is clicked
	 * @param target the {@link AjaxRequestTarget}
	 */
	void onClick(AjaxRequestTarget target);
}
