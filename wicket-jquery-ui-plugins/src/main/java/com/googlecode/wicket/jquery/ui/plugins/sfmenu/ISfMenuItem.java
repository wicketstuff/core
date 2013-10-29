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
package com.googlecode.wicket.jquery.ui.plugins.sfmenu;

import java.util.List;

import org.apache.wicket.Page;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.io.IClusterable;

/**
 * Interface used to represent a single item in a {@link SfMenu}, adapted for Superfish
 *
 * @see AbstractSfMenuItem
 * @author Ludger Kluitmann - JavaLuigi
 * @author Sebastien Briquet - sebfz1
 * @since 6.12.0
 */
public interface ISfMenuItem extends IClusterable
{
	/**
	 * Gets the menu-item title
	 *
	 * @return the menu-item title
	 */
	IModel<String> getTitle();

	/**
	 * Gets the {@link List} of submenu-items
	 *
	 * @return the {@link List} of submenu-items
	 */
	List<ISfMenuItem> getItems();

	/**
	 * Get the page class registered with the link
	 *
	 * @return Page Class
	 */
	Class<? extends Page> getPageClass();
	
	/**
	 * Get the url for a page. In most cases this will
	 * be an url for an external page. 
	 * 
	 * @return url of the page
	 */
	String getPageUrl();

	/**
	 * Indicates whether the menu-item is enabled
	 *
	 * @return true or false
	 */
	boolean isEnabled();
	
	/**
	 * Indicates whether the pages is opened in a new window
	 * 
	 * @return true or false
	 */
	boolean isOpenInNewWindow();
}
