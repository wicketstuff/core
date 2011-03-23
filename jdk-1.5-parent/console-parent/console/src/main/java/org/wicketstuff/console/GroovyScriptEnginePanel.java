package org.wicketstuff.console;

import org.wicketstuff.console.engine.GroovyEngine;
import org.wicketstuff.console.engine.IScriptEngine;

public class GroovyScriptEnginePanel extends AbstractScriptEnginePanel {

	private static final long serialVersionUID = 1L;

	public GroovyScriptEnginePanel(String wicketId) {
		super(wicketId);
	}

	protected void initInput() {
		setInput("println application\n" + "println page\n"
				+ "println component\n");
	}

	protected IScriptEngine newEngine() {
		return new GroovyEngine();
	}

}
