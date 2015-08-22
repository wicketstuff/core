package org.wicketstuff.jwicket;


import java.io.Serializable;


public class JsOption implements Serializable {

	private static final long serialVersionUID = 1L;
	private final String name;
	private final String value;

	public JsOption(final String name, final String value) {
		this.name = name;
		this.value = value;
	}

	public JsOption(final String name, final int value) {
		this.name = name;
		this.value = String.valueOf(value);
	}

	public String toString() {
		return name + ":" + value;
	}
}
