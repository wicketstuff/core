/**
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.wicketstuff.rest.utils.mounting;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.wicketstuff.rest.WicketApplication;

public class MountingAnnotationTest
{
	private WicketTester tester;

	@BeforeEach
	public void setUp()
	{
		tester = new WicketTester(new WicketApplication(new Roles())
		{
			@Override
			public void init()
			{
				super.init();
				PackageScanner.scanPackage("org.wicketstuff.rest.resource");
			}
		});
	}

	@Test
	public void testResourceMounted() throws Exception
	{
		tester.getRequest().setMethod("GET");
		tester.executeUrl("./mountedpath");
		assertEquals(200, tester.getLastResponse().getStatus());
	}

}
