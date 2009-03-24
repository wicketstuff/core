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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;


public enum Library {

	PROTOTYPE("prototype", ""),
	JQUERY("jquery", ".min"),
	MOOTOOLS_CORE("mootools-core", ".min"),
	MOOTOOLS_MORE("mootools-more", ".min"),
	;

	static {
		registerVersion(JQUERY, 1, 0, 4);
		registerVersion(JQUERY, 1, 1, 4);
		registerVersion(JQUERY, 1, 2);
		registerVersion(JQUERY, 1, 2, 1);
		registerVersion(JQUERY, 1, 2, 2);
		registerVersion(JQUERY, 1, 2, 3);
		registerVersion(JQUERY, 1, 2, 4);
		registerVersion(JQUERY, 1, 2, 5);
		registerVersion(JQUERY, 1, 2, 6);
		registerVersion(PROTOTYPE, 1, 5, 0);
		registerVersion(PROTOTYPE, 1, 5, 1);
		registerVersion(PROTOTYPE, 1, 5, 1, 1);
		registerVersion(PROTOTYPE, 1, 5, 1, 2);
		registerVersion(PROTOTYPE, 1, 6, 0);
		registerVersion(PROTOTYPE, 1, 6, 0, 2);
		registerVersion(PROTOTYPE, 1, 6, 0, 3);
		registerVersion(MOOTOOLS_CORE, 1, 2, 1);
		registerVersion(MOOTOOLS_MORE, 1, 2);
		
		for (Library lib : values()) {
			List<Version> list = new ArrayList<Version>(lib.mVersions);
			Collections.sort(list);
			lib.mVersions = Collections.unmodifiableList(list);
			//System.out.println("Initalized: " + lib);
		}
	}

	private static void registerVersion(Library lib, int... versions) {
		lib.mVersions.add(new Version(versions));
	}

	private final String mLibraryName;
	private final String mProductionVersionSignifier;
	private int mMaxVersionDepth;
	private Collection<Version> mVersions = new HashSet<Version>();

	private Library(String name, String productionVersionSignifier) {
		mLibraryName = name;
		mProductionVersionSignifier = productionVersionSignifier;
	}

	public String getLibraryName() {
		return mLibraryName;
	}
	public String getProductionVersionSignifier() {
		return mProductionVersionSignifier;
	}
	public Collection<Version> getVersions() {
		return mVersions;
	}
	
	public static void main(String[] args) {
		Library.class.getSuperclass();
	}

	public int getMaxVersionDepth() {
		if (mMaxVersionDepth == 0) {
			int depth = 0;
			for (Version version : mVersions) {
				depth = version.getNumbers().length > depth ? version.getNumbers().length : depth;
			}
			mMaxVersionDepth = depth;
		}
		return mMaxVersionDepth;
	}
	
	@Override
	public String toString() {
		return super.toString() + "\t [" + mVersions + "]";
	}
	
}
