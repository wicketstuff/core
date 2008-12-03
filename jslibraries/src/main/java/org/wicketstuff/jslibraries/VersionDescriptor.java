package org.wicketstuff.jslibraries;


public class VersionDescriptor {

	private final Library mLibrary;
	private final Integer[] mVersions;
	private final boolean mStopOnFirstMatch;

	private boolean mMatchAttempted;
	private Version mMatch;
	
	private VersionDescriptor(Library library, boolean stopOnFirstMatch, Integer... versions) {
		mLibrary = library;
		mVersions = versions;
		mStopOnFirstMatch = stopOnFirstMatch;
	}

	public Library getLibrary() {
		return mLibrary;
	}

	public Version getVersion() {
		if (!mMatchAttempted) {
			for (Version version : mLibrary.getVersions()) {
				if (matches(version)) {
					mMatch = version;
					if (mStopOnFirstMatch) {
						break;
					}
				}
			}
		}
		return mMatch;
	}

	private boolean matches(Version version) {
		for (int i = 0; i < mVersions.length; i++) {
			if (mVersions[i] != null && !mVersions[i].equals(version.getNumbers()[i])) {
				return false;
			}
		}
		return true;
	}
	
	public static VersionDescriptor alwaysLatest(Library lib) {
		return alwaysLatestOfVersion(lib, new int[0]);
	}
	public static VersionDescriptor exactVersion(Library lib, int... version) {
		Integer[] nums = new Integer[version.length];
		for (int i = 0; i < version.length; i++) {
			nums[i] = version[i];
		}
		return new VersionDescriptor(lib, true, nums);
	}
	public static VersionDescriptor alwaysLatestOfVersion(Library lib, int... baseVersion) {
		Integer[] nums = new Integer[lib.getMaxVersionDepth()];
		for (int i = 0; i < nums.length; i++) {
			nums[i] = i < baseVersion.length ? baseVersion[i] : null;
		}
		return new VersionDescriptor(lib, false, nums);
	}
}
