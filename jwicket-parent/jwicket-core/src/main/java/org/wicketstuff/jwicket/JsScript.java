package org.wicketstuff.jwicket;


import java.io.Serializable;


public class JsScript implements Serializable {

	private static final long serialVersionUID = 1L;
	private final String script;

	public JsScript(final String script) {
		this.script = script;
	}

	public String toString() {
		return script;
	}
}
