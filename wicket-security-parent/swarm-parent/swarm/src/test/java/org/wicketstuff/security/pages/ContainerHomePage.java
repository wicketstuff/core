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
package org.wicketstuff.security.pages;

import org.apache.wicket.Session;
import org.apache.wicket.model.Model;
import org.wicketstuff.security.WaspSession;
import org.wicketstuff.security.components.L1Container;
import org.wicketstuff.security.components.SecureWebPage;
import org.wicketstuff.security.components.markup.html.form.SecureTextField;

/**
 * @author marrink
 */
public class ContainerHomePage extends SecureWebPage
{
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public ContainerHomePage()
	{
		super();
		add(new SecureTextField<String>("txt1", new Model<String>("foobar")));
		add(new L1Container("lvl1"));
	}

	/**
	 * shortcut to {@link WaspSession#logoff(Object)}
	 * 
	 * @param context
	 * @return true if the logoff was successful, false otherwise.
	 */
	public boolean logoff(Object context)
	{
		return ((WaspSession)Session.get()).logoff(context);
	}

}
