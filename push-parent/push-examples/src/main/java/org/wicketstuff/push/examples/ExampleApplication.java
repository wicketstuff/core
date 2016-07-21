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
package org.wicketstuff.push.examples;

import java.io.Serializable;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;
import org.wicketstuff.push.examples.pages.IndexPage;
import org.wicketstuff.push.examples.pages.TestCometdPage;
import org.wicketstuff.push.examples.pages.WicketCometdChatPage;
import org.wicketstuff.push.examples.pages.WicketTimerChatPage;

public class ExampleApplication extends WebApplication implements Serializable
{

	private static final long serialVersionUID = 1L;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Class<? extends WebPage> getHomePage()
	{
		return IndexPage.class;
	}

	@Override
	protected void init()
	{
		super.init();
		mountPage("/" + TestCometdPage.class.getSimpleName(), TestCometdPage.class);
		mountPage("/" + WicketTimerChatPage.class.getSimpleName(), WicketTimerChatPage.class);
		mountPage("/" + WicketCometdChatPage.class.getSimpleName(), WicketCometdChatPage.class);
	}
}