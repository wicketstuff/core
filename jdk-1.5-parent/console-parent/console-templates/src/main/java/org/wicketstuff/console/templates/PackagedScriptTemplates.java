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

package org.wicketstuff.console.templates;

import java.util.ArrayList;
import java.util.List;

import org.wicketstuff.console.engine.Lang;

/**
 * Provides a set of packaged sample {@link ScriptTemplate}s.
 * 
 * @author cretzel
 */
public class PackagedScriptTemplates {

	private static final String SCRIPT_DIR_BASE = "org/wicketstuff/console/templates/";

	private static final String[] PACKAGED_GROOVY_TEMPLATES = new String[] {
			"HibernateCriteria", "HibernateHqlQuery", "HibernateSave",
			"HibernateShowSql", "HibernateStatistics", "Log4j",
			"MethodsAndFields", "ReadClasspathResource", "SystemProperties",
			"WicketClearMarkupCache", "WicketClearPropertiesCache",
			"WicketClientInfo", "WicketComponentHierarchy",
			"WicketInvalidateSession", "WicketSize" };

	private static final String[] PACKAGED_CLOJURE_TEMPLATES = new String[] { "MethodsAndFields" };

	/**
	 * Returns all packaged script templates for a given source language.
	 * 
	 * @param lang
	 *            language
	 * @return List of {@link ScriptTemplate}s
	 */
	public static List<ScriptTemplate> getPackagedScriptTemplates(
			final Lang lang) {

		final List<ScriptTemplate> templates = new ArrayList<ScriptTemplate>();

		String[] templateNames = new String[0];

		switch (lang) {
		case GROOVY:
			templateNames = PACKAGED_GROOVY_TEMPLATES;
			break;
		case CLOJURE:
			templateNames = PACKAGED_CLOJURE_TEMPLATES;
			break;
		default:
			break;
		}

		final ClassLoader cl = PackagedScriptTemplates.class.getClassLoader();
		for (final String name : templateNames) {
			final String scriptBase = SCRIPT_DIR_BASE
					+ lang.name().toLowerCase() + "/";
			final ScriptTemplate template = ScriptTemplateUtils
					.readTemplateFromClasspath(cl, scriptBase, name, lang);
			templates.add(template);
		}

		return templates;
	}

}
