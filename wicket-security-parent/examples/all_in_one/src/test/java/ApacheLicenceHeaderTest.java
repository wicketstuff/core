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
import org.apache.wicket.util.license.ApacheLicenseHeaderTestCase;

/**
 * Test that the license headers are in place in this project. The tests are run from
 * {@link ApacheLicenseHeaderTestCase}, but you can add project specific tests here if
 * needed.
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

		htmlIgnore = new String[] {
		/*
		 * a license header would confuse and make it unclear what the example is about.
		 */
		"src/main/java", "src/main/webapp"};

		cssIgnore = new String[] {
		/*
		 * a license header would confuse and make it unclear what the test is about.
		 */
		"src/main/webapp"};

		xmlIgnore =
			new String[] {"EclipseCodeFormat.xml", "codetemplates.xml",
				"src/webapp/WEB-INF/applicationContext.xml"};

		javaIgnore = new String[] {};

		javaScriptIgnore = new String[] {
		/*
		 * .js in test is very test specific and a license header would confuse and make
		 * it unclear what the test is about.
		 */
		"src/main/webapp"};

		propertiesIgnore = new String[] {
		/*
		 * .properties and a license header make no sense.
		 */
		"src/main/java"};
	}
}
