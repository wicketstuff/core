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

import java.io.Serializable;

import org.wicketstuff.jslibraries.util.Assert;

public class VersionDescriptor implements Serializable
{
	private static final long serialVersionUID = 1L;

	private final Library mLibrary;
	private final boolean mExact;
	private final Version mVersion;

	private VersionDescriptor(Library library, boolean exact, Version version)
	{

		Assert.parameterNotNull(library, "library");
		Assert.parameterNotNull(version, "version");

		mLibrary = library;
		mVersion = version;
		mExact = exact;
	}

	public Library getLibrary()
	{
		return mLibrary;
	}

	public Version getVersion(final Provider provider)
	{
		Assert.parameterNotNull(provider, "provider");

		Version mMatch = null;

		for (Version version : mLibrary.getVersions(provider))
		{
			if (mExact)
			{
				if (version.equals(mVersion))
				{
					mMatch = version;
					break;
				}
			}
			else
			{
				if (version.matches(mVersion))
				{
					mMatch = version;
					// continue to look for something better
				}
			}
		}

		return mMatch;
	}

	public static VersionDescriptor alwaysLatest(Library lib)
	{
		return alwaysLatestOfVersion(lib, new int[0]);
	}

	public static VersionDescriptor exactVersion(Library lib, int... numbers)
	{

		Assert.parameterNotNull(lib, "lib");

		return new VersionDescriptor(lib, true, new Version(numbers));
	}

	public static VersionDescriptor alwaysLatestOfVersion(Library lib, int... numbers)
	{

		Assert.parameterNotNull(lib, "lib");

		return new VersionDescriptor(lib, false, new Version(numbers));
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + (mLibrary == null ? 0 : mLibrary.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		if (getClass() != obj.getClass())
		{
			return false;
		}
		VersionDescriptor other = (VersionDescriptor)obj;
		if (mLibrary == null)
		{
			if (other.mLibrary != null)
			{
				return false;
			}
		}
		else if (!mLibrary.equals(other.mLibrary))
		{
			return false;
		}
		return true;
	}
}
