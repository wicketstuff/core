package wicket.contrib.examples.tinymce;

import wicket.contrib.tinymce.InPlaceEditComponent;

public class InlineTinyMCEPage extends TinyMCEBasePage
{
	private static final long serialVersionUID = 1L;

	public InlineTinyMCEPage()
	{
		InPlaceEditComponent component = new InPlaceEditComponent("editable",
			"<p><b>Click me</b> and <i>edit me</i> with <font color=\"red\">tinymce</font>. "
				+ "Afterwards, click save button to update this text with your changes!</p>");
		add(component);
	}
}
