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
package org.wicketstuff.html5.eventsource;

import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.resource.CoreLibrariesContributor;
import org.wicketstuff.html5.BasePage;

/**
 * A demo page for EventSourceResource
 *
 * @since 6.0
 */
public class EventSourceDemo extends BasePage
{
	@Override
	public void renderHead(final IHeaderResponse response)
	{
		super.renderHead(response);

		response.render(CssHeaderItem.forReference(new CssResourceReference(EventSourceDemo.class, "EventSourceDemo.css")));

		CharSequence eventSourceReferenceUrl = urlFor(new EventSourceResourceReference(), null);
		response.render(JavaScriptHeaderItem.forScript("var eventSourceReferenceUrl = '" + eventSourceReferenceUrl + "';", "eventSourceReferenceUrl"));
		
		CoreLibrariesContributor.contributeAjax(getApplication(), response);
		
		response.render(JavaScriptHeaderItem.forReference(
				new JavaScriptResourceReference(EventSourceDemo.class, "EventSourceDemo.js")));
	}
}
