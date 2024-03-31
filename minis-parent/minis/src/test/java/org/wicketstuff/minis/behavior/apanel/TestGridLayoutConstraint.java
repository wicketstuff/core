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
package org.wicketstuff.minis.behavior.apanel;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class TestGridLayoutConstraint
{
	@Test
	public void testContaining()
	{
		final GridLayoutConstraint constraint = new GridLayoutConstraint(1, 1).setColSpan(3)
			.setRowSpan(3);
		for (int col = 1; col < 4; col++)
		{
			for (int row = 1; row < 4; row++)
			{
				System.out.println("row = " + row);
				System.out.println("col = " + col);
				assertTrue(constraint.contains(col, row));
			}
		}
		assertFalse(constraint.contains(0, 0));
		assertFalse(constraint.contains(4, 4));
	}

	@Test
	public void testIntersection()
	{
		final GridLayoutConstraint constraint = new GridLayoutConstraint(1, 1).setColSpan(3)
			.setRowSpan(3);
		assertFalse(constraint.intersectsWith(new GridLayoutConstraint(0, 0)));
		assertTrue(constraint.intersectsWith(new GridLayoutConstraint(1, 1)));
		assertTrue(constraint.intersectsWith(new GridLayoutConstraint(2, 2)));
		assertTrue(constraint.intersectsWith(new GridLayoutConstraint(3, 3)));
		assertFalse(constraint.intersectsWith(new GridLayoutConstraint(4, 4)));
	}

	@Test
	public void testEqualAndComparableConsistence()
	{
		final GridLayoutConstraint constraint1 = new GridLayoutConstraint(0, 0);
		final GridLayoutConstraint constraint2 = new GridLayoutConstraint(0, 0);
		final GridLayoutConstraint constraint3 = new GridLayoutConstraint(1, 1);

		assertTrue(constraint1.compareTo(constraint2) == 0);
		assertTrue(constraint1.equals(constraint2));
		assertFalse(constraint2.compareTo(constraint3) == 0);
		assertFalse(constraint2.equals(constraint3));
	}
}
