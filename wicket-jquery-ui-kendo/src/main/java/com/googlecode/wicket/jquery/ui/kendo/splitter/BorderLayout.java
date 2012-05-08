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

import org.apache.wicket.markup.html.WebMarkupContainer;

import com.googlecode.wicket.jquery.ui.Options;

public class BorderLayout extends WebMarkupContainer
{
	private static final long serialVersionUID = 1L;

	private SplitterBehavior verticalBehavior;
	private SplitterBehavior horizontalBehavior;

	public BorderLayout(String id)
	{
		super(id);
		this.init();
	}
	
	private void init()
	{
		this.verticalBehavior = new SplitterBehavior("#vertical");
		this.verticalBehavior.setOption("panes", getVerticalPanes());
		this.verticalBehavior.setOption("orientation", Options.asString("vertical"));
		
		this.horizontalBehavior = new SplitterBehavior("#horizontal");
		this.horizontalBehavior.setOption("panes", getHorizontalPanes());
	}

	@Override
	protected void onInitialize()
	{
		super.onInitialize();
		
		this.add(this.verticalBehavior);
		this.add(this.horizontalBehavior);
	}
	
	/**
	 * Gets vertical panes JSON array
	 * @return by default: 15% - middle - 15%
	 */
	protected String getVerticalPanes()
	{
		return "[ { resizable: false, size: '20%' }, {  }, { collapsible: true, size: '15%' } ]";
	}

	/**
	 * Gets horizontal panes JSON array
	 * @return by default: 15% - center - 15%
	 */
	protected String getHorizontalPanes()
	{
		return "[ { size: '15%' }, { }, { size: '15%' } ]"; 
	}
	

}
