package org.wicketstuff.html5.fileapi;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.upload.FileUploadField;

public class TestPage extends WebPage
{
	private static final long serialVersionUID = -8189892371317106568L;

	private final Form<Void> form;
	private final FileUploadField field;

	public TestPage()
	{
		form = new Form<>("form");
		add(form);

		field = new FileUploadField("field");
		form.add(field);
	}

	public Form<Void> getForm()
	{
		return form;
	}

	public FileUploadField getField()
	{
		return field;
	}
}
