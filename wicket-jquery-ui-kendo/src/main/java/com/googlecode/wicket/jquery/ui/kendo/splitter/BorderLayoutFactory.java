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
package com.googlecode.wicket.jquery.ui.kendo.splitter;

import org.apache.wicket.Component;

/**
 * @deprecated use {@link BorderLayout} instead
 *  
 * @author Sebastien Briquet - sebastien@7thweb.net
 *
 */
public class BorderLayoutFactory
{
	public static void create(Component component)
	{
		component.add(new SplitterBehavior("#vertical").setOption("panes", BorderLayoutFactory.getVerticalPanes()).setOption("orientation", "'vertical'"));
		component.add(new SplitterBehavior("#horizontal").setOption("panes", BorderLayoutFactory.getHorizontalOptions()));
	}

	private static String getVerticalPanes()
	{
		return "[ { collapsible: false, resizable: false, size: '85px' }, { collapsible: false }, { collapsible: true, resizable: true, size: '15%' } ]";
	}

	private static String getHorizontalOptions()
	{
		return "[ { collapsible: true, size: '200px' }, { collapsible: false } ]"; 
	}
}
