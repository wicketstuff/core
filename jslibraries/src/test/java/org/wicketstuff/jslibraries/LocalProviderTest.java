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

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Set;

import org.apache.wicket.behavior.HeaderContributor;

import junit.framework.TestCase;


public class LocalProviderTest extends TestCase {


		public void testCDNs() throws Exception {
			IterateAllRegistered(false);
			IterateAllRegistered(true);
		}

		private void IterateAllRegistered(boolean production) throws IOException {

			
			Library[] libs = Library.values();
			for (int i = 0; i < libs.length; i++) {
				Library library = libs[i];
				Set<Version> versions = library.getVersions(LocalProvider.DEFAULT);
				for (Version v : versions) {
					
					VersionDescriptor vd = VersionDescriptor.exactVersion(library, v.getNumbers());
					StringBuffer fn = JSReference.createFileName(library, vd.getVersion(LocalProvider.DEFAULT), production);
					
					URL resource = getClass().getResource(fn.toString());
					System.out.println("checking file: "+fn);
					assertNotNull(resource);
					assertNotNull(resource.getContent());
				}

			}
		}

		
}
