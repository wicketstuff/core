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
package org.wicketstuff.security.components;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.wicketstuff.security.checks.ContainerSecurityCheck;
import org.wicketstuff.security.components.markup.html.form.SecureTextField;

/**
 * @author marrink
 */
public class L1Container extends Panel
{
	private static final long serialVersionUID = 1L;

	/**
	 * Construct.
	 * 
	 * @param id
	 */
	public L1Container(String id)
	{
		this(id, null);
	}

	/**
	 * Construct.
	 * 
	 * @param id
	 * @param model
	 */
	public L1Container(String id, IModel<?> model)
	{
		super(id, model);
		SecureComponentHelper.setSecurityCheck(this, new ContainerSecurityCheck(this));
		add(new SecureTextField<String>("txt1", new Model<String>("foo")));
		WebMarkupContainer lvl2 = new WebMarkupContainer("lvl2");
		add(lvl2);
		SecureComponentHelper.setSecurityCheck(lvl2, new ContainerSecurityCheck(lvl2));
		lvl2.add(new SecureTextField<String>("txt2", new Model<String>("bar")));
	}
}
