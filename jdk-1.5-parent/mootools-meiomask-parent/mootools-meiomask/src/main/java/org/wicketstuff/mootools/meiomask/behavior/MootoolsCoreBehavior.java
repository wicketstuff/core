/*
 *  Copyright 2010 inaiat.
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
package org.wicketstuff.mootools.meiomask.behavior;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.request.resource.ResourceReference;

/**
 * 
 * @author inaiat
 */
public class MootoolsCoreBehavior extends Behavior
{

	private static final long serialVersionUID = 1L;
	// create a reference to the base mootools javascript file.
	// we use JavascriptResourceReference so that the included file will have its comments stripped
// and gzipped.
	private static final ResourceReference MOOTOOLS_JS = new JavaScriptResourceReference(
		MootoolsCoreBehavior.class, "res/mootools-core-1.3-full-nocompat-yc.js");

	/** helper method that hooks into mootools ondomready event */
	protected String executeOnWindowDomReady(String script)
	{
		StringBuilder builder = new StringBuilder(script.length() + 61);
		builder.append("<script>window.addEvent('domready', function(){\n");
		builder.append(script);
		builder.append("\n});</script>");
		return builder.toString();
	}

	@Override
	public void renderHead(Component component, IHeaderResponse response)
	{
		super.renderHead(component, response);
		response.renderJavaScriptReference(MOOTOOLS_JS);
	}

}
