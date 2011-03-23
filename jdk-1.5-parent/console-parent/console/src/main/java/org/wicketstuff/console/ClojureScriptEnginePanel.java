package org.wicketstuff.console;

import org.wicketstuff.console.engine.ClojureEngine;
import org.wicketstuff.console.engine.IScriptEngine;

public class ClojureScriptEnginePanel extends AbstractScriptEnginePanel {

	private static final long serialVersionUID = 1L;

	public ClojureScriptEnginePanel(String wicketId) {
		super(wicketId);
	}

	protected void initInput() {
		setInput("(import '(org.wicketstuff.console.engine ClojureEngine))\n"
				+ "(let [bindings (ClojureEngine/getBindings)\n"
				+ "      application (.get bindings \"application\")\n"
				+ "      page (.get bindings \"page\")\n"
				+ "      component (.get bindings \"component\")]\n"
				+ "  (println component))");
	}

	protected IScriptEngine newEngine() {
		return new ClojureEngine();
	}

}
