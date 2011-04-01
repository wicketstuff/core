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
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.util.file.Files;

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
		final File dir = getPackagedScriptFilesDir(lang);
		final List<ScriptTemplate> templates = readTemplatesFromDir(lang, dir);

		return templates;
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

	private static File getPackagedScriptFilesDir(final Lang lang) {

		final String scriptDirName = SCRIPT_DIR_BASE
				+ lang.name().toLowerCase();

		final URL url = PackagedScriptTemplates.class.getClassLoader()
				.getResource(scriptDirName);
		final File dir = urlToDir(url);

		return dir;
	}

	static File urlToDir(final URL url) {
		if (url == null) {
			throw new IllegalArgumentException("URL is null");
		}

		final String dirName = url.getFile();
		if ("".equals(dirName)) {
			throw new IllegalArgumentException(
					"URL does not denote a file name ");
		}

		final File dir = new File(dirName);
		if (!dir.isDirectory()) {
			throw new IllegalArgumentException(
					"URL does not denote a directory " + dir.getAbsolutePath());
		}

		return dir;
	}

	static String readFile(final File file) {
		try {
			return new String(Files.readBytes(file));
		} catch (final IOException e) {
			throw new RuntimeException("Could not read file " + file, e);
		}
	}

}
