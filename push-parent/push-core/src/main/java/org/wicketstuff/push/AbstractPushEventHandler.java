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
package org.wicketstuff.push;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.lang.Args;

/**
 * @author <a href="http://sebthom.de/">Sebastian Thomschke</a>
 */
public abstract class AbstractPushEventHandler<EventType> implements IPushEventHandler<EventType>
{
	private static final long serialVersionUID = 1L;

	protected void appendHTML(final AjaxRequestTarget target, final Label component,
		final String html)
	{
		Args.notNull(target, "target");
		Args.notNull(component, "component");

		if (html == null)
			return;

		// update the component's model object for the case when the page is refreshed using F5
		component.setEscapeModelStrings(false);
		final IModel<?> model = component.getDefaultModel();
		component.setDefaultModelObject(model == null || model.getObject() == null ? html
			: model.getObject() + html);

		// escape backslashes for JavaScript
		final String escapedHTML = html.replaceAll("\\\\", "&#92;");

		target.appendJavaScript("" + //
			"var target = document.getElementById('" + component.getMarkupId() + "');" + //
			"target.innerHTML += '" + escapedHTML + "';" + //
			"target.scrollTop = target.scrollHeight" //
		);
	}
}
