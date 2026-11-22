/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wicketstuff.hugerte;

import java.util.List;

import org.apache.wicket.Application;
import org.apache.wicket.RuntimeConfigurationType;
import org.apache.wicket.markup.head.HeaderItem;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.request.resource.ResourceReference;

import de.agilecoders.wicket.webjars.WicketWebjars;
import de.agilecoders.wicket.webjars.request.resource.WebjarsJavaScriptResourceReference;

/**
 * The Wicket glue script ({@code hugerte-wicket.js}). It depends on the HugeRTE webjar and on
 * Wicket's Ajax script.
 * <p>
 * The webjar references are created on first use, when the application has installed
 * wicket-webjars, so this class can be referenced from {@code WebApplication.init()}.
 */
public final class HugeRteJavaScriptReference extends JavaScriptResourceReference
{
	private static final long serialVersionUID = 1L;

	private static final HugeRteJavaScriptReference INSTANCE = new HugeRteJavaScriptReference();

	private static volatile ResourceReference editor;

	private static volatile ResourceReference editorMin;

	public static HugeRteJavaScriptReference get()
	{
		return INSTANCE;
	}

	/**
	 * @return the HugeRTE editor script: the full version in development mode, the minified one
	 *         otherwise
	 */
	public static ResourceReference editor()
	{
		if (Application.get().getConfigurationType() == RuntimeConfigurationType.DEVELOPMENT)
		{
			if (editor == null)
			{
				editor = new WebjarsJavaScriptResourceReference(path("hugerte.js"));
			}
			return editor;
		}
		if (editorMin == null)
		{
			editorMin = new WebjarsJavaScriptResourceReference(path("hugerte.min.js"));
		}
		return editorMin;
	}

	private static String path(String file)
	{
		return "hugerte/" + WicketWebjars.settings().recentVersionPlaceHolder() + "/" + file;
	}

	private HugeRteJavaScriptReference()
	{
		super(HugeRteJavaScriptReference.class, "hugerte-wicket.js");
	}

	@Override
	public List<HeaderItem> getDependencies()
	{
		return List.of(
			JavaScriptHeaderItem.forReference(
				Application.get().getJavaScriptLibrarySettings().getWicketAjaxReference()),
			JavaScriptHeaderItem.forReference(editor()));
	}
}
