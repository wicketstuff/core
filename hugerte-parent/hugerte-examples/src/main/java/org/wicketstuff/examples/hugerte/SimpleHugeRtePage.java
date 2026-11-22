package org.wicketstuff.examples.hugerte;

import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.model.Model;
import org.wicketstuff.hugerte.HugeRteBehavior;

/**
 * @author Iulian-Corneliu COSTAN
 */
public class SimpleHugeRtePage extends HugeRteBasePage {
	private static final long serialVersionUID = 1L;

	public SimpleHugeRtePage() {
		var textArea = new TextArea<>("ta", Model.of("Some <b>element</b>, this is to be editor 1."));
		textArea.add(new HugeRteBehavior());
		add(textArea);
	}

}
