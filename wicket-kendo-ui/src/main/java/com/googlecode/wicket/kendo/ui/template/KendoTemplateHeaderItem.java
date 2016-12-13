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
package com.googlecode.wicket.kendo.ui.template;

import org.apache.wicket.markup.head.HeaderItem;
import org.apache.wicket.util.template.PackageTextTemplate;

import com.googlecode.wicket.jquery.core.template.IJQueryTemplate;
import com.googlecode.wicket.jquery.core.template.JQueryTemplate.JQueryPackageTextTemplate;
import com.googlecode.wicket.jquery.core.template.JQueryTemplateHeaderItem;

/**
 * Provides an {@link HeaderItem} that renders a Kendo UI template
 * 
 * @see JQueryPackageTextTemplate
 * @see KendoTemplateResourceStream
 * @author Sebastien Briquet - sebfz1
 *
 */
public class KendoTemplateHeaderItem extends JQueryTemplateHeaderItem
{
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 * 
	 * @param template the {@link IJQueryTemplate}
	 * @param token the unique resource-stream token that will be the script-id.
	 */
	public KendoTemplateHeaderItem(IJQueryTemplate template, String token)
	{
		super(new KendoTemplateResourceStream(template, token));
	}

	/**
	 * Constructor
	 * 
	 * @param clazz the {@code Class} to be used for retrieving the classloader for loading the {@link PackageTextTemplate}
	 * @param filename the template file to load in the package
	 * @param token the unique resource-stream token that will be the script-id.
	 */
	public KendoTemplateHeaderItem(Class<?> clazz, String filename, String token)
	{
		this(new JQueryPackageTextTemplate(clazz, filename), token);
	}

	/**
	 * Gets the jQuery statement this Kendo template
	 *
	 * @param token the template script-id
	 * @return the jQuery statement
	 */
	public static String $(final String token)
	{
		return String.format("kendo.template(jQuery('#%s').html())", token);
	}
}
