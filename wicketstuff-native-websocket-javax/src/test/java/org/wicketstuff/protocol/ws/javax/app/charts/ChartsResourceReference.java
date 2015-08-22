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
package org.wicketstuff.protocol.ws.javax.app.charts;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.markup.head.HeaderItem;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.protocol.ws.api.WicketWebSocketJQueryResourceReference;
import org.apache.wicket.request.Url;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.request.resource.UrlResourceReference;

/**
 * A resource reference that contributes 'charts.js' - the JavaScript that registers the WebSocket client
 * and constructs the Google chart.
 */
public class ChartsResourceReference extends JavaScriptResourceReference
{
	public ChartsResourceReference()
	{
		super(ChartsResourceReference.class, "charts.js");
	}

	/**
	 * Specify that charts.js depends on Google JS APIs and Wicket WebSocket JavaScript
	 * @return a list of dependencies
	 */
	@Override
	public List<HeaderItem> getDependencies()
	{
		Iterable<? extends HeaderItem> dependencies = super.getDependencies();
        List<HeaderItem> deps = new ArrayList<>();
        for (HeaderItem dependency : dependencies) {
            deps.add(dependency);
        }
		deps.add(JavaScriptHeaderItem.forReference(new UrlResourceReference(Url.parse("https://www.google.com/jsapi"))));
		deps.add(JavaScriptHeaderItem.forReference(WicketWebSocketJQueryResourceReference.get()));
		return deps;
	}
}
