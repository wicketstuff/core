/*
 *  Copyright 2012 inaiat.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  under the License.
 */
package org.wicketstuff.jqplot.behavior;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.Application;
import org.apache.wicket.markup.head.HeaderItem;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.request.resource.JavaScriptResourceReference;

public class JqPlotJavascriptResourceReference extends JavaScriptResourceReference {
	private static final long serialVersionUID = 1L;

	private static final JqPlotJavascriptResourceReference INSTANCE = new JqPlotJavascriptResourceReference();

	public static JqPlotJavascriptResourceReference get()
	{
		return INSTANCE;
	}

	private JqPlotJavascriptResourceReference()
	{
		super(JqPlotJavascriptResourceReference.class, "jquery.jqplot.js");
	}

	@Override
	public List<HeaderItem> getDependencies()
	{
		List<HeaderItem> dependencies = new ArrayList<>();
		dependencies.addAll(super.getDependencies());
		dependencies.add(JavaScriptHeaderItem.forReference(Application.get()
			.getJavaScriptLibrarySettings()
			.getJQueryReference()));
		return dependencies;
	}

}
