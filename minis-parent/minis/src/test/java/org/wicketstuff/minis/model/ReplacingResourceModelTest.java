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
package org.wicketstuff.minis.model;


import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.wicket.util.tester.TagTester;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReplacingResourceModelTest
{

	private WicketTester wicketTester;

	@BeforeEach
	public void setup()
	{
		wicketTester = new WicketTester();
	}

	@AfterEach
	public void tearDown()
	{
		wicketTester.destroy();
	}

	@Test
	public void testReplacing()
	{
		wicketTester.startPage(ReplacingResourceModelPage.class);
		String document = wicketTester.getLastResponse().getDocument();
		TagTester createTagByAttribute = TagTester.createTagByName(document, "span");
		assertEquals(createTagByAttribute.getValue(), "Hello my friends Max and Ulli");
	}
}
