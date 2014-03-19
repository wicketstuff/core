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
package org.wicketstuff.shiro.component;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.wicketstuff.shiro.ShiroServletRequestModel;
import org.wicketstuff.shiro.ShiroSubjectModel;

public class ShiroConfigInfoPanel extends Panel
{
	private static final long serialVersionUID = 1L;

	public ShiroConfigInfoPanel(final String id)
	{
		super(id);

		final ShiroServletRequestModel shiroRequestModel = new ShiroServletRequestModel();
		final WebMarkupContainer request = new WebMarkupContainer("request",
			new CompoundPropertyModel<ShiroServletRequestModel>(shiroRequestModel));
		request.add(new Label("toString", shiroRequestModel));
		request.add(new Label("class.name"));
		request.add(new Label("RemoteUser"));
		request.add(new Label("RequestedSessionId"));
		request.add(new Label("UserPrincipal"));
		request.add(new Label("HttpSessions"));
		request.add(new Label("RequestedSessionIdFromCookie"));
		request.add(new Label("RequestedSessionIdFromUrl"));
		request.add(new Label("RequestedSessionIdFromURL"));
		request.add(new Label("RequestedSessionIdValid"));
		add(request);

		final ShiroSubjectModel shiroSubjectModel = new ShiroSubjectModel();
		final WebMarkupContainer subject = new WebMarkupContainer("subject",
			new CompoundPropertyModel<ShiroSubjectModel>(shiroSubjectModel));
		subject.add(new Label("toString", shiroSubjectModel));
		subject.add(new Label("class.name"));
		subject.add(new Label("authenticated"));
		subject.add(new Label("principal"));
		subject.add(new Label("session"));
		add(subject);
	}
}
