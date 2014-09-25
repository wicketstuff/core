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
package org.wicketstuff.minis.behavior;

import org.apache.wicket.markup.Markup;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class VisibleModelBehaviorTest
{

	private static final String CID_LABEL = "label";
	private static final String MARKUP = "<span wicket:id=\"" + CID_LABEL + "\"></span>";

	private final WicketTester tester = new WicketTester();

	@After
	public void after()
	{
		tester.destroy();
	}

	@Test(expected = NullPointerException.class)
	public void testNullDependentModelThrowsNPE()
	{
		new Label(CID_LABEL).add(new VisibleModelBehavior(null));
	}

	@Test
	public void testComponentNotPresentOnFalseModel()
	{
		tester.startComponentInPage(
			new Label(CID_LABEL).add(new VisibleModelBehavior(Model.of(false))), Markup.of(MARKUP));
		Assert.assertNull(tester.getTagByWicketId(CID_LABEL));
	}

	@Test
	public void testComponentPresentOnTrueModel()
	{
		tester.startComponentInPage(
			new Label(CID_LABEL).add(new VisibleModelBehavior(Model.of(true))), Markup.of(MARKUP));
		Assert.assertNotNull(tester.getTagByWicketId(CID_LABEL));
	}


}
