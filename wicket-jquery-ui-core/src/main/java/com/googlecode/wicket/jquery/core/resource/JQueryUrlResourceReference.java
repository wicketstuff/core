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

import java.util.List;

import org.apache.wicket.Application;
import org.apache.wicket.markup.head.HeaderItem;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.request.Url;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.request.resource.UrlResourceReference;
import org.apache.wicket.resource.DynamicJQueryResourceReference;

/**
 * The resource reference for a jQuery plugin CDN library.
 * 
 * @author Sebastien Briquet - sebfz1
 */
public class JQueryUrlResourceReference extends UrlResourceReference
{
	private static final long serialVersionUID = 1L;

	public JQueryUrlResourceReference(Url url)
	{
		super(url);
	}

	public JQueryUrlResourceReference(String url)
	{
		super(Url.parse(url));
	}

	@Override
	public List<HeaderItem> getDependencies()
	{
		final ResourceReference jQueryReference;

		if (Application.exists())
		{
			jQueryReference = Application.get().getJavaScriptLibrarySettings().getJQueryReference();
		}
		else
		{
			jQueryReference = DynamicJQueryResourceReference.get();
		}

		List<HeaderItem> dependencies = super.getDependencies();
		dependencies.add(JavaScriptHeaderItem.forReference(jQueryReference));

		return dependencies;
	}
}
