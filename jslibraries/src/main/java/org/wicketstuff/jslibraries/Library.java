package org.wicketstuff.jslibraries;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;


public enum Library {

	PROTOTYPE("prototype", 4, ""),
	JQUERY("jquery", 3, ".min"),
	;

	static {
		registerVersion(JQUERY, 1, 0, 4);
		registerVersion(JQUERY, 1, 1, 4);
		registerVersion(JQUERY, 1, 2, 0);
		registerVersion(JQUERY, 1, 2, 1);
		registerVersion(JQUERY, 1, 2, 2);
		registerVersion(JQUERY, 1, 2, 3);
		registerVersion(JQUERY, 1, 2, 4);
		registerVersion(JQUERY, 1, 2, 5);
		registerVersion(JQUERY, 1, 2, 6);
		registerVersion(PROTOTYPE, 1, 5, 0, 0);
		registerVersion(PROTOTYPE, 1, 5, 1, 0);
		registerVersion(PROTOTYPE, 1, 5, 1, 1);
		registerVersion(PROTOTYPE, 1, 5, 1, 2);
		registerVersion(PROTOTYPE, 1, 6, 0, 0);
		registerVersion(PROTOTYPE, 1, 6, 0, 2);
		registerVersion(PROTOTYPE, 1, 6, 0, 3);
		
		for (Library lib : values()) {
			List<Version> list = new ArrayList<Version>(lib.mVersions);
			Collections.sort(list);
			lib.mVersions = Collections.unmodifiableList(list);
		}
	}

	private static void registerVersion(Library lib, int... versions) {
		lib.mVersions.add(new Version(versions));
	}

	private final String mLibraryName;
	private final String mProductionVersionSignifier;
	private final int mVersionDepth;
	private Collection<Version> mVersions = new HashSet<Version>();

	private Library(String name, int depth, String productionVersionSignifier) {
		mLibraryName = name;
		mVersionDepth = depth;
		mProductionVersionSignifier = productionVersionSignifier;
	}

	public String getLibraryName() {
		return mLibraryName;
	}
	public int getVersionDepth() {
		return mVersionDepth;
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
	
}
