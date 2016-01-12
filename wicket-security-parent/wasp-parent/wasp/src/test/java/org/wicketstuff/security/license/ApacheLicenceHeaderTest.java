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
package org.wicketstuff.security.license;

import java.util.Arrays;

import org.apache.wicket.util.license.ApacheLicenseHeaderTestCase;

/**
 * Test that the license headers are in place in this project. The tests are run from
 * {@link ApacheLicenseHeaderTestCase}, but you can add project specific tests here if needed.
 * 
 * @author Frank Bille Jensen (frankbille)
 */
public class ApacheLicenceHeaderTest extends ApacheLicenseHeaderTestCase
{
	/**
	 * Construct.
	 */
	public ApacheLicenceHeaderTest()
	{
		// addHeaders = true;

		htmlIgnore = Arrays.asList(
		/*
		 * .html in test is very test specific and a license header would confuse and make it
		 * unclear what the test is about.
		 */
		"src/test/java",
		/*
		 * See NOTICE.txt
		 */
		"src/main/java/org/apache/wicket/util/diff");

		cssIgnore = Arrays.asList(
		/*
		 * .css in test is very test specific and a license header would confuse and make it unclear
		 * what the test is about.
		 */
		"src/test/java");

		xmlIgnore = Arrays.asList("EclipseCodeFormat.xml", "codetemplates.xml");

		javaScriptIgnore = Arrays.asList(
		/*
		 * .js in test is very test specific and a license header would confuse and make it unclear
		 * what the test is about.
		 */
		"src/test/java");

		propertiesIgnore = Arrays.asList(
		/*
		 * .properties in test is very test specific and a license header would confuse and make it
		 * unclear what the test is about.
		 */
		"src/test/java");
	}
}
