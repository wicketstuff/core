/*
 *  Copyright 2012 inaiat.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  under the License.
 */
package org.wicketstuff.lightbox2.references;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.Application;
import org.apache.wicket.markup.head.HeaderItem;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.request.resource.JavaScriptResourceReference;

public class LightboxJavascriptResourceReference extends JavaScriptResourceReference
{

	private static final long serialVersionUID = 8280254730894457750L;

	private static final LightboxJavascriptResourceReference INSTANCE = new LightboxJavascriptResourceReference();

	public static LightboxJavascriptResourceReference get()
	{
		return INSTANCE;
	}

	private LightboxJavascriptResourceReference()
	{
		super(LightboxJavascriptResourceReference.class, "js/lightbox.js");
	}

	@Override
	public List<HeaderItem> getDependencies()
	{
		List<HeaderItem> dependencies = new ArrayList<>();
		for (Iterator<? extends HeaderItem> iterator = super.getDependencies().iterator(); iterator.hasNext();)
		{
			HeaderItem headerItem = iterator.next();
			dependencies.add(headerItem);
		}
		dependencies.add(JavaScriptHeaderItem.forReference(Application.get()
			.getJavaScriptLibrarySettings()
			.getJQueryReference()));
		return dependencies;
	}

}
