package com.googlecode.wicket.jquery.ui.samples.pages.kendo.editor;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;

import com.googlecode.wicket.jquery.ui.form.button.Button;
import com.googlecode.wicket.jquery.ui.kendo.editor.Editor;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;

public class DefaultEditorPage extends AbstractEditorPage
{
	private static final long serialVersionUID = 1L;
	
	public DefaultEditorPage()
	{
		Form<Void> form = new Form<Void>("form");
		this.add(form);
		
		// FeedbackPanel //
		final FeedbackPanel feedbackPanel = new JQueryFeedbackPanel("feedback");
		form.add(feedbackPanel);

		// ComboBox //
		final Editor<String> editor = new Editor<String>("editor", new Model<String>("<p>test</p>"));
		form.add(editor);

		// Buttons //
		form.add(new Button("button") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit()
			{
				String html = editor.getModelObject();
				
				this.info(html != null ? "Escaped HTML: " + html : "empty");
			}
		});
	}
}
