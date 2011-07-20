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

import scala.actors.threadpool.Arrays;

/**
 * Provides a set of packaged sample {@link ScriptTemplate}s.
 * 
 * @author cretzel
 */
public class PackagedScriptTemplates implements IScriptTemplateStore
{

	private static final long serialVersionUID = 1L;

	private static final String SCRIPT_DIR_BASE = "org/wicketstuff/console/templates/";

	private static final String[] PACKAGED_GROOVY_TEMPLATES = new String[] { "ScriptStoreList",
			"ScriptStoreSave", "ScriptStoreDelete", "ScriptStoreGet", "HibernateCriteria",
			"HibernateHqlQuery", "HibernateSave", "HibernateShowSql", "HibernateStatistics",
			"Hibernate2ndLevelCache", "Log4j", "MethodsAndFields", "ReadClasspathResource",
			"SystemProperties", "WicketClearMarkupCache", "WicketClearPropertiesCache",
			"WicketClientInfo", "WicketComponentHierarchy", "WicketInvalidateSession", "WicketSize" };

	private static final String[] PACKAGED_CLOJURE_TEMPLATES = new String[] { "HibernateSave",
			"HibernateCriteria", "HibernateHqlQuery", "HibernateShowSql", "HibernateStatistics",
			"Log4j", "MethodsAndFields", "ReadClasspathResource", "SystemProperties",
			"WicketClearMarkupCache", "WicketClearPropertiesCache", "WicketClientInfo",
			"WicketComponentHierarchy", "WicketInvalidateSession", "WicketSize" };

	private static final String[] PACKAGED_SCALA_TEMPLATES = new String[] { "HibernateCriteria" };

	private static final String[] PACKAGED_JYTHON_TEMPLATES = new String[] { "HibernateSave",
			"HibernateCriteria", "HibernateHqlQuery", "HibernateShowSql", "Log4j",
			"MethodsAndFields", "SystemProperties", "WicketClearMarkupCache",
			"WicketClearPropertiesCache", "WicketClientInfo", "WicketInvalidateSession",
			"WicketSize" };

	/**
	 * Returns all packaged script templates for a given source language.
	 * 
	 * @param lang
	 *            language
	 * @return List of {@link ScriptTemplate}s
	 */
	public List<ScriptTemplate> findAll(final Lang lang)
	{

		final List<ScriptTemplate> templates = new ArrayList<ScriptTemplate>();

		final String[] templateNames = templateNamesForLang(lang);

		for (final String name : templateNames)
		{
			final ScriptTemplate template = getTemplate(lang, name);
			templates.add(template);
		}

		return templates;
	}

	private String[] templateNamesForLang(final Lang lang)
	{
		String[] templateNames = new String[0];

		switch (lang)
		{
			case GROOVY :
				templateNames = PACKAGED_GROOVY_TEMPLATES;
				break;
			case CLOJURE :
				templateNames = PACKAGED_CLOJURE_TEMPLATES;
				break;
			case SCALA :
				templateNames = PACKAGED_SCALA_TEMPLATES;
				break;
			case JYTHON :
				templateNames = PACKAGED_JYTHON_TEMPLATES;
				break;
			default :
				break;
		}
		return templateNames;
	}

	public ScriptTemplate getById(final Long id)
	{
		return templateForId(id);
	}

	private ScriptTemplate getTemplate(final Lang lang, final String name)
	{
		final ClassLoader cl = PackagedScriptTemplates.class.getClassLoader();
		final String scriptBase = SCRIPT_DIR_BASE + lang.name().toLowerCase() + "/";
		final ScriptTemplate template = ScriptTemplateUtils.readTemplateFromClasspath(cl,
			scriptBase, name, lang);
		template.id = idForTemplate(lang, name);
		return template;
	}

	private ScriptTemplate templateForId(final Long id)
	{
		final Lang lang = Lang.values()[(int)(id / 1000)];
		final String name = templateNamesForLang(lang)[(int)(id % 1000)];

		return getTemplate(lang, name);
	}


	private Long idForTemplate(final Lang lang, final String name)
	{
		final int major = lang.ordinal() * 1000;
		final int minor = Arrays.asList(templateNamesForLang(lang)).indexOf(name);

		return (long)(major + minor);
	}


	public boolean readOnly()
	{
		return true;
	}

	public void save(final ScriptTemplate t) throws ReadOnlyStoreException
	{
		throw new ReadOnlyStoreException("This is an internal read-only store");
	}


	public void delete(final ScriptTemplate template) throws ReadOnlyStoreException
	{
		throw new ReadOnlyStoreException("This is an internal read-only store");
	}

	public void delete(final Long id) throws ReadOnlyStoreException
	{
		throw new ReadOnlyStoreException("This is an internal read-only store");
	}

	public void detach()
	{
	}

}
