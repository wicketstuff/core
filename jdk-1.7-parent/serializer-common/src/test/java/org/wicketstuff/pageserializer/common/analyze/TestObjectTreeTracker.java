/**
 * Copyright (C)
 * 	2008 Jeremy Thomerson <jeremy@thomersonfamily.com>
 * 	2012 Michael Mosmann <michael@mosmann.de>
 *
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
package org.wicketstuff.pageserializer.common.analyze;


import org.junit.Assert;
import org.junit.Test;

public class TestObjectTreeTracker
{

	@Test
	public void testSizes()
	{

		Object a = "HA";
		Object b = 1;

		IObjectLabelizer labelizer = new IObjectLabelizer()
		{

			@Override
			public String labelFor(Object object)
			{
				return null;
			}
		};

		ObjectTreeTracker tracker = new ObjectTreeTracker(labelizer, a);
		tracker.newItem(0, a);
		tracker.newItem(12, b);
		tracker.closeItem(14, b);
		tracker.closeItem(24, a);

		ISerializedObjectTree tree = tracker.end(a);
		Assert.assertEquals("size", 22, tree.size());
		Assert.assertEquals("size", 2, tree.childSize());
	}
}
