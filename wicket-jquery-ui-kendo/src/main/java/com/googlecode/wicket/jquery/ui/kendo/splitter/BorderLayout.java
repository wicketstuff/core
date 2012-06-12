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
import org.apache.wicket.markup.html.WebMarkupContainer;

/**
 * Provides a Border Layout {@link WebMarkupContainer} based on vertical and horizontal {@link SplitterBehavior}<pre>s</pre><br/>
 * {@link #getVerticalPanes()} and {@link #getHorizontalPanes()} may be overridden to change the default layout<br/>
 * <br/>
 * <b>Note:</b> the {@link BorderLayout} IS a {@link WebMarkupContainer}. If you wish to apply a {@link BorderLayout} on an existing Page or an existing Panel, you can implement the {@link IBorderLayout} interface.<br/>
 * <br/>
 * Alternatively, the HTML markup look like:
 * <pre>
&lt;div wicket:id="layout"&gt;
	&lt;div id="vertical"&gt;
		&lt;div&gt;
			- top -
		&lt;/div&gt;		
		&lt;div id="horizontal"&gt;	
			&lt;div&gt;
				- left -
			&lt;/div&gt;
			&lt;div&gt;
				- center -
			&lt;/div&gt;
			&lt;div&gt;
				- right -
			&lt;/div&gt;
		&lt;/div&gt;
		&lt;div&gt;
			- bottom -
		&lt;/div&gt;
	&lt;/div&gt;
&lt;/div&gt;
 * </pre>
 * 
 * @author Sebastien Briquet - sebfz1
 *
 */
public class BorderLayout extends WebMarkupContainer implements IBorderLayout
{
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 * @param id the markup id
	 */
	public BorderLayout(String id)
	{
		super(id);
	}
	
	@Override
	protected void onInitialize()
	{
		super.onInitialize();
		
		this.addBorderLayout(this);
	}
	
	@Override
	public void addBorderLayout(MarkupContainer container)
	{
		container.add(new SplitterBehavior("#vertical").setOption("panes", this.getVerticalPanes()).setOption("orientation", "'vertical'"));
		container.add(new SplitterBehavior("#horizontal").setOption("panes", this.getHorizontalPanes()));
	}

	/**
	 * Gets vertical panes in a JSON array
	 * @return by default: 15% - middle - 15%
	 */
	@Override
	public String getVerticalPanes()
	{
		return "[ { resizable: false, size: '15%' }, {  }, { collapsible: true, size: '15%' } ]";
	}

	/**
	 * Gets horizontal panes in a JSON array
	 * @return by default: 15% - center - 15%
	 */
	@Override
	public String getHorizontalPanes()
	{
		return "[ { size: '15%' }, { }, { size: '15%' } ]"; 
	}

	

	

}
