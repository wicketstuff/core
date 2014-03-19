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
package org.apache.wicket.portlet;

import org.apache.wicket.Component;
import org.apache.wicket.application.IComponentInitializationListener;

/**
 * If the markup id is not defined directly on the component tag, it prepends the portlet namespace
 * to the Wicket generated markup id.
 * 
 * @author Peter Pastrnak
 */
public class MarkupIdPrepender implements IComponentInitializationListener
{
	private String prefix;

	public String getPrefix()
	{
		if (prefix == null)
		{
			prefix = ThreadPortletContext.getNamespace() + "_";
		}
		return prefix;
	}

	public void onInitialize(Component component)
	{
		boolean outputMarkupId = component.getOutputMarkupId();
		if (outputMarkupId)
		{
			String markupId = component.getMarkupId();
			String markupIdFromMarkup = component.getMarkupIdFromMarkup();

			if (markupId != markupIdFromMarkup)
			{
				component.setMarkupId(getPrefix() + markupId);
			}
		}
	}
}
