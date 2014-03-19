/*
 * Copyright 2011 inaiat.
 *
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
package org.wicketstuff.mootools.meiomask.behavior;

import org.apache.wicket.Component;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.request.resource.ResourceReference;

/**
 * 
 * @author inaiat
 */
public class MootoolsMoreBehavior extends MootoolsCoreBehavior
{

	private static final long serialVersionUID = 1L;
	// create a reference to the base mootools javascript file.
	// we use JavascriptResourceReference so that the included file will have its comments stripped
// and gzipped.
	private static final ResourceReference MOOTOOLS_MORE_JS = new JavaScriptResourceReference(
		MootoolsMoreBehavior.class, "res/mootools-more-1.3.0.1.js");


	@Override
	public void renderHead(Component component, IHeaderResponse response)
	{
		super.renderHead(component, response);
		response.render(JavaScriptHeaderItem.forReference(MOOTOOLS_MORE_JS));
	}


}
