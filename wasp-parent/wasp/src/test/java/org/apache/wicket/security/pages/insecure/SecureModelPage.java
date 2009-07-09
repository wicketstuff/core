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
package org.apache.wicket.security.pages.insecure;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.security.models.SecureCompoundPropertyModel;
import org.apache.wicket.security.pages.BasePage;
import org.apache.wicket.util.value.ValueMap;


/**
 * Page showing the use of a {@link SecureCompoundPropertyModel}.
 * 
 * @author marrink
 * 
 */
public class SecureModelPage extends BasePage
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public SecureModelPage()
	{
		ValueMap map = new ValueMap("label=hello,input=type in me");
		setDefaultModel(new SecureCompoundPropertyModel(map));
		add(new Label("label"));
		add(new TextField("input"));
	}

}
