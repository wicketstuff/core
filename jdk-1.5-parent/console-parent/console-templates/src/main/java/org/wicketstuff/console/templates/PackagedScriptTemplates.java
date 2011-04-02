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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.util.file.Files;
import org.wicketstuff.console.engine.Lang;
import org.wicketstuff.console.engine.LangFileFilter;

/**
 * Provides a set of sample {@link ScriptTemplate}s.
 * <p>
 * Use {@link #dataProvider()} and feed a
 * {@link ScriptTemplateSelectionTablePanel} with it.
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
	 * Creates a data provider that returns all packaged templates for a given
	 * source language.
	 * 
	 * @param lang
	 *            language
	 * @return data provider
	 */
	public static IDataProvider<ScriptTemplate> packagedScriptTemplatesDataProvider(
			final Lang lang) {
		return new ListDataProvider<ScriptTemplate>(
				getPackagedScriptTemplates(lang));
	}

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
			final ScriptTemplate template = readTemplateFromClasspath(cl,
					scriptBase, name, lang);
			templates.add(template);
		}

		return templates;
	}

	/**
	 * Read a source file from class path into a {@link ScriptTemplate}.
	 * <p>
	 * The file has to have an appropriate extension such as .groovy or .clj.
	 * 
	 * @param cl
	 *            ClassLoader
	 * @param path
	 *            path on the class path
	 * @param name
	 *            name of the script (without file extension)
	 * @param lang
	 *            source language
	 * @return a ScriptTemplate
	 */
	static ScriptTemplate readTemplateFromClasspath(final ClassLoader cl,
			final String path, final String name, final Lang lang) {

		final URL url = cl.getResource(path + name + lang.getFileExtension());

		StringBuilder content;
		try {
			content = readUrl(url);
		} catch (final IOException e) {
			throw new RuntimeException(
					String.format("Could not read class path file %s %s %s",
							path, name, lang));
		}

		final ScriptTemplate template = new ScriptTemplate(
				camelCaseSpace(name), content.toString(), lang);
		return template;

	}

	private static StringBuilder readUrl(final URL url) throws IOException {

		final InputStream is = url.openConnection().getInputStream();
		final LineNumberReader r = new LineNumberReader(new InputStreamReader(
				is));

		final StringBuilder content = new StringBuilder();
		String line = null;
		while ((line = r.readLine()) != null) {
			content.append(line).append("\n");
		}

		return content;
	}

	/**
	 * Utility method to read {@link ScriptTemplate}s from a source directory.
	 * 
	 * @param lang
	 *            source language
	 * @param dir
	 *            directory to scan for source files
	 * @return list of {@link ScriptTemplate}s
	 */
	public static List<ScriptTemplate> readTemplatesFromDir(final Lang lang,
			final File dir) {

		final List<ScriptTemplate> templates = new ArrayList<ScriptTemplate>();

		final File[] files = dir.listFiles(LangFileFilter.create(lang));

		for (final File file : files) {
			final ScriptTemplate template = readTemplateFromFile(lang, file);
			templates.add(template);
		}

		return templates;
	}

	/**
	 * Utility method to read a {@link ScriptTemplate} from a source file.
	 * 
	 * @param lang
	 *            source language
	 * @param file
	 *            source file
	 * @return a {@link ScriptTemplate}
	 */
	public static ScriptTemplate readTemplateFromFile(final Lang lang,
			final File file) {
		final String title = camelCaseSpaceFilename(file);
		final String script = readFile(file);
		final ScriptTemplate template = new ScriptTemplate(title, script, lang);
		return template;
	}

	/**
	 * Utility method to convert a camel cased file name using
	 * {@link #camelCaseSpace(String)}.
	 */
	public static String camelCaseSpaceFilename(final File file) {
		final String filename = file.getName();
		return camelCaseSpaceFilename(filename);
	}

	/**
	 * Utility method to convert a camel cased file name using
	 * {@link #camelCaseSpace(String)}.
	 */
	public static String camelCaseSpaceFilename(final String filename) {
		final int indexOfExt = filename.lastIndexOf('.');
		if (indexOfExt == 0) {
			throw new RuntimeException("Invalid filename " + filename);
		}

		final String baseFilename = filename.substring(0, indexOfExt);
		final String scriptName = camelCaseSpace(baseFilename);

		return scriptName;
	}

	/**
	 * Converts a camel cased string into a string where each upper case letter
	 * is prefixed with a space.
	 * 
	 * @param str
	 *            a camel cased string
	 * @return a string where each upper case letter is prefixed with a space
	 */
	public static String camelCaseSpace(final String str) {
		if (str.length() == 0) {
			throw new RuntimeException(
					"camelCaseSpace not allowed for string length 0");
		}

		final char[] charArray = str.toCharArray();
		final StringBuilder result = new StringBuilder(charArray[0] + "");
		for (int i = 1; i < charArray.length; i++) {
			final char c = charArray[i];

			if (Character.isUpperCase(c)) {
				result.append(' ');
			}

			result.append(c);
		}

		return result.toString();
	}

	static String readFile(final File file) {
		try {
			return new String(Files.readBytes(file));
		} catch (final IOException e) {
			throw new RuntimeException("Could not read file " + file, e);
		}
	}

}
