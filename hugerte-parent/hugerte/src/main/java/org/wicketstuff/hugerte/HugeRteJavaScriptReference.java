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

import de.agilecoders.wicket.webjars.request.resource.WebjarsJavaScriptResourceReference;

/**
 * The Wicket glue script ({@code hugerte-wicket.js}). It depends on the HugeRTE webjar and on
 * Wicket's Ajax script.
 */
public final class HugeRteJavaScriptReference extends JavaScriptResourceReference
{
	private static final long serialVersionUID = 1L;

	/** The HugeRTE editor itself. */
	public static final ResourceReference HUGERTE = new WebjarsJavaScriptResourceReference(
		"hugerte/current/hugerte.js");

	/** The minified HugeRTE editor. */
	public static final ResourceReference HUGERTE_MIN = new WebjarsJavaScriptResourceReference(
		"hugerte/current/hugerte.min.js");

	private static final HugeRteJavaScriptReference INSTANCE = new HugeRteJavaScriptReference();

	public static HugeRteJavaScriptReference get()
	{
		return INSTANCE;
	}

	private HugeRteJavaScriptReference()
	{
		super(HugeRteJavaScriptReference.class, "hugerte-wicket.js");
	}

	@Override
	public List<HeaderItem> getDependencies()
	{
		var application = Application.get();
		var editor = application.getConfigurationType() == RuntimeConfigurationType.DEVELOPMENT
			? HUGERTE : HUGERTE_MIN;
		return List.of(
			JavaScriptHeaderItem.forReference(
				application.getJavaScriptLibrarySettings().getWicketAjaxReference()),
			JavaScriptHeaderItem.forReference(editor));
	}
}
