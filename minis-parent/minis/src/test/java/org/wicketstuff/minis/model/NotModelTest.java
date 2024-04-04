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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.wicket.model.Model;
import org.junit.jupiter.api.Test;

public class NotModelTest
{

	@Test
	public void testNullDependentModelThrowsNPE()
	{
		assertThrows(NullPointerException.class, () -> {
			new NotModel(null);
		});
	}

	@Test
	public void testTrueDependentModelYieldsFalse()
	{
		assertFalse(new NotModel(Model.of(true)).getObject());
	}

	@Test
	public void testFalseDependentModelYieldsTrue()
	{
		assertTrue(new NotModel(Model.of(false)).getObject());
	}

	@Test
	public void testNullDependentModelYieldsTrueByDefault()
	{
		assertTrue(new NotModel(Model.of((Boolean)null)).getObject());
	}

	@Test
	public void testSpecifyNullDependentModelResult()
	{
		assertFalse(new NotModel(Model.of((Boolean)null), false).getObject());
	}

}
