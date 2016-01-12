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
 * Panel showing information about Heineken beer.
 * 
 * @author marrink
 */
public class Heineken extends BeerPanel
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
	public Heineken(String id)
	{
		super(id);
	}

	/**
	 * @see org.apache.wicket.security.examples.tabs.panels.BeerPanel#getDescription()
	 */
	@Override
	public String getDescription()
	{
		return "<img src='../img/beer/heineken.jpg' />\r\n\r\n"
			+ "Heineken has wide international presence through a global network of "
			+ "distributors and breweries. Heineken owns and manages one of the world�s "
			+ "leading portfolios of beer brands and is one of the world�s leading brewers "
			+ "in terms of sales volume and profitability. Our principal international brands are "
			+ "Heineken and Amstel, but the group brews and sells more than 170 international premium, "
			+ "regional, local and specialty beers, including Cruzcampo, Tiger, Zywiec, "
			+ "Birra Moretti, Ochota, Murphy�s and Star.";
	}

	/**
	 * @see org.apache.wicket.security.examples.tabs.panels.BeerPanel#getName()
	 */
	@Override
	public String getName()
	{
		return "Heineken";
	}

	/**
	 * @see org.apache.wicket.security.examples.tabs.panels.BeerPanel#getUrl()
	 */
	@Override
	public String getUrl()
	{
		return "http://heineken.com/";
	}

}
