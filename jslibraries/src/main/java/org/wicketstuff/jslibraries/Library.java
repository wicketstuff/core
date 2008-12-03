package org.wicketstuff.jslibraries;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;


public enum Library {

	PROTOTYPE("prototype", ""),
	JQUERY("jquery", ".min"),
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
