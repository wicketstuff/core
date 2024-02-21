package org.wicketstuff.examples.tinymce;

import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.model.Model;
import org.wicketstuff.tinymce6.TinyMceBehavior;

/**
 * @author Iulian-Corneliu COSTAN
 */
public class SimpleTinyMCEPage extends TinyMCEBasePage {
	private static final long serialVersionUID = 1L;

	public SimpleTinyMCEPage() {
		var textArea = new TextArea<>("ta", Model.of("Some <b>element</b>, this is to be editor 1."));
		textArea.add(new TinyMceBehavior());
		add(textArea);
	}

}
