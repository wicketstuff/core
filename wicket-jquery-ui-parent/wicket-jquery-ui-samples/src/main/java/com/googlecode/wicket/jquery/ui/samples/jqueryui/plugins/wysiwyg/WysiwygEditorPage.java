package com.googlecode.wicket.jquery.ui.samples.jqueryui.plugins.wysiwyg;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;

import com.googlecode.wicket.jquery.core.IJQueryWidget;
import com.googlecode.wicket.jquery.core.resource.StyleSheetPackageHeaderItem;
import com.googlecode.wicket.jquery.ui.form.button.ButtonBehavior;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
import com.googlecode.wicket.jquery.ui.plugins.wysiwyg.WysiwygEditor;
import com.googlecode.wicket.jquery.ui.plugins.wysiwyg.toolbar.DefaultWysiwygToolbar;
import com.googlecode.wicket.jquery.ui.samples.JQuerySamplePage;
import com.googlecode.wicket.kendo.ui.form.button.Button;

public class WysiwygEditorPage extends JQuerySamplePage
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

		// Buttons //
		form.add(new Button("display") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit()
			{
				String html = editor.getModelObject();

				if (html != null)
				{
					form.info(html);
				}
			}
		});

		form.add(new AjaxLink<Void>("disable") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onInitialize()
			{
				super.onInitialize();

				this.add(new ButtonBehavior(IJQueryWidget.JQueryWidget.getSelector(this)));
			}

			@Override
			public void onClick(AjaxRequestTarget target)
			{
				target.add(form.setEnabled(!form.isEnabled()));
			}
		});
	}

	// Methods //

	@Override
	public void renderHead(IHeaderResponse response)
	{
		super.renderHead(response);

		response.render(new StyleSheetPackageHeaderItem(WysiwygEditorPage.class));
	}
}
