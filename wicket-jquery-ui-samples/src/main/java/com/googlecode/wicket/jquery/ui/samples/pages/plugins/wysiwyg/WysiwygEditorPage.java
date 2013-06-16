package com.googlecode.wicket.jquery.ui.samples.pages.plugins.wysiwyg;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;

import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
import com.googlecode.wicket.jquery.ui.plugins.wysiwyg.WysiwygEditor;
import com.googlecode.wicket.jquery.ui.plugins.wysiwyg.toolbar.DefaultWysiwygToolbar;
import com.googlecode.wicket.jquery.ui.samples.SamplePage;

public class WysiwygEditorPage extends SamplePage
{
	private static final long serialVersionUID = 1L;

	public WysiwygEditorPage()
	{
		// Form //
		final Form<Void> form = new Form<Void>("form");
		this.add(form);

		// Wysiwyg //
		DefaultWysiwygToolbar toolbar = new DefaultWysiwygToolbar("toolbar");
		final WysiwygEditor editor = new WysiwygEditor("wysiwyg", Model.of(""), toolbar);

		form.add(toolbar);
		form.add(editor);

		// Feedback//
		final FeedbackPanel feedback = new JQueryFeedbackPanel("feedback");
		form.add(feedback);

		// Button //
		AjaxButton button = new AjaxButton("button") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form)
			{
				String html = editor.getModelObject();

				if (html != null)
				{
					this.info(html);
				}

				target.add(feedback);
			}
		};

		form.add(button);
	}
}
