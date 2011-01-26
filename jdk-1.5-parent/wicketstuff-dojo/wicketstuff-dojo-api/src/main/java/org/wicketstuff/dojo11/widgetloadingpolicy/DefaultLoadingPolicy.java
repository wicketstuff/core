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
package org.wicketstuff.dojo11.widgetloadingpolicy;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.IHeaderResponse;

/**
 * Default implementation of {@link IDojoWidgetLoadingPolicy}
 * <p>
 * This policy keeps the default Dojo widget policy: It load the widget on page
 * loading. 
 * </p>
 * @author Vincent Demay
 */
public class DefaultLoadingPolicy implements IDojoWidgetLoadingPolicy
{
	/**
	 * @see org.wicketstuff.dojo11.widgetloadingpolicy.IDojoWidgetLoadingPolicy#renderHead(org.apache.wicket.markup.html.IHeaderResponse, org.apache.wicket.Component)
	 */
	public void renderHead(IHeaderResponse response, Component component)
	{
		// DO NOTHING
	}

	/**
	 * @see org.wicketstuff.dojo11.widgetloadingpolicy.IDojoWidgetLoadingPolicy#onComponentReRendered(org.apache.wicket.ajax.AjaxRequestTarget)
	 */
	public void onComponentReRendered(AjaxRequestTarget ajaxTarget)
	{
		// DO NOTHING
	}

}
