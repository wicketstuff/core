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
package com.googlecode.wicket.jquery.core.resource;

import java.util.Map;

import org.apache.wicket.markup.head.JavaScriptReferenceHeaderItem;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.resource.TextTemplateResourceReference;

/**
 * Provides a {@link JavaScriptReferenceHeaderItem} that will load a '.js' file corresponding to the supplied {@code Class}<br/>
 * <b>i.e.:</b> {@code MyClass.js}
 * 
 * @author Sebastien Briquet - sebfz1
 *
 */
public class JavaScriptPackageHeaderItem extends JavaScriptReferenceHeaderItem
{
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 * 
	 * @param scope the scope
	 */
	public JavaScriptPackageHeaderItem(Class<?> scope)
	{
		this(scope, scope.getSimpleName());
	}

	/**
	 * Constructor
	 * 
	 * @param scope the scope
	 * @param the function, i.e.: the name of the '.js' file without the extension
	 */
	public JavaScriptPackageHeaderItem(Class<?> scope, String function)
	{
		super(new JavaScriptResourceReference(scope, function + ".js"), null, function, false, null, null);
	}

	/**
	 * Constructor
	 * 
	 * @param scope the scope
	 * @param variables the variable {@code Map} to supply to the file
	 */
	public JavaScriptPackageHeaderItem(Class<?> scope, Map<String, Object> variables)
	{
		this(scope, scope.getSimpleName(), variables);
	}

	/**
	 * Constructor
	 * 
	 * @param scope the scope
	 * @param the function, i.e.: the name of the '.js' file without the extension
	 * @param variables the variable {@code Map} to supply to the file
	 */
	public JavaScriptPackageHeaderItem(Class<?> scope, String function, Map<String, Object> variables)
	{
		super(new TextTemplateResourceReference(scope, function + ".js", Model.ofMap(variables)), null, function, false, null, null);
	}
}
