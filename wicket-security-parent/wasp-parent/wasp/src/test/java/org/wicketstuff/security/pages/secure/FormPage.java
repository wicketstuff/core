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
package org.wicketstuff.security.pages.secure;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.util.value.ValueMap;
import org.wicketstuff.security.components.markup.html.form.SecureForm;
import org.wicketstuff.security.pages.SecureTestPage;

/**
 * Page with a secure form and regular components.
 * 
 * @author marrink
 * 
 */
public class FormPage extends SecureTestPage
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public FormPage()
	{
		ValueMap map = new ValueMap("text=hello,area=foobar");
		Form<ValueMap> form = new SecureForm<ValueMap>("form", new CompoundPropertyModel<ValueMap>(
			map));
		add(form);
		form.add(new TextField<String>("text"));
		form.add(new TextArea<String>("area"));
		form.add(new Button("button"));
		// TODO add listview
	}

}
