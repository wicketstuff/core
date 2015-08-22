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
 * Panel showing information about the delicious Gifkikker beer.
 * 
 * @author marrink
 */
public class Gifkikker extends BeerPanel
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
	public Gifkikker(String id)
	{
		super(id);
	}

	/**
	 * @see org.apache.wicket.security.examples.tabs.panels.BeerPanel#getDescription()
	 */
	@Override
	public String getDescription()
	{
		return "<img src='../img/beer/logo_gifkikker.jpg' />\r\n\r\n"
			+ "On November 24 2005, Topicus employees brewed for the very first time this original beer.\r\n"
			+ "This unique beer after own recepy was proudly crissened 'Gifkikker'. A surprisingly tastefull and headstrong beer from the east of the Netherlands.";
	}

	/**
	 * @see org.apache.wicket.security.examples.tabs.panels.BeerPanel#getName()
	 */
	@Override
	public String getName()
	{
		return "Topicus Gifkikker";
	}

	/**
	 * @see org.apache.wicket.security.examples.tabs.panels.BeerPanel#getUrl()
	 */
	@Override
	public String getUrl()
	{
		return "http://gifkikker.topicus.nl/";
	}

}
