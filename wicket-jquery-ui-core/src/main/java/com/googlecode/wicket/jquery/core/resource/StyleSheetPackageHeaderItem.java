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

import org.apache.wicket.markup.head.CssReferenceHeaderItem;
import org.apache.wicket.markup.head.JavaScriptReferenceHeaderItem;
import org.apache.wicket.request.resource.CssResourceReference;

/**
 * Provides a {@link JavaScriptReferenceHeaderItem} that will load a '.css' file corresponding to the supplied {@code Class}<br/>
 * <b>i.e.:</b> {@code MyClass.css}
 * 
 * @author Sebastien Briquet - sebfz1
 *
 */
public class StyleSheetPackageHeaderItem extends CssReferenceHeaderItem
{
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 * 
	 * @param scope the scope
	 */
	public StyleSheetPackageHeaderItem(Class<?> scope)
	{
		super(new CssResourceReference(scope, scope.getSimpleName() + ".css"), null, null, null);
	}
}
