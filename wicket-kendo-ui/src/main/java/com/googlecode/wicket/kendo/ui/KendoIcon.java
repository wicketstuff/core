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
package com.googlecode.wicket.kendo.ui;

import com.googlecode.wicket.kendo.ui.form.button.Button;

/**
 * Provides default Kendo UI icons. Might be used to decorate a {@link Button} for instance.
 *
 * @author Sebastien Briquet - sebfz1
 *
 */
public class KendoIcon
{
	public static final String NONE = "";

	public static final String ARROW_N = "arrow-n";
	public static final String ARROW_E = "arrow-e";
	public static final String ARROW_S = "arrow-s";
	public static final String ARROW_W = "arrow-w";
	public static final String ARROWHEAD_E = "arrowhead-e";
	public static final String ARROWHEAD_N = "arrowhead-n";
	public static final String ARROWHEAD_S = "arrowhead-s";
	public static final String ARROWHEAD_W = "arrowhead-w";
	public static final String CALENDAR = "calendar";
	public static final String CANCEL = "cancel";
	public static final String CLOCK = "clock";
	public static final String CLOSE = "close";
	public static final String COLLAPSE = "collapse";
	public static final String COLLAPSE_W = "collapse-w";
	public static final String CUSTOM = "custom";
	public static final String EXPAND = "expand";
	public static final String EXPAND_W = "expand-w";
	public static final String FOLDER_ADD = "folder-add";
	public static final String FOLDER_UP = "folder-up ";
	public static final String FUNNEL = "funnel";
	public static final String FUNNEL_CLEAR = "funnel-clear";
	public static final String INSERT_M = "insert-m";
	public static final String INSERT_N = "insert-n";
	public static final String INSERT_S = "insert-s";
	public static final String MAXIMIZE = "maximize";
	public static final String MINIMIZE = "minimize";
	public static final String NOTE = "note";
	public static final String PENCIL = "pencil";
	public static final String PLUS = "plus";
	public static final String REFRESH = "refresh";
	public static final String RESTORE = "restore";
	public static final String SEARCH = "search";
	public static final String SEEK_E = "seek-e";
	public static final String SEEK_N = "seek-n";
	public static final String SEEK_S = "seek-s";
	public static final String SEEK_W = "seek-w";
	public static final String TICK = "tick";

	/**
	 * Constants class
	 */
	private KendoIcon()
	{
	}

	/**
	 * Indicates whether the icon is {@link KendoIcon#NONE}
	 *
	 * @param icon the icon
	 * @return true or false
	 */
	public static boolean isNone(String icon)
	{
		return KendoIcon.NONE.equals(icon);
	}

	/**
	 * Gets the full css-class of the icon
	 *
	 * @param icon the {@link KendoIcon}
	 * @return the full css-class
	 */
	public static String getCssClass(String icon)
	{
		if (!isNone(icon))
		{
			return "k-icon k-i-" + icon;
		}

		return "";
	}
}
