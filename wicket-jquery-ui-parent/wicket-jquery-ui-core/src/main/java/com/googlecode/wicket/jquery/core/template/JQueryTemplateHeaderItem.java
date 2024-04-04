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
package com.googlecode.wicket.jquery.core.template;

import org.apache.wicket.markup.head.HeaderItem;
import org.apache.wicket.markup.head.StringHeaderItem;
import org.apache.wicket.util.template.PackageTextTemplate;

import com.googlecode.wicket.jquery.core.template.JQueryTemplate.JQueryPackageTextTemplate;

/**
 * Provides an {@link HeaderItem} that renders a jQuery template
 * 
 * @see JQueryPackageTextTemplate
 * @see JQueryTemplateResourceStream
 * @author Sebastien Briquet - sebfz1
 *
 */
public class JQueryTemplateHeaderItem extends StringHeaderItem
{
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 * 
	 * @param template the {@link IJQueryTemplate}
	 * @param token the unique resource-stream token that will be the script-id.
	 */
	public JQueryTemplateHeaderItem(IJQueryTemplate template, String token)
	{
		this(new JQueryTemplateResourceStream(template, token));
	}

	/**
	 * Constructor
	 * 
	 * @param clazz the {@code Class} to be used for retrieving the classloader for loading the {@link PackageTextTemplate}
	 * @param filename the template file to load in the package
	 * @param token the unique resource-stream token that will be the script-id.
	 */
	public JQueryTemplateHeaderItem(Class<?> clazz, String filename, String token)
	{
		this(new JQueryPackageTextTemplate(clazz, filename), token);
	}

	/**
	 * Constructor
	 * 
	 * @param stream the {@link JQueryTemplateResourceStream}
	 */
	protected JQueryTemplateHeaderItem(JQueryTemplateResourceStream stream)
	{
		super(stream.asString());
	}
}
