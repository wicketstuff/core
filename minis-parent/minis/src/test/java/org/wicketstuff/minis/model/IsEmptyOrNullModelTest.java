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

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;

import org.apache.wicket.model.Model;
import org.apache.wicket.model.util.ListModel;
import org.junit.Assert;
import org.junit.Test;

public class IsEmptyOrNullModelTest
{

	@Test(expected = NullPointerException.class)
	public void testNullDependentModelThrowsNPE()
	{
		new IsEmptyOrNullModel<>(null);
	}

	@Test
	public void testNullModelValueYieldsTrue()
	{
		Assert.assertTrue(new IsEmptyOrNullModel<>(Model.of((Serializable)null)).getObject());
	}

	@Test
	public void testNotNullModelValueYieldsFalse()
	{
		Assert.assertFalse(new IsEmptyOrNullModel<>(Model.of(1)).getObject());
	}

	@Test
	public void testEmptyStringModelValueYieldsTrue()
	{
		Assert.assertTrue(new IsEmptyOrNullModel<>(Model.of("")).getObject());
	}

	@Test
	public void testNonEmptyStringModelValueYielsFalse()
	{
		Assert.assertFalse(new IsEmptyOrNullModel<>(Model.of("test")).getObject());
	}

	@Test
	public void testEmptyCollectionModelValueYieldsTrie()
	{
		Assert.assertTrue(new IsEmptyOrNullModel<>(new ListModel<>(Collections.emptyList())).getObject());
	}

	@Test
	public void testComponentPresentOnNonEmptyCollectionModel()
	{
		Assert.assertFalse(new IsEmptyOrNullModel<>(new ListModel<>(Arrays.asList("test"))).getObject());
	}

}
