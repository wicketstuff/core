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

import org.apache.wicket.MarkupContainer;

/**
 * Helping interface which specifies that a Kendo UI border-layout will be applied on the underlying {@link MarkupContainer}.<br />
 * For instance:<br/>
 * <pre>
 * public class BorderLayoutPage extends WebPage implements IBorderLayout
 * {
 * 	public BorderLayoutPage(PageParameters parameters)
 * 	{
 * 		super(parameters);
 * 		this.addBorderLayout(this);
 * 	}
 *
 * 	public void addBorderLayout(MarkupContainer container)
 * 	{
 * 		container.add(new SplitterBehavior("#vertical").setOption("panes", this.getVerticalPanes()).setOption("orientation", "'vertical'"));
 * 		container.add(new SplitterBehavior("#horizontal").setOption("panes", this.getHorizontalPanes()));
 * 	}
 *
 *  ...
 * }
 * </pre>
 *
 * @author Sebastien Briquet - sebfz1
 *
 */
public interface IBorderLayout
{
	/**
	 * Method that is designed to create the BorderLayout.<br/>
	 * A typical implementation is:<br/>
	 * <pre>
	 * container.add(new SplitterBehavior("#vertical").setOption("panes", this.getVerticalPanes()).setOption("orientation", "'vertical'"));
	 * container.add(new SplitterBehavior("#horizontal").setOption("panes", this.getHorizontalPanes()));
	 * </pre>
	 *
	 * @param container either a Page or a Panel.
	 */
	void addBorderLayout(MarkupContainer container);

	/**
	 * Gets the vertical panes in a JSON array
	 * @return a String that represent the JSON array
	 */
	String getVerticalPanes();

	/**
	 * Gets the horizontal panes in a JSON array
	 * @return a String that represent the JSON array
	 */
	String getHorizontalPanes();
}
