package com.googlecode.wicket.jquery.ui.samples.pages.plugins.wysiwyg;

import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;

import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.ui.form.button.AjaxButton;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
import com.googlecode.wicket.jquery.ui.plugins.wysiwyg.WysiwygEditor;
import com.googlecode.wicket.jquery.ui.plugins.wysiwyg.toolbar.DefaultWysiwygToolbar;
import com.googlecode.wicket.jquery.ui.samples.SamplePage;

public class WysiwygEditorPage extends SamplePage
{
	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private String text;

	public WysiwygEditorPage()
	{
		// Form //
		final Form<Void> form = new Form<Void>("form");
		add(form.setOutputMarkupId(true));

		// Wysiwyg //
		DefaultWysiwygToolbar toolbar = new DefaultWysiwygToolbar("toolbar");
		final WysiwygEditor editor = new WysiwygEditor("wysiwyg", new PropertyModel<String>(this, "text"), toolbar);

		form.add(toolbar, editor);

		// Feedback//
		final FeedbackPanel feedback = new JQueryFeedbackPanel("feedback");
		form.add(feedback);

		add(new AjaxButton("display", form) {
				private static final long serialVersionUID = 1L;
	
				@Override
				protected void onSubmit(AjaxRequestTarget target, Form<?> _form)
				{
					String html = editor.getModelObject();
	
					if (html != null)
					{
						_form.info(html);
					}
	
					target.add(feedback);
				}
			}
			, new WebMarkupContainer("disable").setMarkupId("disable-btn").add(new JQueryBehavior("#disable-btn", "button")
				, new AjaxEventBehavior("click") {
					private static final long serialVersionUID = 1L;
					
					@Override
					protected void onEvent(AjaxRequestTarget target) {
						target.add(form.setEnabled(!form.isEnabled()));
					}
				})
		);
	}
}
