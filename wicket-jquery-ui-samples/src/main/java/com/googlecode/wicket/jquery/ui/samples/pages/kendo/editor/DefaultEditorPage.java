package com.googlecode.wicket.jquery.ui.samples.pages.kendo.editor;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.Model;

import com.googlecode.wicket.kendo.ui.form.button.Button;
import com.googlecode.wicket.kendo.ui.panel.KendoFeedbackPanel;
import com.googlecode.wicket.kendo.ui.widget.editor.Editor;

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
		final Editor<String> editor = new Editor<String>("editor", Model.of("<p>test</p>"));
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
