/**
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.wicketstuff.jeeweb.examples;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.wicketstuff.jeeweb.ajax.JEEWebGlobalAjaxEvent;

public class TestPage extends WebPage
{

	private static final long serialVersionUID = 1457592847439107204L;
	private Label label;

	public TestPage()
	{
		setStatelessHint(false);
		label = new Label("labelToChange", "This Text will be changed");
		label.setOutputMarkupId(true);
		add(label);

	}

	@Override
	public void onEvent(IEvent<?> event)
	{
		JEEWebGlobalAjaxEvent castedEvent = JEEWebGlobalAjaxEvent.getCastedEvent(event);
		if (castedEvent != null)
		{
			AjaxRequestTarget ajaxRequestTarget = castedEvent.getAjaxRequestTarget();
			label.setDefaultModelObject(castedEvent.getPageParameters().get("param").toString());
			ajaxRequestTarget.add(label);
		}
	}
}
