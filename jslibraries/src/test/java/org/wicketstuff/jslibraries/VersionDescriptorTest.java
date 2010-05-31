/**
 * Copyright (C) 2008 Jeremy Thomerson <jeremy@thomersonfamily.com>
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
package org.wicketstuff.jslibraries;

import junit.framework.TestCase;

public class VersionDescriptorTest extends TestCase {

	public void testLatestOfVersionDescriptor() throws Exception {
		VersionDescriptor vd = VersionDescriptor.alwaysLatestOfVersion(Library.JQUERY, 1, 1);
		assertEquals(new Version(1, 1, 4), vd.getVersion(LocalProvider.DEFAULT));
	}

	public void testExactVersionDescriptor() throws Exception {
		VersionDescriptor vd = VersionDescriptor.exactVersion(Library.JQUERY, 1, 2);
		assertEquals(new Version(1, 2), vd.getVersion(LocalProvider.DEFAULT));
		
		vd = VersionDescriptor.exactVersion(Library.JQUERY, 1, 2, 6);
		assertEquals(new Version(1, 2, 6), vd.getVersion(LocalProvider.DEFAULT));
	}

	public void testAlwaysLatestDescriptor() throws Exception {
		// this test will obviously fail every time we add a new JQUERY version
		VersionDescriptor vd = VersionDescriptor.alwaysLatest(Library.JQUERY);
		assertEquals(new Version(1, 4, 2), vd.getVersion(LocalProvider.DEFAULT));
	}
	
	public void testLibraryWithManyVersionDepths() throws Exception {
		assertEquals(new Version(1, 5, 1, 2), VersionDescriptor.alwaysLatestOfVersion(Library.PROTOTYPE, 1, 5).getVersion(LocalProvider.DEFAULT));
		assertEquals(new Version(1, 5, 1, 2), VersionDescriptor.alwaysLatestOfVersion(Library.PROTOTYPE, 1, 5, 1).getVersion(LocalProvider.DEFAULT));
		assertEquals(new Version(1, 6, 0), VersionDescriptor.exactVersion(Library.PROTOTYPE, 1, 6, 0).getVersion(LocalProvider.DEFAULT));
		// this test will obviously fail every time we add a new PROTOTYPE version
		assertEquals(new Version(1, 6, 0, 3), VersionDescriptor.alwaysLatest(Library.PROTOTYPE).getVersion(LocalProvider.DEFAULT));
		
	}
}
