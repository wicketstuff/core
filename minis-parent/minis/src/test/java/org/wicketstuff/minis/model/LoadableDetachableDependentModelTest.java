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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.wicket.markup.Markup;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class LoadableDetachableDependentModelTest
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
	public void testNullDependentModelThrowsNPE()
	{
		assertThrows(NullPointerException.class, () -> {
			new LoadableDetachableDependentModel<String, String>(null)
			{
				private static final long serialVersionUID = 1L;

				@Override
				protected String load()
				{
					return "test";
				}
			};
		});
	}

	@Test
	public void testDependentModelIsSameAndDetached()
	{

		LoadableDetachableModel<String> dependentModel = new LoadableDetachableModel<String>()
		{
			private static final long serialVersionUID = 1L;

			@Override
			protected String load()
			{
				return "test";
			}
		};

		LoadableDetachableDependentModel<String, String> model = new LoadableDetachableDependentModel<String, String>(
			dependentModel)
		{
			private static final long serialVersionUID = 1L;

			@Override
			protected String load()
			{
				return "test";
			}

		};
		assertSame(dependentModel, model.getDependentModel());
		tester.startComponentInPage(new Label(CID_LABEL, model), Markup.of(MARKUP));
		assertFalse(dependentModel.isAttached());

	}


}
