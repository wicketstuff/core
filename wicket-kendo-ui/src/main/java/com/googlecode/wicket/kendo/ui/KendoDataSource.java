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

import org.apache.wicket.Component;
import org.apache.wicket.core.request.handler.IPartialPageRequestHandler;
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
	 * @param name the data-source name (caution: it should not contain invalid js-variable chars)
	 */
	public KendoDataSource(String name)
	{
		this(name, TYPE);
	}

	/**
	 * Constructor
	 *
	 * @param component the hosting component (used to get the name)
	 */
	public KendoDataSource(Component component)
	{
		this(nameOf(component), TYPE);
	}

	/**
	 * Main Constructor
	 *
	 * @param name the data-source name (caution: it should not contain invalid js-variable chars)
	 * @param type the response data type (json, xml)
	 */
	public KendoDataSource(String name, String type)
	{
		this.name = Args.notNull(name, "name").replace('#', '_');
		this.transport = new Options();

		this.set("sync", "function() { this.read(); }"); // will force holding component to call #refresh
		this.set("error", DebugUtils.errorCallback);
		this.set("dataType", Options.asString(type)); // useless
	}

	/**
	 * Constructor
	 *
	 * @param component the hosting component (used to get the name)
	 * @param type the response data type (json, xml)
	 */
	public KendoDataSource(Component component, String type)
	{
		this(nameOf(component), type);
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
	 * @param function the javascript function.
	 * @see #setTransportReadUrl(String)
	 */
	public void setTransportRead(String function)
	{
		this.transport.set("read", function);
	}

	/**
	 * Sets the 'transport.read' callback url
	 *
	 * @param url the callback url
	 */
	public void setTransportReadUrl(CharSequence url)
	{
		this.transport.set("read", Options.asString(url));
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
	 * Sets the 'transport.destroy' callback function
	 *
	 * @param function the javascript function
	 */
	public void setTransportDelete(String function)
	{
		this.transport.set("destroy", function);
	}

	/**
	 * Prepares the data-source to be rendered
	 *
	 * @return this, for chaining
	 */
	public Options build()
	{
		this.set("transport", this.transport);

		return this;
	}

	@Override
	public void destroy(IPartialPageRequestHandler handler)
	{
		handler.prependJavaScript(String.format("var $w = jQuery('#%s'); if($w) { $w.detach(); }", this.getToken()));
	}

	@Override
	public String toScript()
	{
		return String.format("jQuery(function() { %s = new kendo.data.DataSource(%s); });", this.getName(), this.build());
	}

	// Helpers //

	/**
	 * Gets the datasource name from the supplied {@link Component}
	 * 
	 * @param component the {@code Component}
	 * @return the datasource name
	 */
	public static String nameOf(Component component)
	{
		return component.getMarkupId() + "_datasource";
	}

	/**
	 * Gets the 'read' callback function from an url
	 *
	 * @param url the callback url
	 * @return the 'read' callback function
	 */
	public static String getReadCallbackFunction(CharSequence url)
	{
		return KendoDataSource.getReadCallbackFunction(url, false);
	}

	/**
	 * Gets the 'read' callback function from an url
	 *
	 * @param url the callback url
	 * @param useCache whether the function should use cache
	 * @return the 'read' callback function
	 */
	public static String getReadCallbackFunction(CharSequence url, boolean useCache)
	{
		return "function(options) {" // lf
				+ " jQuery.ajax({" // lf
				+ "		url: '" + url + "'," // lf
				+ "		data: options.data," // lf
				+ "		cache: " + useCache + "," // lf
				+ "		success: function(result) {" // lf
				+ "			options.success(result);" // lf
				+ "		}," // lf
				+ "		error: function(result) {" // lf
				+ "			options.error(result);" // lf
				+ "		}" // lf
				+ "	});" // lf
				+ "}";
	}
}
