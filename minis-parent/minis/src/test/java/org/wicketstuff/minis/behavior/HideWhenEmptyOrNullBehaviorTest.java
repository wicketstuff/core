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

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;

import org.apache.wicket.markup.Markup;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.util.ListModel;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class HideWhenEmptyOrNullBehaviorTest
{

	private static final String CID_LABEL = "label";
	private static final String MARKUP = "<span wicket:id=\"" + CID_LABEL + "\"></span>";

	private final WicketTester tester = new WicketTester();

	@AfterEach
	public void after()
	{
		tester.destroy();
	}

	@Test
	public void testComponentNotPresentOnNullModel()
	{
		tester.startComponentInPage(
			new Label(CID_LABEL, Model.of((Serializable)null)).add(HideWhenEmptyOrNullBehavior.get()),
			Markup.of(MARKUP));
		assertNull(tester.getTagByWicketId(CID_LABEL));
	}

	@Test
	public void testComponentPresentOnNotNullModel()
	{
		tester.startComponentInPage(
			new Label(CID_LABEL, Model.of(1)).add(HideWhenEmptyOrNullBehavior.get()),
			Markup.of(MARKUP));
		assertNotNull(tester.getTagByWicketId(CID_LABEL));
	}

	@Test
	public void testComponentNotPresentOnEmptyStringModel()
	{
		tester.startComponentInPage(
			new Label(CID_LABEL, Model.of("")).add(HideWhenEmptyOrNullBehavior.get()),
			Markup.of(MARKUP));
		assertNull(tester.getTagByWicketId(CID_LABEL));
	}

	@Test
	public void testComponentPresentOnNonEmptyStringModel()
	{
		tester.startComponentInPage(
			new Label(CID_LABEL, Model.of("test")).add(HideWhenEmptyOrNullBehavior.get()),
			Markup.of(MARKUP));
		assertNotNull(tester.getTagByWicketId(CID_LABEL));
	}

	@Test
	public void testComponentNotPresentOnEmptyCollectionModel()
	{
		tester.startComponentInPage(
			new Label(CID_LABEL, new ListModel<>(Collections.emptyList())).add(HideWhenEmptyOrNullBehavior.get()),
			Markup.of(MARKUP));
		assertNull(tester.getTagByWicketId(CID_LABEL));
	}

	@Test
	public void testComponentPresentOnNonEmptyCollectionModel()
	{
		tester.startComponentInPage(
			new Label(CID_LABEL, new ListModel<>(Arrays.asList("test"))).add(HideWhenEmptyOrNullBehavior.get()),
			Markup.of(MARKUP));
		assertNotNull(tester.getTagByWicketId(CID_LABEL));
	}

}
