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

import org.apache.wicket.Application;
import org.apache.wicket.ResourceReference;

public class JSReference {

	public static ResourceReference getReference(VersionDescriptor versionDescriptor) {
		Version version = versionDescriptor.getVersion();
		Library lib = versionDescriptor.getLibrary();
		StringBuffer sb = new StringBuffer();
		sb.append("js/").append(lib.getLibraryName()).append('-');
		for (int i = 0; i < version.getNumbers().length; i++) {
			sb.append(version.getNumbers()[i]);
			if (i < (version.getNumbers().length - 1)) {
				sb.append('.');
			}
		}
		if (Application.DEPLOYMENT.equals(Application.get().getConfigurationType())) {
			sb.append(lib.getProductionVersionSignifier());
		}
		sb.append(".js");
		return new ResourceReference(JSReference.class, sb.toString());
	}
}
