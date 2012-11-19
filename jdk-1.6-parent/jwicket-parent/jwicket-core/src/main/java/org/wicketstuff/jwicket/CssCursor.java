package org.wicketstuff.jwicket;


public enum CssCursor {

	AUTO("auto"),
	DEFAULT("default"),
	CROSSHARI("crosshair"),
	POINTER("pointer"),
	MOVE("move"),
	NRESIZE("n-resize"),
	NERESIZE("ne-resize"),
	ERESIZE("e-resize"),
	SERESIZE("se-resize"),
	SRESIZE("s-resize"),
	SWRESIZE("sw-resize"),
	WRESIZE("s-resize"),
	NWRESIZE("nw-resize"),
	TEXT("text"),
	WAIT("wait"),
	HELP("help");

	private final String name;

	private CssCursor(final String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public String toString() {
		return this.name();
	}
}
