package org.wicketstuff.jwicket;


import java.io.Serializable;


/**
 * This is the base class for the jQuery integration with wicket.
 */
public class JQuery implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final int majorVersion = 0;
	private static final int minorVersion = 6;
	private static final int subVersion   = 5;

	private static final String version = majorVersion + "." + minorVersion + "." + subVersion;

	public static final String getVersion() {
		return version;
	}


	public static final int getMajorVersion() {
		return majorVersion;
	}

	public static final int getMinorVersion() {
		return minorVersion;
	}

	public static final int getSubVersion() {
		return subVersion;
	}


	private static boolean debug = false;
	public static boolean isDebug() {
		return JQuery.debug;
	}
	public static void setDebug(final boolean value) {
		JQuery.debug = value;
	}
}
