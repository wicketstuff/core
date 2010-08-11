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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.wicketstuff.jslibraries.util.Assert;

public enum Library {

	DOJO, EXT_CORE,JQUERY, JQUERY_UI, MOOTOOLS_CORE, MOOTOOLS_MORE, PROTOTYPE, SCRIPTACULOUS, SWFOBJECT, YUI;

	static {
		new LibraryData();
	}
	private Map<Provider, SortedSet<Version>> providerInfo = new HashMap<Provider, SortedSet<Version>>();

	static void register(final Library lib, final Provider provider,
			final int[]... versions) {
		Assert.parameterNotNull(lib, "lib");
		Assert.parameterNotNull(provider, "provider");
		Assert.parameterNotNull(versions, "versions");

		lib.register(provider, versions);
	}

	private void register(final Provider provider, final int[]... versions) {
		Assert.parameterNotNull(provider, "provider");
		Assert.parameterNotNull(versions, "versions");

		SortedSet<Version> versionsAvailable = providerInfo.get(provider);
		if (versionsAvailable == null) {
			versionsAvailable = new TreeSet<Version>();
			providerInfo.put(provider, versionsAvailable);
		}
		for (int i = 0; i < versions.length; i++) {
			Version v = new Version(versions[i]);
			versionsAvailable.add(v);
		}
	}

	@SuppressWarnings("unchecked")
	public Set<Version> getVersions(final Provider provider) {
		Assert.parameterNotNull(provider, "provider");
		
		SortedSet<Version> sortedSet = providerInfo.get(provider);
		if (sortedSet == null) {
			return Collections.EMPTY_SET;
		}
		return sortedSet;
	}

	@Deprecated
	public String getLibraryName() {
		return LocalProvider.DEFAULT.getLocalFileName(this);
	}

	int getMaxVersionDepth(LocalProvider provider) {
		Assert.parameterNotNull(provider, "provider");

		int depth = 0;
		for (Version version : getVersions(provider)) {
			depth = version.getNumbers().length > depth ? version.getNumbers().length
					: depth;
		}
		return depth;
	}

}
