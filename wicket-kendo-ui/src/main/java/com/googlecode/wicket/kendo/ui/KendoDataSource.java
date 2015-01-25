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

import org.apache.wicket.util.lang.Args;

import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.kendo.ui.utils.DebugUtils;

/**
 * Provides a standard Kendo UI data-source<br/>
 *
 * @see <a href="http://docs.telerik.com/kendo-ui/api/framework/datasource">http://docs.telerik.com/kendo-ui/api/framework/datasource</a>
 * @author Sebastien Briquet - sebfz1
 */
public class KendoDataSource extends Options implements IKendoDataSource
{
	private static final long serialVersionUID = 1L;

	protected static final String TYPE = "json";

	private final String name;
	private final Options transport;

	/**
	 * Constructor
	 *
	 * @param name the data-source name
	 */
	public KendoDataSource(String name)
	{
		this(name, TYPE);
	}

	/**
	 * Constructor
	 *
	 * @param name the data-source name
	 * @param type the response type (json, xml)
	 */
	public KendoDataSource(String name, String type)
	{
		this.name = Args.notNull(name, "name");
		this.transport = new Options();

		this.set("type", Options.asString(type));
		this.set("sync", "function() { this.read(); }");
		this.set("error", DebugUtils.errorCallback);
	}

	// Properties //

	/**
	 * Gets the data-source's javacript variable name (global scoped)
	 *
	 * @return the variable name (ie: window.myDataSource)
	 */
	public String getName()
	{
		return "window." + this.name;
	}

	@Override
	public String getToken()
	{
		return "kendo-" + this.name;
	}

	/**
	 * Sets the 'transport.read' callback function
	 *
	 * @param function the javascript function
	 */
	public void setTransportRead(String function)
	{
		this.transport.set("read", function);
	}

	/**
	 * Sets the 'transport.create' callback function
	 *
	 * @param function the javascript function
	 */
	public void setTransportCreate(String function)
	{
		this.transport.set("create", function);
	}

	/**
	 * Sets the 'transport.update' callback function
	 *
	 * @param function the javascript function
	 */
	public void setTransportUpdate(String function)
	{
		this.transport.set("update", function);
	}

	/**
	 * Sets the 'transport.delete' callback function
	 *
	 * @param function the javascript function
	 */
	public void setTransportDelete(String function)
	{
		this.transport.set("destroy", function);
	}

	@Override
	public IKendoDataSource prepareRender()
	{
		this.set("transport", this.transport);

		return this;
	}

	@Override
	public String toScript()
	{
		return String.format("jQuery(function() { %s = new kendo.data.DataSource(%s); });", this.getName(), this.toString());
	}
}
