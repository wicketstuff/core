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

import java.util.List;
import java.util.Map;

import org.apache.wicket.util.lang.Generics;
import org.apache.wicket.util.template.PackageTextTemplate;

/**
 * Provides base implementation (no text properties) of {@link IJQueryTemplate}
 * 
 * @author Sebastien Briquet - sebfz1
 */
public abstract class JQueryTemplate implements IJQueryTemplate
{
	private static final long serialVersionUID = 1L;

	public JQueryTemplate()
	{
	}

	@Override
	public List<String> getTextProperties()
	{
		return Generics.newArrayList();
	}

	/**
	 * Provides a {@link JQueryTemplate} that reads the template from file in specified class's package
	 */
	public static class JQueryPackageTextTemplate extends JQueryTemplate
	{
		private static final long serialVersionUID = 1L;
		
		private final PackageTextTemplate template;
		private transient Map<String, ?> variables = null;

		public JQueryPackageTextTemplate(Class<?> clazz, String fileName)
		{
			this.template = new PackageTextTemplate(clazz, fileName);
		}

		public JQueryPackageTextTemplate(Class<?> clazz, String fileName, String contentType)
		{
			this.template = new PackageTextTemplate(clazz, fileName, contentType);
		}

		public JQueryPackageTextTemplate(Class<?> clazz, String fileName, String contentType, String encoding)
		{
			this.template = new PackageTextTemplate(clazz, fileName, contentType, encoding);
		}

		public JQueryPackageTextTemplate(Class<?> clazz, String fileName, Map<String, ?> variables)
		{
			this.template = new PackageTextTemplate(clazz, fileName);
			this.variables = variables;
		}

		public JQueryPackageTextTemplate(Class<?> clazz, String fileName, String contentType, Map<String, ?> variables)
		{
			this.template = new PackageTextTemplate(clazz, fileName, contentType);
			this.variables = variables;
		}

		public JQueryPackageTextTemplate(Class<?> clazz, String fileName, String contentType, String encoding, Map<String, ?> variables)
		{
			this.template = new PackageTextTemplate(clazz, fileName, contentType, encoding);
			this.variables = variables;
		}

		@Override
		public String getText()
		{
			return this.template.asString(this.variables);
		}
	}
}
