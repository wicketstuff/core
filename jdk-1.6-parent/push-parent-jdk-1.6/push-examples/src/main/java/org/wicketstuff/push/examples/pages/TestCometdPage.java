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
package org.wicketstuff.push.examples.pages;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.wicketstuff.push.AbstractPushEventHandler;
import org.wicketstuff.push.IPushEventContext;
import org.wicketstuff.push.IPushNode;
import org.wicketstuff.push.cometd.CometdPushNode;
import org.wicketstuff.push.cometd.CometdPushService;

/**
 * @author <a href="http://sebthom.de/">Sebastian Thomschke</a>
 */
public class TestCometdPage extends WebPage
{
	private static final long serialVersionUID = 1L;
	private final TextField<String> field;
	private String val;

	@SuppressWarnings("serial")
	public TestCometdPage(final PageParameters parameters)
	{

		final CometdPushNode<String> pushNode = CometdPushService.get().installNode(this,
			new AbstractPushEventHandler<String>()
			{
				@Override
				public void onEvent(final AjaxRequestTarget target, final String event,
					final IPushNode<String> node, final IPushEventContext<String> ctx)
				{
					field.setModel(new Model<String>("updated"));
					target.add(field);
				}
			});

		final AjaxLink<Void> link = new AjaxLink<Void>("link")
		{
			@Override
			public void onClick(final AjaxRequestTarget target)
			{
				CometdPushService.get().publish(pushNode, val);
			}
		};
		add(link);

		field = new TextField<String>("text", new Model<String>(val));
		field.setOutputMarkupId(true);
		add(field);
	}
}
