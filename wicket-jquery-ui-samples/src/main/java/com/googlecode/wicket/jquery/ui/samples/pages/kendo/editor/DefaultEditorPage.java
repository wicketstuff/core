package com.googlecode.wicket.jquery.ui.samples.pages.kendo.editor;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;

import com.googlecode.wicket.jquery.ui.form.button.AjaxButton;
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
		form.add(feedbackPanel.setOutputMarkupId(true).setEscapeModelStrings(false));

		// ComboBox //
		final Editor<String> editor = new Editor<String>("editor", new Model<String>());
		form.add(editor);

		// Buttons //
		form.add(new AjaxButton("button") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form)
			{
				String html = editor.getModelObject();
				
				this.info(html != null ? html : "empty");
				target.add(feedbackPanel);
			}
		});
	}
}
