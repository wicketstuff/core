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
package org.apache.wicket.security.examples.tabs.panels;

/**
 * Panel showing information about Grolsch beer.
 * 
 * @author marrink
 */
public class Grolsch extends BeerPanel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Construct.
	 * 
	 * @param id
	 */
	public Grolsch(String id)
	{
		super(id);
	}

	/**
	 * @see org.apache.wicket.security.examples.tabs.panels.BeerPanel#getDescription()
	 */
	@Override
	public String getDescription()
	{
		return "<img src='../img/beer/grolsch.jpg' align='left'/><div style='margin-left:4px;'>"
			+ "<b>Grolsch Premium Lager - quality from start to finish</b>\r\n"
			+ "Our genuine Dutch Premium Lager owes its superb quality to the finest "
			+ "ingredients and the traditional bottom fermentation brewing process.\r\n"
			+ "<b>Brewed to be the best</b>\r\nFor almost four centuries, Grolsch brewers "
			+ "have been employing skill and patience to produce a beer that is renowned worldwide "
			+ "for its flavour and character. Besides malt, hops and water, Grolsch brewers use a "
			+ "fourth vital ingredient: <i>time</i>. It takes several weeks to complete the "
			+ "perfectly natural brewing process, that makes Grolsch beer so special. </div>";
	}

	/**
	 * @see org.apache.wicket.security.examples.tabs.panels.BeerPanel#getName()
	 */
	@Override
	public String getName()
	{
		return "Grolsch";
	}

	/**
	 * @see org.apache.wicket.security.examples.tabs.panels.BeerPanel#getUrl()
	 */
	@Override
	public String getUrl()
	{
		return "http://grolsch.com/";
	}

}
