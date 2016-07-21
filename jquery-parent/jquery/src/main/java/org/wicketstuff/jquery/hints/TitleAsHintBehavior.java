/*
 *  Copyright 2007 dwayne.
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
package org.wicketstuff.jquery.hints;

import org.apache.wicket.Component;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.apache.wicket.request.resource.ResourceReference;
import org.wicketstuff.jquery.JQueryBehavior;

/**
 * 
 * @author dwayne
 */
@SuppressWarnings(value = "serial")
public class TitleAsHintBehavior extends JQueryBehavior
{

	public static final ResourceReference HINTS_JS = new PackageResourceReference(
		TitleAsHintBehavior.class, "jquery.hints.js");

	@Override
	public void renderHead(Component component, IHeaderResponse response)
	{
		super.renderHead(component, response);
		response.render(JavaScriptHeaderItem.forReference(HINTS_JS));
	}

	@Override
	protected CharSequence getOnReadyScript()
	{
		return "$('input:text').hint();";
	}
}