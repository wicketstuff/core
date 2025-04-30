/*
Licensed under the Apache License, Version 2.0 (the "License") http://www.apache.org/licenses/LICENSE-2.0
 */
package org.wicketstuff.jquery.ui.samples.kendoui.editor;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.Model;
import org.wicketstuff.kendo.ui.form.button.Button;
import org.wicketstuff.kendo.ui.panel.KendoFeedbackPanel;
import org.wicketstuff.kendo.ui.widget.editor.Editor;

public class DefaultEditorPage extends AbstractEditorPage
{
	private static final long serialVersionUID = 1L;

	public DefaultEditorPage()
	{
		Form<Void> form = new Form<Void>("form");
		this.add(form);

		// FeedbackPanel //
		final KendoFeedbackPanel feedback = new KendoFeedbackPanel("feedback");
		form.add(feedback);

		// ComboBox //
		final Editor editor = new Editor("editor", Model.of("<p>test</p>"));
		form.add(editor.setEscapeModelStrings(false));

		// Buttons //
		form.add(new Button("button") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit()
			{
				String html = editor.getModelObject();

				this.info(html != null ? html : "empty");
			}
		});
	}
}
