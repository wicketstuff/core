package com.googlecode.wicket.jquery.ui.samples.pages.plugins;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;

import com.googlecode.wicket.jquery.ui.form.button.AjaxButton;
import com.googlecode.wicket.jquery.ui.plugins.SnippetLabel;

public class OptionSnippetPage extends AbstractSnippetPage
{
	private static final long serialVersionUID = 1L;

	/**
	 * @return the Model to be used as source for the SnippetLabel
	 */
	private static IModel<String> newSnippetModel()
	{
		return new LoadableDetachableModel<String>() {

			private static final long serialVersionUID = 1L;

			@Override
			protected String load()
			{
				return	"<h1>H1 tag!</h1>\n" +
					"<iframe src=\"index.html\"></iframe>\n" +
					"<div id=\"foo\">\n" +
					"<h3>H3 html is awesome!</h3>\n" +
					"<span class=\"bar\">Womp.</span>\n" +
					"<!-- comment -->\n" +
					"click here\n" +
					"</div>\n";
			}
		};
	}


	public OptionSnippetPage()
	{
		// Form //
		Form<Void> form = new Form<Void>("form");
		this.add(form);

		// SnippetLabel //
		final WebMarkupContainer container = new WebMarkupContainer("container");
		this.add(container.setOutputMarkupId(true));

		final SnippetLabel label = new SnippetLabel("code", "html", newSnippetModel());
		label.setStyle("ide-eclipse");
		container.add(label);

		// Buttons //
		form.add(new AjaxButton("style1", new Model<String>("ide-eclipse")) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form)
			{
				label.setStyle(this.getModelObject());
				target.add(container);
			}
		});

		form.add(new AjaxButton("style2", new Model<String>("blacknblue")) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form)
			{
				label.setStyle(this.getModelObject());
				target.add(container);
			}
		});
	}
}
