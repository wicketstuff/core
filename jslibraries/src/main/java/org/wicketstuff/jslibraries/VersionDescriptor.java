package org.wicketstuff.jslibraries;

public class VersionDescriptor {

	private final Library mLibrary;
	private final Integer[] mVersions;

	private boolean mMatchAttempted;
	private Version mMatch;
	
	private VersionDescriptor(Library library, Integer... versions) {
		if (library.getVersionDepth() != versions.length) {
			throw new IllegalArgumentException("must supply correct version depth");
		}
		mLibrary = library;
		mVersions = versions;
	}

	public Library getLibrary() {
		return mLibrary;
	}

	public Version getVersion() {
		if (!mMatchAttempted) {
			for (Version version : mLibrary.getVersions()) {
				if (matches(version)) {
					mMatch = version;
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
	public static VersionDescriptor alwaysLatestOfVersion(Library lib, int... baseVersion) {
		Integer[] nums = new Integer[lib.getVersionDepth()];
		for (int i = 0; i < nums.length; i++) {
			nums[i] = i < baseVersion.length ? baseVersion[i] : null;
		}
		return new VersionDescriptor(lib, nums);
	}
}
