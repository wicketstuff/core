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

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

import java.io.File;

import org.junit.Before;
import org.junit.Test;
import org.wicketstuff.console.engine.Lang;

/**
 * Testing {@link ScriptTemplateUtils}.
 * 
 * @author cretzel
 */
public class ScriptTemplatesUtilsTest
{

	private static final String TEST_SCRIPTS_BASE_DIR = "org/wicketstuff/console/scripts/";

	@Before
	public void setUp() throws Exception
	{
	}

	@Test
	public void test_camelCaseSpace() throws Exception
	{
		// Given
		final String str = "GimmeSomeMo";

		// When
		final String result = ScriptTemplateUtils.camelCaseSpace(str);

		// Then
		assertEquals("Gimme Some Mo", result);
	}

	@Test
	public void test_camelCaseSpace_Numbers() throws Exception
	{
		// Given
		final String str = "GimmeSome4Mo";

		// When
		final String result = ScriptTemplateUtils.camelCaseSpace(str);

		// Then
		assertEquals("Gimme Some4 Mo", result);
	}

	@Test
	public void test_camelCaseSpaceFilename() throws Exception
	{
		// Given
		final String filename = "GimmeSomeMo.Mo.groovy";

		// When
		final String result = ScriptTemplateUtils.camelCaseSpaceFilename(filename);

		// Then
		assertEquals("Gimme Some Mo. Mo", result);
	}

	@Test
	public void test_readTemplateFromFile() throws Exception
	{
		// Given
		final File file = new File(getClass().getClassLoader()
			.getResource(TEST_SCRIPTS_BASE_DIR + "groovy/Test01.groovy")
			.toURI());

		// When
		final ScriptTemplate template = ScriptTemplateUtils.readTemplateFromFile(Lang.GROOVY, file);

		// Then
		assertNotNull(template);
		assertEquals("println \"foo\"", template.script);
	}

	@Test
	public void test_readTemplateFromClasspath() throws Exception
	{

		// When
		final ScriptTemplate template = ScriptTemplateUtils.readTemplateFromClasspath(
			getClass().getClassLoader(), TEST_SCRIPTS_BASE_DIR + "clojure/", "Test01", Lang.CLOJURE);

		// Then
		assertNotNull(template);
	}

	@Test(expected = RuntimeException.class)
	public void test_readTemplateFromClasspath_NotFound() throws Exception
	{

		// When
		ScriptTemplateUtils.readTemplateFromClasspath(getClass().getClassLoader(),
			TEST_SCRIPTS_BASE_DIR + "clojure/", "Test02", Lang.CLOJURE);

		// Then RuntimeException

	}

}
