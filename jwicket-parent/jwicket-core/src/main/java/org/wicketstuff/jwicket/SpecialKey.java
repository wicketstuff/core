package org.wicketstuff.jwicket;


public enum SpecialKey {

	SHIFT("shift", 16),
	CTRL("ctrl", 17),
	ALT("alt", 18),
	ESC("esc", 27),
	PAGEUP("pageup", 33),
	PAGEDOWN("pagedown", 34),
	END("end", 35),
	POS1("pos1", 36),
	CURSOR_LEFT("crsr-left", 37),
	CURSOR_UP("crsr-up", 38),
	CURSOR_RIGHT("crsr-right", 39),
	CURSOR_DOWN("crsr-down", 40),
	INSERT("insert", 45),
	DELETE("delete", 46),
	ILLEGAL("illegal", -1);

	private final String name;
	private final int code;

	private SpecialKey(final String name, final int code) {
		this.name = name;
		this.code = code;
	}

	public String getName() {
		return this.name;
	}

	public int getCode() {
		return this.code;
	}


	public static SpecialKey getSpecialKey(final String name) {
		for (SpecialKey key : SpecialKey.values())
			if (key.getName().equalsIgnoreCase(name))
				return key;
		return ILLEGAL;
	}

	public String toString() {
		return this.name;
	}
}
