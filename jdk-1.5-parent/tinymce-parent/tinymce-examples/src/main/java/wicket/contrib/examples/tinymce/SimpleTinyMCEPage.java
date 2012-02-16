package wicket.contrib.examples.tinymce;

import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.model.Model;

import wicket.contrib.tinymce.TinyMceBehavior;

/**
 * @author Iulian-Corneliu COSTAN
 */
public class SimpleTinyMCEPage extends TinyMCEBasePage
{
	private static final long serialVersionUID = 1L;

	public SimpleTinyMCEPage()
	{
		TextArea textArea = new TextArea("ta", new Model(TEXT));
		textArea.add(new TinyMceBehavior());
		add(textArea);
	}

	private String TEXT = "Some <b>element</b>, this is to be editor 1.";
}
