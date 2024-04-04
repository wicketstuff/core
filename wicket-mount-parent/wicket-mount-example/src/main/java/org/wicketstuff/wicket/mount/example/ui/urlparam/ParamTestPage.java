/*
 * Copyright 2014 WicketStuff.
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
package org.wicketstuff.wicket.mount.example.ui.urlparam;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.wicketstuff.wicket.mount.core.annotation.MountPath;

/**
 * @author jsarman
 */
@MountPath("${testParam}/ParamsViaUrlTest.html")
public class ParamTestPage extends WebPage
{

	private static final long serialVersionUID = 1L;

	public ParamTestPage(PageParameters pp)
	{


		add(new Label("message",
				"Received parameter via url = " + pp.get("testParam").toOptionalString()));

	}
}
