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

import org.wicketstuff.jslibraries.util.Assert;

public class VersionDescriptor {

	private final Library mLibrary;
	private final Integer[] mVersions;
	private final boolean mStopOnFirstMatch;

	private Version mMatch;

	private VersionDescriptor(Library library, boolean stopOnFirstMatch,
			Integer... versions) {

		Assert.parameterNotNull(library, "library");
		Assert.parameterNotNull(versions, "versions");

		mLibrary = library;
		mVersions = versions;
		mStopOnFirstMatch = stopOnFirstMatch;
	}

	public Library getLibrary() {
		return mLibrary;
	}

	public Version getVersion(final Provider provider) {
		Assert.parameterNotNull(provider, "provider");

		for (Version version : mLibrary.getVersions(provider)) {
			if (matches(version)) {
				mMatch = version;
				if (mStopOnFirstMatch) {
					break;
				}
			}
		}
		return mMatch;
	}

	private boolean matches(Version version) {
		Assert.parameterNotNull(version, "version");

		if (mVersions.length > version.getNumbers().length) {
			// we're more specific - don't match
			return false;
		}
		for (int i = 0; i < mVersions.length; i++) {
			if (mVersions[i] != null
					&& !mVersions[i].equals(version.getNumbers()[i])) {
				return false;
			}
		}
		return true;
	}

	public static VersionDescriptor alwaysLatest(Library lib) {
		return alwaysLatestOfVersion(lib, new int[0]);
	}

	public static VersionDescriptor exactVersion(Library lib, int... version) {

		Assert.parameterNotNull(lib, "lib");

		Integer[] nums = new Integer[version.length];
		for (int i = 0; i < version.length; i++) {
			nums[i] = version[i];
		}
		return new VersionDescriptor(lib, true, nums);
	}

	public static VersionDescriptor alwaysLatestOfVersion(Library lib,
			int... baseVersion) {

		Assert.parameterNotNull(lib, "lib");

		Integer[] nums = new Integer[lib
				.getMaxVersionDepth(LocalProvider.DEFAULT)];
		for (int i = 0; i < nums.length; i++) {
			nums[i] = i < baseVersion.length ? baseVersion[i] : null;
		}
		return new VersionDescriptor(lib, false, nums);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((mLibrary == null) ? 0 : mLibrary.hashCode());
		result = prime * result + ((mMatch == null) ? 0 : mMatch.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		VersionDescriptor other = (VersionDescriptor) obj;
		if (mLibrary == null) {
			if (other.mLibrary != null) {
				return false;
			}
		} else if (!mLibrary.equals(other.mLibrary)) {
			return false;
		}
		if (mMatch == null) {
			if (other.mMatch != null) {
				return false;
			}
		} else if (!mMatch.equals(other.mMatch)) {
			return false;
		}
		return true;
	}
}
